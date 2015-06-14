package com.cloudtime.ui

import com.cloudtime.dto.Timer
import com.cloudtime.ui.common.BaseRecyclerAdapter

public class TimersAdapter : BaseRecyclerAdapter() {

    fun update(timers: List<Timer>) {
        clearItems()
        timers.forEach { addItem(TimerItemAdapter(it)) }
        notifyDataSetChanged()
    }
}
