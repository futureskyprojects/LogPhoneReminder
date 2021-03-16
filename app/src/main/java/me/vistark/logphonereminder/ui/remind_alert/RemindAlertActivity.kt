package me.vistark.logphonereminder.ui.remind_alert

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_remind_alert.*
import me.vistark.fastdroid_lib.ui.activities.FastdroidActivity
import me.vistark.fastdroid_lib.utils.FastToasty.ToastError
import me.vistark.fastdroid_lib.utils.VibrateUtils.vibrate
import me.vistark.fastdroid_lib.utils.VibrateUtils.vibrateLoop
import me.vistark.fastdroid_lib.utils.ViewExtension.onTap
import me.vistark.fastdroid_lib.utils.WakeLock.wakeLock
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.entities.ReminderLogEntity
import me.vistark.logphonereminder.application.repositories.ReminderLogRepository
import me.vistark.logphonereminder.ui.diary_phone_number_reminder.DiaryPhoneNumberReminderActivity
import java.lang.Exception

class RemindAlertActivity : FastdroidActivity(R.layout.activity_remind_alert) {

    lateinit var ReminderLogRepository: ReminderLogRepository

    var currentRemindLogE: ReminderLogEntity? = null
    lateinit var mediaPlayer: MediaPlayer
    var v: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ReminderLogRepository = ReminderLogRepository(this)

        initPrev()

        process()

        initViewData()

        confirmReminded()

        notifySignal()
    }

    private fun confirmReminded() {
        currentRemindLogE!!.IsReminded = true
        ReminderLogRepository.Update(currentRemindLogE!!.Id, currentRemindLogE!!)
        DiaryPhoneNumberReminderActivity.crr?.reloadData()
    }

    private fun notifySignal() {
        wakeLock()

        mediaPlayer = MediaPlayer.create(this, R.raw.iphone_x_ringtone)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        v = vibrateLoop()
        dissmissButton.onTap {
            mediaPlayer.stop()
            mediaPlayer.release()
            v?.cancel()
            onBackPressed()
        }
    }

    override fun onDestroy() {
        try {
            mediaPlayer.stop()
            mediaPlayer.release()
            v?.cancel()
        } catch (e: Exception) {

        }
        super.onDestroy()
    }

    private fun initViewData() {
        phoneNumber.text = currentRemindLogE?.PhoneNumber
        directionName.text = "(Hướng: ${currentRemindLogE?.Direction})"
        areaName.text = "(Khu vực: ${currentRemindLogE?.Area})"
        noteContent.text = currentRemindLogE?.Note

    }

    private fun process() {
        if (currentRemindLogE == null) {
            ToastError("Không thể xác định được số cần nhắc")
            finish()
        }
    }

    private fun initPrev() {
        val resId = intent.getStringExtra("ID")
        if (!resId.isNullOrEmpty()) {
            currentRemindLogE = ReminderLogRepository.Get(resId)
        }
    }
}