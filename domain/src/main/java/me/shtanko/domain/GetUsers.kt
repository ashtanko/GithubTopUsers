package me.shtanko.domain

import me.shtanko.data.entity.User
import me.shtanko.common.Either
import me.shtanko.common.Failure
import javax.inject.Inject

open class GetUsers @Inject constructor(
    //private val mainRepository: MainRepository
) : UseCase<List<User>, Triple<Int, Int, Int>>() {
    override suspend fun run(params: Triple<Int, Int, Int>): Either<Failure, List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override suspend fun run(params: Triple<Int, Int, Int>): Either<Failure, List<User>> {
//        return mainRepository.getUsers(page = params.third, perPage = params.second, since = params.third)
//    }
}
