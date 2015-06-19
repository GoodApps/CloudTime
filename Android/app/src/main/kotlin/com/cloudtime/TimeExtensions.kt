package com.cloudtime

import java.util.concurrent.TimeUnit

fun Long.toMillis(unit: TimeUnit) = TimeUnit.MILLISECONDS.convert(this, unit)

fun Long.toSeconds(unit: TimeUnit) = TimeUnit.SECONDS.convert(this, unit)
