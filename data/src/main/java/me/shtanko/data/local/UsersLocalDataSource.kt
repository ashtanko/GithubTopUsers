package me.shtanko.data.local

import me.shtanko.data.local.dao.UsersDao
import me.shtanko.data.local.model.UserLocalModel
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(
    private val usersDao: UsersDao
) {
    //fun getAll(): Observable<List<UserLocalModel>> = usersDao.getUsers()

    fun insertAll(users: List<UserLocalModel>) = usersDao.insertAll(*users.toTypedArray())
}