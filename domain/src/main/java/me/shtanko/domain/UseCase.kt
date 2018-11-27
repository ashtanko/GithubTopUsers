package me.shtanko.domain

import kotlinx.coroutines.*
import me.shtanko.common.Either
import me.shtanko.common.Failure

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>


    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) =
        runBlocking {

            val job = async { run(params) }
            launch { onResult(job.await()) }

            //val job = async(CommonPool) { run(params) }
            //launch(Contacts.Intents.UI) { onResult(job.await()) }
        }

    class None
}