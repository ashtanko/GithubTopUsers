package dev.shtanko.data.local

import dev.shtanko.data.local.dao.UsersDao
import dev.shtanko.data.local.model.UserLocalModel
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(
    private val usersDao: UsersDao
) {
    //fun getAll(): Observable<List<UserLocalModel>> = usersDao.getUsers()

    fun insertAll(users: List<UserLocalModel>) = usersDao.insertAll(*users.toTypedArray())
}