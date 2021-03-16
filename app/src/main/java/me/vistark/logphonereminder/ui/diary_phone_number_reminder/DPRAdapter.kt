package me.vistark.logphonereminder.ui.diary_phone_number_reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.vistark.fastdroid_lib.interfaces.IClickable
import me.vistark.fastdroid_lib.interfaces.ILongClickable
import me.vistark.fastdroid_lib.utils.ViewExtension.onTap
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.entities.ReminderLogEntity

class DPRAdapter(val arrayList: ArrayList<ReminderLogEntity>) :
    RecyclerView.Adapter<DPRViewHolder>(),
    IClickable<ReminderLogEntity>,
    ILongClickable<ReminderLogEntity> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DPRViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return DPRViewHolder(v)
    }

    override fun onBindViewHolder(holder: DPRViewHolder, position: Int) {
        val e = arrayList[position]
        holder.bind(e)
        holder.cItemLnRoot.onTap {
            onClick?.invoke(e)
        }
        holder.cItemLnRoot.setOnLongClickListener {
            onLongClick?.invoke(e)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override var onClick: ((ReminderLogEntity) -> Unit)? = null
    override var onLongClick: ((ReminderLogEntity) -> Unit)? = null
}