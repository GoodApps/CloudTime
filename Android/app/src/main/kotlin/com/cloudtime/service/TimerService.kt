package com.cloudtime.service

import com.cloudtime.dto.Timer
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import rx.Observable
import rx.Subscriber
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

public class TimerService {

    companion object {
        private val timerClass = "Timer"
        private val durationColumn = "durationInSeconds"
        private val titleColumn = "title"
        private val startedAtColumn = "startedAt"
    }

    fun addTimer(duration: Long, unit: TimeUnit, title: String) {
        val timer = ParseObject(timerClass)
        timer.put(durationColumn, TimeUnit.SECONDS.convert(duration, unit))
        timer.put(titleColumn, title)
        timer.saveInBackground()
    }

    fun loadTimers(): Observable<List<Timer>> {
        return Observable.create { it: Subscriber<in List<Timer>> -> onLoadTimers(it) }
                .subscribeOn(Schedulers.io())
    }

    private fun onLoadTimers(subscriber: Subscriber<in List<Timer>>) {
        try {
            subscriber.onNext(loadTimersImpl())
        } catch (e: ParseException) {
            subscriber.onError(e)
        }
        subscriber.onCompleted()
    }

    private fun loadTimersImpl(): List<Timer> {
        return ParseQuery.getQuery<ParseObject>(timerClass)
                .find()
                .map { createTimer(it) }
    }

    private fun createTimer(po: ParseObject): Timer {
        return Timer(
                po.getDate(startedAtColumn),
                po.getLong(durationColumn),
                po.getString(titleColumn))
    }
}
