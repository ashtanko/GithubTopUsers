package me.shtanko.domain

import kotlinx.coroutines.*
import me.shtanko.common.Either
import me.shtanko.common.Failure

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val supervisorJob = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + supervisorJob)
        suspend fun doWork(): Either<Failure, Type> = withContext(scope.coroutineContext) { run(params) }
        GlobalScope.launch(Dispatchers.Main) {
            onResult(doWork())
        }
    }
}