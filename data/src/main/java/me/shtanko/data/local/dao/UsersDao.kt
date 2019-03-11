package me.shtanko.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import me.shtanko.data.local.model.UserLocalModel

@Dao
interface UsersDao {

    //@Query("SELECT * FROM Users")
    //fun getUsers(): <List<UserLocalModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: UserLocalModel)
}