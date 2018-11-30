package me.shtanko.data

import io.reactivex.Scheduler
import me.shtanko.domain.Schedulers
import javax.inject.Inject

class AppSchedulers @Inject constructor() : Schedulers {
    override val subscribeOn: Scheduler
        get() = io.reactivex.schedulers.Schedulers.computation()
    override val observeOn: Scheduler
        get() = io.reactivex.android.schedulers.AndroidSchedulers.mainThread()

}