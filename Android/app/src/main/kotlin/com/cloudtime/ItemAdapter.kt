package com.cloudtime

import android.support.v7.widget.RecyclerView
import android.view.View

public interface ItemAdapter {

    fun getLayoutId(): Int

    fun onCreateViewHolder(itemView: View): BaseViewHolder

    fun onBind(holder: BaseViewHolder)
}
