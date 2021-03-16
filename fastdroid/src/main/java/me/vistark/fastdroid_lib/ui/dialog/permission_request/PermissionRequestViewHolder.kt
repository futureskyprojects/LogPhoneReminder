package me.vistark.fastdroid_lib.ui.dialog.permission_request

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.vistark.fastdroid_lib.R
import me.vistark.fastdroid_lib.core.models.RequirePermission

class PermissionRequestViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val iprTvPermissionName: TextView = v.findViewById(R.id.iprTvPermissionName)
    private val iprTvPermissionDescription: TextView =
        v.findViewById(R.id.iprTvPermissionDescription)

    fun bind(pos: Int, reqPermission: RequirePermission) {
        iprTvPermissionName.text = "$pos." + reqPermission.displayName
        iprTvPermissionDescription.text = reqPermission.description
    }
}