package com.cloudtime

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

public open class BaseRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val items: MutableList<ItemAdapter> = ArrayList()

    fun clearItems() {
        items.clear()
    }

    fun addItem(itemAdapter: ItemAdapter) {
        items.add(itemAdapter)
    }

    override fun getItemCount(): Int {
        return items.size()
    }

    override fun getItemViewType(position: Int): Int {
        return items.get(position).getLayoutId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        for (itemAdapter in items) {
            val layoutId = itemAdapter.getLayoutId()
            if (layoutId == viewType) {
                val inflater = LayoutInflater.from(parent.getContext())
                val itemView = inflater.inflate(layoutId, parent, false)
                return itemAdapter.onCreateViewHolder(itemView)
            }
        }
        throw IllegalStateException()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        items.get(position).onBind(holder)
    }
}
