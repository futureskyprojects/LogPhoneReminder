package me.vistark.logphonereminder.ui._commond.text_only_av

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.vistark.logphonereminder.R

class TextOnlyAdapter<T>(
    val arrayList: ArrayList<T>,
    val bind: (e: T, holder: TextOnlyViewHolder) -> Unit
) : RecyclerView.Adapter<TextOnlyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextOnlyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.text_only_item, parent, false)
        return TextOnlyViewHolder(v)
    }

    override fun onBindViewHolder(holder: TextOnlyViewHolder, position: Int) {
        bind.invoke(arrayList[position], holder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}