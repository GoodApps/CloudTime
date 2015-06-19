package com.cloudtime.dto

import com.cloudtime.toMillis
import com.parse.ParseObject
import java.util.Date
import java.util.concurrent.TimeUnit

data class Timer(
        var startedAt: Date?,
        val durationInSeconds: Long,
        val title: String?,
        /** later wrap this to something like BackendObject class to
         * not expose the dependency to parse.com */
        val backendObject: ParseObject
) {
    fun started() = startedAt != null

    fun endsAt() = startedAt!!.getTime() + durationInSeconds.toMillis(TimeUnit.SECONDS)

    fun deleteEventually() {
        backendObject.deleteEventually()
    }

    fun startTimer() {
        startedAt = Date()
        backendObject.put(::startedAt.name, startedAt) // todo move to delegate property
        backendObject.saveEventually()
    }

    fun stopTimer() {
        startedAt = null
        backendObject.remove(::startedAt.name) // todo move to delegate property
        backendObject.saveEventually()
    }

}
