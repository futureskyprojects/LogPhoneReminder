package me.vistark.logphonereminder.ui.diary_phone_number_reminder

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.common_rv_layout.*
import me.vistark.fastdroid_lib.ui.activities.FastdroidActivity
import me.vistark.fastdroid_lib.ui.dialog.ConfirmDialog.deleteConfirm
import me.vistark.fastdroid_lib.utils.AnimationUtils.fadeIn
import me.vistark.fastdroid_lib.utils.AnimationUtils.fadeOut
import me.vistark.fastdroid_lib.utils.FastToasty.ToastInfo
import me.vistark.fastdroid_lib.utils.FastToasty.ToastSuccess
import me.vistark.fastdroid_lib.utils.TimerUtils
import me.vistark.fastdroid_lib.utils.ViewExtension.onTap
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.AppStorage
import me.vistark.logphonereminder.application.entities.AreaEntity
import me.vistark.logphonereminder.application.entities.ReminderLogEntity
import me.vistark.logphonereminder.application.repositories.ReminderLogRepository
import me.vistark.logphonereminder.ui._commond.text_only_av.TextOnlyAdapter
import me.vistark.logphonereminder.ui.area_manager.AreaManagerActivity
import me.vistark.logphonereminder.ui.area_manager.create_update.CreateUpdateAreaManagerActivity
import me.vistark.logphonereminder.ui.create_update_remind.CreateUpdateRemindActivity
import me.vistark.logphonereminder.ui.direct_manager.DirectManagerActivity
import me.vistark.logphonereminder.ui.nearest_call.NearestCallActivity

class DiaryPhoneNumberReminderActivity :
    FastdroidActivity(
        R.layout.activity_diary_phone_number_reminder,
        isHaveActionBar = true
    ) {

    companion object {
        var crr: DiaryPhoneNumberReminderActivity? = null
    }

    val data: ArrayList<ReminderLogEntity> = ArrayList()
    lateinit var adapter: DPRAdapter

    lateinit var ReminderLogRepository: ReminderLogRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ReminderLogRepository = ReminderLogRepository(this)

        initActionBar()

        initRecyclerViews()

        syncData()

        crr = this
    }

    private fun initRecyclerViews() {
        noContentText.text = "Hiện tại chưa có nhắc nhở cho bất cứ số điện thoại nào"
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        adapter = DPRAdapter(data)
        adapter.onLongClick = { e ->
            deleteConfirm("Anh/Chị có thực sự muốn xóa nhắc cho số \"${e.PhoneNumber}\"?") {
                if (it) {
                    if (ReminderLogRepository.Remove(e.Id) > 0) {
                        ToastSuccess("Đã xóa nhắc cho số \"${e.PhoneNumber}\"")
                        reloadData()
                    }
                }
            }
        }
        adapter.onClick = { e ->
            val intent = Intent(this, CreateUpdateRemindActivity::class.java)
            intent.putExtra("ID", e.Id)
            startActivityForTextResult("", intent) {
                reloadData()
            }
        }
        rv.adapter = adapter
    }

    fun reloadData() {
        data.clear()
        adapter.notifyDataSetChanged()
        rvState()
        TimerUtils.startAfter(300) {
            syncData()
        }
    }

    private fun rvState() {
        if (data.size > 0) {
            rv.visibility = View.VISIBLE
            rv.fadeIn(300)
        } else {
            rv.fadeOut(300) {
                rv.visibility = View.GONE
            }
        }
    }

    private fun syncData() {
        var synced = ReminderLogRepository.GetAll()
        data.clear()
        data.addAll(synced)
        label.text = "Danh sách nhắc (${data.size})"
        adapter.notifyDataSetChanged()
        rvState()
    }

    private fun initActionBar() {
        // Thiết lập tiêu đề
        supportActionBar?.title = "Các số cần nhắc nhở"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.base_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new_log_phone -> {
                redirect()
                return true
            }
            R.id.areManager -> {
                startActivity(AreaManagerActivity::class.java)
                return true
            }
            R.id.directManager -> {
                startActivity(DirectManagerActivity::class.java)
                return true
            }
            else -> {
            }
        }
        return false
    }

    private fun redirect() {
        startActivityForTextResult("", NearestCallActivity::class.java) {
            reload()
        }
    }
}