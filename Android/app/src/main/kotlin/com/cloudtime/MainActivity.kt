package com.cloudtime

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import com.cloudtime.service.TheService
import rx.Subscription
import java.util.concurrent.TimeUnit

public class MainActivity : BaseActivity() {

    private var subscription: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar) as Toolbar)
        val editText = findViewById(R.id.main_edit) as EditText
        editText.setOnEditorActionListener { v, actionId, event ->
            if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
                addTimer(15, TimeUnit.MINUTES)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        loadTimers()
    }

    override fun onPause() {
        super.onPause()
        cancelLoading()
    }

    private fun addTimer(duration: Long, unit: TimeUnit) {
        TheService().addTimer(duration, unit)
    }

    private fun loadTimers() {
        subscription = TheService().loadTimers()
                .subscribe({
                    Log.e("tag", "size: ${it.size()}")
                }, {
                    Log.e("tag", "error", it)
                })
    }

    private fun cancelLoading() {
        subscription?.unsubscribe()
    }
}
