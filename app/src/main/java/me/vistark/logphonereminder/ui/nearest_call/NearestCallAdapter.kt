package me.vistark.logphonereminder.ui.nearest_call

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.vistark.fastdroid_lib.interfaces.IClickable
import me.vistark.fastdroid_lib.utils.ViewExtension.onTap
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.ui.nearest_call.models.CallLogs

class NearestCallAdapter(val callLogs: ArrayList<CallLogs>) :
    RecyclerView.Adapter<NearestCallViewHolder>(), IClickable<CallLogs> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearestCallViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.phone_number_log_item, parent, false)
        return NearestCallViewHolder(v)
    }

    override fun onBindViewHolder(holder: NearestCallViewHolder, position: Int) {
        val callLogs = callLogs[position]
        holder.bind(callLogs)
        holder.cItemLnRoot.onTap {
            onClick?.invoke(callLogs)
        }
    }

    override fun getItemCount(): Int {
        return callLogs.size
    }

    override var onClick: ((CallLogs) -> Unit)? = null
}