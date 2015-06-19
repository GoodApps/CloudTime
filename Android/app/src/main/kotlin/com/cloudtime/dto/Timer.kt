package com.cloudtime.dto

import com.parse.ParseACL
import com.parse.ParseObject
import java.util.Date

public class Timer(
        val creationTime: Date,
        var startedAt: Date?,
        val durationInSeconds: Long,
        val title: String?,
        /** later wrap this to something like BackendObject class to
         * not expose the dependency to parse.com */
        val backendObject: ParseObject
) {
    object Metadata {
        val CLASS_NAME = "Timer"

    }

    fun deleteEventually() {
        backendObject.deleteEventually()
    }

    fun startTimer() {
        startedAt = Date()
        backendObject.put("startedAt", startedAt) // todo move to delegate property
        backendObject.saveEventually()
    }

    fun stopTimer() {
        startedAt = null
        backendObject.remove("startedAt") // todo move to delegate property
        backendObject.saveEventually()
    }

}
