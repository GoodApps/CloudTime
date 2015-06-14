package com.cloudtime.ui.common

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

public open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun find(IdRes id: Int): View {
        return itemView.findViewById(id)
    }
}
