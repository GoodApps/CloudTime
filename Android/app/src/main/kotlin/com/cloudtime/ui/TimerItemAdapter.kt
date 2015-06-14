package com.cloudtime.ui

import android.view.View
import android.widget.TextView
import com.cloudtime.R
import com.cloudtime.dto.Timer
import com.cloudtime.ui.common.BaseViewHolder
import com.cloudtime.ui.common.ItemAdapter

public class TimerItemAdapter(private val timer: Timer) : ItemAdapter {

    override fun getLayoutId(): Int {
        return R.layout.timer_item
    }

    override fun onCreateViewHolder(itemView: View): BaseViewHolder {
        return Holder(itemView)
    }

    override fun onBind(holder: BaseViewHolder) {
        val h = holder as Holder
        h.textView.setText("${timer.durationInSeconds / 60} minutes")
    }

    class Holder(itemView: View) : BaseViewHolder(itemView) {

        val textView = find(R.id.timer_item_text) as TextView
    }
}
