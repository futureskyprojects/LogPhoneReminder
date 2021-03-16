package me.vistark.logphonereminder

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import me.vistark.fastdroid_lib.services.FastdroidService.Companion.isServiceRunning
import me.vistark.fastdroid_lib.ui.activities.FastdroidActivity
import me.vistark.fastdroid_lib.utils.AnimationUtils.fadeIn
import me.vistark.fastdroid_lib.utils.FastToasty.ToastError
import me.vistark.fastdroid_lib.utils.GlideUtils.load
import me.vistark.fastdroid_lib.utils.PermissionUtils.requestAllPermissions
import me.vistark.fastdroid_lib.utils.TimerUtils.startAfter
import me.vistark.logphonereminder.application.constants.AppRequires
import me.vistark.logphonereminder.services.BackgroundService
import me.vistark.logphonereminder.ui.diary_phone_number_reminder.DiaryPhoneNumberReminderActivity
import kotlin.random.Random

class MainActivity : FastdroidActivity(R.layout.activity_main, isLimit = false) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestAllPermissions(AppRequires.permissions,
            onCompleted = {
                redirect()
                runServices()
            },
            onDenied = {
                ToastError("Ứng dụng cần được cung cấp tất cả các quyền để hoạt động")
            })

    }

    private fun runServices() {
        if (!isServiceRunning(BackgroundService::class.java.name)) {
            val intent = Intent(this, BackgroundService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }


    private fun redirect() {
        startLoadingIcon()
        startAfter(Random.nextLong(500L, 1500L)) {
            startSingleActivity(DiaryPhoneNumberReminderActivity::class.java)
        }
    }

    private fun startLoadingIcon() {
        ivLoadingIcon.visibility = View.VISIBLE
        ivLoadingIcon.load(R.raw.loading_pink)
        ivLoadingIcon.fadeIn(300) {

        }
    }
}