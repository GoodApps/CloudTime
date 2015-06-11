package com.cloudtime

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.parse.ParseObject
import com.parse.SaveCallback

public class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obj = ParseObject("MySecondClass")
        obj.put("myFirstField", 6)
        obj.saveInBackground()
    }
}
