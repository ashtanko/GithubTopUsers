package me.shtanko.network.extension

import me.shtanko.common.Either
import me.shtanko.common.Either.Left
import me.shtanko.common.Failure
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
    return try {
        val response = call.execute()
        when (response.isSuccessful) {
            true -> Either.Right(transform((response.body() ?: default)))
            false -> Left(Failure.ServerError())
        }
    } catch (exception: Throwable) {
        Left(Failure.ServerError())
    }
}