package me.vistark.logphonereminder.ui.direct_manager

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
import me.vistark.fastdroid_lib.utils.StringUtils.Guid
import me.vistark.fastdroid_lib.utils.TimerUtils
import me.vistark.fastdroid_lib.utils.ViewExtension.onTap
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.AppStorage
import me.vistark.logphonereminder.application.constants.AppRequires
import me.vistark.logphonereminder.application.entities.DirectionEntity
import me.vistark.logphonereminder.ui._commond.text_only_av.TextOnlyAdapter
import me.vistark.logphonereminder.ui.direct_manager.create_update.CreateUpdatDirectActivity

class DirectManagerActivity :
    FastdroidActivity(R.layout.activity_direct_manager, isHaveActionBar = true) {

    var isReadyForPickItemName = false

    val data: ArrayList<DirectionEntity> = ArrayList()
    lateinit var adapter: TextOnlyAdapter<DirectionEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)

        iniArgs()

        initActionBar()

        initRecyclerViews()

        syncData()
    }

    private fun iniArgs() {
        isReadyForPickItemName = intent.getBooleanExtra("PICK", false)
    }

    private fun initRecyclerViews() {
        noContentText.text = "Hiện tại chưa có hướng nào được nhập"
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        adapter = TextOnlyAdapter(data) { e, h ->
            h.bind(e.Name)
            h.cItemLnRoot.onTap {
                if (!isReadyForPickItemName) {
                    val intent = Intent(this, CreateUpdatDirectActivity::class.java)
                    intent.putExtra("ID", e.Id)
                    startActivityForTextResult("", intent) {
                        reloadData()
                    }
                } else {
                    val intent = Intent()
                    intent.putExtra("NAME", e.Name)
                    ToastInfo("Đã chọn hướng \"${e.Name}\"")
                    setResult(RESULT_OK, intent)
                    finish()
                }

            }
            h.cItemLnRoot.setOnLongClickListener {
                deleteConfirm("Anh/Chị có thực sự muốn xóa hướng \"${e.Name}\" ra khỏi danh sách?") {
                    if (it) {
                        AppStorage.directions =
                            AppStorage.directions.filter { x -> !x.Id.equals(e.Id, true) }
                                .toTypedArray()
                        ToastSuccess("Đã xóa hướng \"${e.Name}\"")
                        reloadData()
                    }
                }
                return@setOnLongClickListener true
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

    private fun initActionBar() {
        if (isReadyForPickItemName) {
            supportActionBar?.title = "Nhấp chọn phương hướng"
        } else {
            supportActionBar?.title = "Quản lý phương hướng"
        }

        // Hiển thị nút trở vềF
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
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
            R.id.addNewItem -> {
                startActivityForTextResult("", CreateUpdatDirectActivity::class.java) {
                    reloadData()
                }
                return true
            }
            else -> {
            }
        }
        return false
    }

    private fun syncData() {
        var synced = AppStorage.directions
        if (synced.isEmpty()) {
            synced = initFirstData()
            AppStorage.directions = synced
        }
        data.clear()
        data.addAll(synced)
        label.text = "Danh sách hướng (${data.size})"
        adapter.notifyDataSetChanged()
        rvState()
    }

    private fun initFirstData(): Array<DirectionEntity> {
        val areas = ArrayList<DirectionEntity>()
        AppRequires.directions.forEach {
            val area = DirectionEntity(Guid(), it)
            areas.add(area)
        }
        return areas.toTypedArray()
    }
}