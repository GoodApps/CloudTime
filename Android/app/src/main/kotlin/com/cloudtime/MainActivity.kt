package com.cloudtime

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import com.cloudtime.service.TheService
import com.parse.ParseObject
import com.parse.SaveCallback
import java.util.concurrent.TimeUnit

public class MainActivity : AppCompatActivity() {

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

    private fun addTimer(duration: Long, unit: TimeUnit) {
        TheService().addTimer(duration, unit)
    }
}
