package com.cloudtime.service

import com.parse.ParseObject
import java.util.concurrent.TimeUnit

public final class TheService {

    fun addTimer(duration: Long, unit: TimeUnit) {
        val timer = ParseObject("Timer")
        timer.put("duration", TimeUnit.SECONDS.convert(duration, unit))
        timer.saveInBackground()
    }
}
