package me.vistark.logphonereminder.ui.create_update_remind

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.common_create_update_reminder.*
import me.vistark.fastdroid_lib.ui.activities.FastdroidActivity
import me.vistark.fastdroid_lib.utils.DateTimeUtils.Companion.format
import me.vistark.fastdroid_lib.utils.FastToasty.ToastError
import me.vistark.fastdroid_lib.utils.FastToasty.ToastSuccess
import me.vistark.fastdroid_lib.utils.StringUtils.Guid
import me.vistark.fastdroid_lib.utils.ViewExtension.binDateTimePicker
import me.vistark.fastdroid_lib.utils.ViewExtension.onTap
import me.vistark.fastdroid_lib.utils.ViewExtension.onTextChanged
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.entities.ReminderLogEntity
import me.vistark.logphonereminder.application.repositories.ReminderLogRepository
import me.vistark.logphonereminder.ui.area_manager.AreaManagerActivity
import me.vistark.logphonereminder.ui.direct_manager.DirectManagerActivity
import java.util.*

class CreateUpdateRemindActivity : FastdroidActivity(
    R.layout.activity_create_remind,
    isHaveActionBar = true
) {
    lateinit var ReminderLogRepository: ReminderLogRepository

    var isDateTimeSelected = false

    var entity = ReminderLogEntity("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ReminderLogRepository = ReminderLogRepository(this)

        initPreArg()
        bindDateTimePicker()
        bindAreSelector()
        bindDirectionSelector()
        bindNoteInputer()

        initOnButtonConfirm()

        initActionBar()
    }

    private fun bindNoteInputer() {
        notes.onTextChanged {
            validate()
            entity.Note = it
        }
    }

    private fun initOnButtonConfirm() {
        ceConfirmButton.onTap {
            if (ReminderLogRepository.Any(
                    "${ReminderLogEntity.PHONE_NUMBER}=? AND" +
                            " ${ReminderLogEntity.REMIND_AT}=? AND" +
                            " ${ReminderLogEntity.ID}!=?",
                    arrayOf(
                        entity.PhoneNumber,
                        entity.RemindAt.format("HH:mm:ss dd/MM/yyyy"),
                        entity.Id
                    )
                )
            ) {
                ToastSuccess("Không thể đặt nhắc cho cùng một số điện thoại tại cùng một thời điểm")
                return@onTap
            }
            if (entity.Id.isEmpty()) {
                createRemind()
            } else {
                updateRemind()
            }
        }
    }

    private fun createRemind() {
        entity.Id = Guid()
        val res = ReminderLogRepository.Create(entity) > 0
        if (res) {
            ToastSuccess("Tạo nhắc cho số \"${entity.PhoneNumber}\" thành công")
            setResult(RESULT_OK)
            finish()
        } else {
            ToastError("Tạo nhắc chưa thành công, vui lòng thử lại")
        }
    }

    private fun updateRemind() {
        val res = ReminderLogRepository.Update(entity.Id, entity) > 0
        if (res) {
            ToastSuccess("Cập nhật nhắc cho số \"${entity.PhoneNumber}\" thành công")
            setResult(RESULT_OK)
            finish()
        } else {
            ToastError("Cập nhật nhắc chưa thành công, vui lòng thử lại")
        }
    }

    private fun initActionBar() {
        if (entity.Id.isEmpty()) {
            supportActionBar?.title = "Thêm nhắc mới"
            ceConfirmButton.text = "Thêm nhắc"
        } else {
            supportActionBar?.title = "Sửa nhắc nhở"
            ceConfirmButton.text = "Sửa nhắc"
        }

        // Hiển thị nút trở vềF
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // Khi nhấn nút trở về
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun validate() {
        ceConfirmButton.isEnabled =
            !(entity.Area.isEmpty() || entity.Direction.isEmpty() || entity.PhoneNumber.isEmpty() || !isDateTimeSelected)
    }

    private fun bindDirectionSelector() {
        directionName.onTap {
            val intent = Intent(this, DirectManagerActivity::class.java)
            intent.putExtra("PICK", true)
            startActivityForTextResult("NAME", intent) { s ->
                if (!s.isEmpty()) {
                    directionName.text = s
                    entity.Direction = s
                    validate()
                } else {
                    ToastError("Chọn hướng không thành công")
                }
            }
        }
    }

    private fun bindAreSelector() {
        areaName.onTap {
            val intent = Intent(this, AreaManagerActivity::class.java)
            intent.putExtra("PICK", true)
            startActivityForTextResult("NAME", intent) { s ->
                if (!s.isEmpty()) {
                    areaName.text = s
                    entity.Area = s
                    validate()
                } else {
                    ToastError("Chọn khu vực không thành công")
                }
            }
        }
    }

    private fun bindDateTimePicker() {
        remindAt.binDateTimePicker(entity.RemindAt) {
            isDateTimeSelected = true
            validate()
            val cal = Calendar.getInstance()
            cal.time = it
            cal.set(Calendar.SECOND, 0)
            entity.RemindAt = cal.time
            entity.IsReminded = false
        }
    }

    private fun initPreArg() {
        val gottedId = intent.getStringExtra("ID")
        val gottedPhoneNumber = intent.getStringExtra("PhoneNumber")
        if (gottedId.isNullOrEmpty()) {
            if (gottedPhoneNumber.isNullOrEmpty()) {
                finish()
                return
            } else {
                entity.PhoneNumber = gottedPhoneNumber
            }
        } else {
            val temp = ReminderLogRepository.Get(gottedId)
            if (temp != null) {
                entity = temp
                areaName.text = entity.Area
                directionName.text = entity.Direction
                isDateTimeSelected = true
                remindAt.text = entity.RemindAt.format("HH:mm:ss dd/MM/yyyy")
                notes.setText(entity.Note)
            } else {
                finish()
                return
            }
        }

        phoneNumber.setText(entity.PhoneNumber)
    }
}