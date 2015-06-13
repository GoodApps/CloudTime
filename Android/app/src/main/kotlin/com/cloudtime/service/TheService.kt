package com.cloudtime.service

import com.cloudtime.dto.Timer
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

public final class TheService {

    fun addTimer(duration: Long, unit: TimeUnit) {
        val timer = ParseObject("Timer")
        timer.put("duration", TimeUnit.SECONDS.convert(duration, unit))
        timer.saveInBackground()
    }

    public fun loadTimers(): Observable<List<Timer>> {
        val timersObservable = Observable.create(object : Observable.OnSubscribe<List<Timer>> {
            override fun call(subscriber: Subscriber<in List<Timer>>) {
                try {
                    val list = ParseQuery.getQuery<ParseObject>("Timer").find()
                            .map { po -> Timer(po.getCreatedAt(), po.getLong("duration")) }
                    subscriber.onNext(list)
                } catch (e: ParseException) {
                    subscriber.onError(e)
                }
                subscriber.onCompleted()
            }
        })
        return timersObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
