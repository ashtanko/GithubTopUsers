package me.shtanko.data.repository

import io.reactivex.Observable
import me.shtanko.data.local.UsersLocalDataSource
import me.shtanko.data.local.model.UserLocalModel
import me.shtanko.data.remote.UsersRemoteDataSource
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val localDataSource: UsersLocalDataSource,
    private val remoteDataSource: UsersRemoteDataSource
) {

    fun getAll(page: Int, perPage: Int, since: Int): Observable<List<UserLocalModel>> {
        val local = localDataSource.getAll().filter { it.isEmpty().not() }

        val remote = remoteDataSource.getAll(page, perPage, since).map { users ->
            users.map { user ->
                UserLocalModel(user.id, user.login, user.avatarUrl)
            }
        }.doOnNext { users ->
            localDataSource.insertAll(users)
        }

        return Observable.concat(local, remote)
            .lastElement() //load only from network
            .toObservable()
    }

}