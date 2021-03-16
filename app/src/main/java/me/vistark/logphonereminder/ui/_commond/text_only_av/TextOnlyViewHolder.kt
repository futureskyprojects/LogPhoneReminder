package me.vistark.logphonereminder.ui._commond.text_only_av

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.vistark.logphonereminder.R

class TextOnlyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val cItemLnRoot: LinearLayout = v.findViewById(R.id.cItemLnRoot)
    val textContent: TextView = v.findViewById(R.id.textContent)

    fun bind(content: String) {
        textContent.text = content
        textContent.isSelected = true
    }
}