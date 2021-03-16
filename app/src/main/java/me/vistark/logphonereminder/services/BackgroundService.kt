package me.vistark.logphonereminder.services

import android.content.Intent
import android.text.format.DateUtils
import com.google.gson.Gson
import me.vistark.fastdroid_lib.services.FastdroidService
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.repositories.ReminderLogRepository
import me.vistark.logphonereminder.ui.remind_alert.RemindAlertActivity
import java.util.*

class BackgroundService : FastdroidService(
    "LPReminder",
    R.mipmap.ic_launcher_round,
    "Vui lòng không xóa tác vụ này"
) {

    lateinit var ReminderLogRepository: ReminderLogRepository

    override fun onCreate() {
        super.onCreate()
        ReminderLogRepository = ReminderLogRepository(this)
    }

    fun isInTime(time: Date): Boolean {
        return Date().time >= time.time
    }

    var state = 0
    override fun tasks() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val filteredRecords = ReminderLogRepository.GetAll().filter { x ->
                    !x.IsReminded && DateUtils.isToday(x.RemindAt.time)
                }
                val totalCount = ReminderLogRepository.Count()
                val remindedCount = totalCount - filteredRecords.size
                val progress = 0
                if (totalCount > 0) {
                    (remindedCount / totalCount).toInt()
                }
                if (totalCount <= 0 || remindedCount == totalCount) {
                    if (state != 0) {
                        state = 0
                        updateDefault()
                    }
                } else {
                    if (state != 1) {
                        state = 1
                        updateProgress("Đã nhắc ${remindedCount}/${totalCount}", progress)
                        // Tìm record có thời gian là hiện tại và chưa được nhắc
                        val x = filteredRecords.firstOrNull { x -> isInTime(x.RemindAt) }
                        if (x != null) {
                            startActivityForAlert(x.Id)
                        }
                    }
                }
            }
        }, 1000, 5 * 1000)
    }

    fun startActivityForAlert(id: String) {
        val dialogIntent = Intent(this, RemindAlertActivity::class.java)
        dialogIntent.putExtra("ID", id)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }
}