package me.vistark.logphonereminder.ui.diary_phone_number_reminder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.vistark.fastdroid_lib.utils.DateTimeUtils.Companion.format
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.entities.ReminderLogEntity

class DPRViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val cItemLnRoot: LinearLayout = v.findViewById(R.id.cItemLnRoot)
    val isReminded: ImageView = v.findViewById(R.id.isReminded)
    val phoneNumber: TextView = v.findViewById(R.id.phoneNumber)
    val remindAt: TextView = v.findViewById(R.id.remindAt)

    fun bind(entity: ReminderLogEntity) {
        if (entity.IsReminded) {
            isReminded.setImageResource(R.drawable.checked)
        } else {
            isReminded.setImageResource(R.drawable.unchecked)
        }
        phoneNumber.text = entity.PhoneNumber
        remindAt.text = "Nhắc vào lúc: " + entity.RemindAt.format("HH:mm:ss dd/MM/yyyy")
    }
}