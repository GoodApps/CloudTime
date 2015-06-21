package com.cloudtime.ui

import android.app.Activity
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import com.cloudtime.dto.Timer
import com.cloudtime.ui.common.BaseRecyclerAdapter
import java.util.HashSet

public class TimersAdapter(
        val activity : Activity
) : BaseRecyclerAdapter() {

    val handler = Handler(Looper.getMainLooper())

    val countDownTimers : MutableSet<CountDownTimer> = HashSet<CountDownTimer>()


    fun update(timers: List<Timer>) {
        countDownTimers.forEach {
            it.cancel()
        }
        countDownTimers.clear()

        clearItems()
        var itemPos = -1
        timers.forEach {
            itemPos ++;
            addItem(TimerItemAdapter(it, activity))
            if ( it.started() ) {
                val countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 250) {

                    override fun onTick(millisUntilFinished: Long) {
                        notifyItemChanged(itemPos)
//                        notifyDataSetChanged()
                    }

                    override fun onFinish() {
                        // nothing
                    }
                }

                countDownTimers.add(countDownTimer)
                countDownTimer.start()
            }

        }
        notifyDataSetChanged()
    }
}
