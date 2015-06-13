package com.cloudtime

import com.cloudtime.dto.Timer

public class TimersAdapter : BaseRecyclerAdapter() {

    fun update(timers: List<Timer>) {
        clearItems()
        timers.forEach { addItem(TimerItemAdapter(it)) }
        notifyDataSetChanged()
    }
}
