package me.vistark.logphonereminder.ui.nearest_call

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.CallLog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.common_rv_layout.*
import me.vistark.fastdroid_lib.ui.activities.FastdroidActivity
import me.vistark.fastdroid_lib.utils.AnimationUtils.fadeIn
import me.vistark.fastdroid_lib.utils.AnimationUtils.fadeOut
import me.vistark.fastdroid_lib.utils.FastToasty.ToastSuccess
import me.vistark.fastdroid_lib.utils.TimerUtils.startAfter
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.ui.create_update_remind.CreateUpdateRemindActivity
import me.vistark.logphonereminder.ui.nearest_call.models.CallLogs
import java.lang.Long
import java.util.*
import kotlin.collections.ArrayList


class NearestCallActivity : FastdroidActivity(
    R.layout.activity_nearest_call,
    isHaveActionBar = true
) {
    val callLogs: ArrayList<CallLogs> = ArrayList()
    lateinit var adapter: NearestCallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)

        initActionBar()

        initRecyclerViews()

        syncCallDetails()
    }

    private fun initRecyclerViews() {
        noContentText.text = "Hiện tại chưa có lịch sử cuộc gọi nào"
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        adapter = NearestCallAdapter(callLogs)
        adapter.onClick = { cl ->
            val intent = Intent(this, CreateUpdateRemindActivity::class.java)
            intent.putExtra("PhoneNumber", cl.phoneNumber)
            startActivityForTextResult("", intent) {
                reloadData()
                setResult(RESULT_OK)
            }
        }
        rv.adapter = adapter
    }

    fun reloadData() {
        callLogs.clear()
        adapter.notifyDataSetChanged()
        rvState()
        startAfter(300) {
            syncCallDetails()
        }
    }

    fun addCallLogs(c: CallLogs) {
        val index = callLogs.indexOfFirst { x -> x.phoneNumber == c.phoneNumber }
        if (index >= 0) {
            if (callLogs[index].callDate < c.callDate) {
                callLogs[index] = c
            }
        } else {
            callLogs.add(c)
        }
        adapter.notifyDataSetChanged()
        label.text = "Danh sách lịch sử số mới nhất (${callLogs.size})"

        rvState()
    }

    private fun rvState() {
        if (callLogs.size > 0) {
            rv.visibility = View.VISIBLE
            rv.fadeIn(300)
        } else {
            rv.fadeOut(300) {
                rv.visibility = View.GONE
            }
        }
    }

    private fun initActionBar() {
        // Thiết lập tiêu đề
        supportActionBar?.title = "Các số gần đây"

        // Hiển thị nút trở vềF
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.reload_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Khi nhấn nút trở về
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.reload_phone_list -> {
                reloadData()
                ToastSuccess("Làm mới danh sách các cuộ gọi gần đây thành công")
                return true
            }
            else -> {
            }
        }
        return false
    }

    private fun syncCallDetails() {
        var managedCursor: Cursor? =
            contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
        if (managedCursor == null)
            managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null)
        if (managedCursor == null)
            return;
        val number: Int = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type: Int = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
        val date: Int = managedCursor.getColumnIndex(CallLog.Calls.DATE)
        val duration: Int = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
        while (managedCursor.moveToNext()) {
            val phNumber: String = managedCursor.getString(number)
            val callType: String = managedCursor.getString(type)
            val callDate: String = managedCursor.getString(date)
            val callDayTime = Date(Long.valueOf(callDate))
            val callDuration: String = managedCursor.getString(duration)

            val dircode = callType.toInt()
            val _cls = CallLogs(phNumber, dircode, callDayTime, callDuration)
            addCallLogs(_cls)
        }
        managedCursor.close()
    }
}