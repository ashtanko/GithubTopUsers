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

package me.shtanko.domain.executor

import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobExecutor @Inject constructor(

) : ThreadExecutor {

    companion object {
        private const val CORE_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5
        private const val KEEP_ALIVE_TIME = 10L
    }

    private val threadPoolExecutor =
        ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            SECONDS,
            LinkedBlockingDeque(),
            JobThreadFactory()
        )

    override fun execute(runnable: Runnable?) {
        this.threadPoolExecutor.execute(runnable)
    }

    private inner class JobThreadFactory : ThreadFactory {

        var counter = 0

        override fun newThread(runnable: Runnable?): Thread {
            return Thread(runnable, "android_${++counter}")
        }
    }
}