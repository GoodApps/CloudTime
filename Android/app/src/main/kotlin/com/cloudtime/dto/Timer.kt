package com.cloudtime.dto

import java.util.*

public class Timer(
        val creationTime: Date,
        val durationInSeconds: Long,
        val title: String?
) {
    object Metadata {
        val CLASS_NAME = "Timer"

    }

}
