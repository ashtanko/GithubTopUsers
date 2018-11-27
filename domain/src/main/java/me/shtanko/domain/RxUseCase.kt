/*
 * Copyright (c) 2018 Alexey Shtanko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.shtanko.domain

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import me.shtanko.domain.executor.PostExecutionThread
import me.shtanko.domain.executor.ThreadExecutor
import java.lang.Exception

abstract class RxUseCase<T, Params>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    private val disposables = CompositeDisposable()

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    abstract fun buildObservable(params: Params): Observable<T>

    open fun execute(
        observer: DisposableObserver<T>,
        params: Params
    ) {

        try {
            val observable = buildObservable(params).subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())

            addDisposable(observable.subscribeWith(observer))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}