package com.cloudtime.ui

import android.app.Activity
import android.view.View
import android.widget.TextView
import com.cloudtime.R
import com.cloudtime.dto.Timer
import com.cloudtime.ui.common.BaseViewHolder
import com.cloudtime.ui.common.ItemAdapter
import java.text.SimpleDateFormat

public class TimerItemAdapter(
        private val timer: Timer,
        private val activity: Activity)
: ItemAdapter {

    override fun getLayoutId(): Int {
        return R.layout.timer_item
    }

    override fun onCreateViewHolder(itemView: View): BaseViewHolder {
        return Holder(itemView)
    }

    override fun onBind(holder: BaseViewHolder) {
        val h = holder as Holder

//        val startTimeText = if (timer.startedAt == null) ""
//                else SimpleDateFormat("h:mm a").format(timer.startedAt)
        val durationSeconds = timer.durationInSeconds
        val remainingTimeText =
            if ( timer.startedAt != null ) {
                val remainingSeconds = (timer.startedAt!!.getTime() + durationSeconds * 1000 - System.currentTimeMillis()) / 1000
                "${remainingSeconds / 60}m ${remainingSeconds % 60}s"
            } else {
                ""
            }
        val durationText = "${durationSeconds / 60}m ${durationSeconds % 60}s"
        h.textView.setText("$durationText; ${timer.title}\n" +
                "${remainingTimeText}")

        activity.registerForContextMenu(h.itemView)
        h.itemView.setOnClickListener({
            if ( timer.started() ) {
                timer.stopTimer()
            } else {
                timer.startTimer()
            }
        })
        h.itemView.setTag(timer)
    }

    class Holder(itemView: View) : BaseViewHolder(itemView) {

        val textView = find(R.id.timer_item_text) as TextView
    }
}
