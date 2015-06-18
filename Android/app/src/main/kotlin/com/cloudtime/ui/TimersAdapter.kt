package com.cloudtime.ui

import android.app.Activity
import com.cloudtime.dto.Timer
import com.cloudtime.ui.common.BaseRecyclerAdapter

public class TimersAdapter(
        val activity : Activity
) : BaseRecyclerAdapter() {

    fun update(timers: List<Timer>) {
        clearItems()
        timers.forEach { addItem(TimerItemAdapter(it, activity)) }
        notifyDataSetChanged()
    }
}
