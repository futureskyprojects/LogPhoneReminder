package me.vistark.logphonereminder.ui.nearest_call

import android.provider.CallLog
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.vistark.fastdroid_lib.utils.DateTimeUtils.Companion.format
import me.vistark.fastdroid_lib.utils.FastDateOfWeek.Companion.getNameOfDayVN
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.entities.ReminderLogEntity
import me.vistark.logphonereminder.application.repositories.ReminderLogRepository
import me.vistark.logphonereminder.ui.nearest_call.models.CallLogs

class NearestCallViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val cItemLnRoot: LinearLayout = v.findViewById(R.id.cItemLnRoot)
    val addStateIcon: ImageView = v.findViewById(R.id.addStateIcon)
    val phoneNumber: TextView = v.findViewById(R.id.phoneNumber)
    val callStateAndDuration: TextView = v.findViewById(R.id.callStateAndDuration)
    val callDate: TextView = v.findViewById(R.id.callDate)

    val ReminderLogRepository = ReminderLogRepository(v.context)

    fun bind(callLogs: CallLogs) {
        phoneNumber.text = callLogs.phoneNumber
        callStateAndDuration.text =
            "(${callTypeToString(callLogs.callType)}/${callLogs.callDuration}s)"
        callDate.text =
            callLogs.callDate.format("HH:mm:ss dd/MM/yyyy") + " (${callLogs.callDate.getNameOfDayVN()})"
        if (ReminderLogRepository.Any(
                "${ReminderLogEntity.PHONE_NUMBER}=?",
                arrayOf(callLogs.phoneNumber)
            )
        ) {
            addStateIcon.setImageResource(R.drawable.checked)
        } else {
            addStateIcon.setImageResource(R.drawable.unchecked)
        }
    }

    fun callTypeToString(callType: Int): String {
        return when (callType) {
            CallLog.Calls.OUTGOING_TYPE -> "Gọi đi"
            CallLog.Calls.INCOMING_TYPE -> "Gọi đến"
            CallLog.Calls.MISSED_TYPE -> "Gọi nhỡ"
            else -> "Kh.Xác định"
        }
    }
}