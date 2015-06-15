package com.cloudtime.service

import com.cloudtime.dto.Timer
import com.parse.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

public final class TheService {

    fun addTimer(duration: Long, unit: TimeUnit, title: String) {
        val timer = ParseObject(Timer.Metadata.CLASS_NAME)
        timer.put(Timer::durationInSeconds.name, TimeUnit.SECONDS.convert(duration, unit))
        timer.put(Timer::title.name, title)
        timer.saveInBackground()
    }

    public fun loadTimers(): Observable<List<Timer>> {
        val timersObservable = Observable.create { subscriber: Subscriber<in List<Timer>> ->
            try {
                val list = ParseQuery.getQuery<ParseObject>(Timer.Metadata.CLASS_NAME).find()
                        .map { po : ParseObject -> Timer(
                                po.getCreatedAt(),
                                po.getLong(Timer::durationInSeconds.name),
                                po.getString(Timer::title.name))
                        }
                subscriber.onNext(list)
            } catch (e: ParseException) {
                subscriber.onError(e)
            }
            subscriber.onCompleted()
        }
        return timersObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
