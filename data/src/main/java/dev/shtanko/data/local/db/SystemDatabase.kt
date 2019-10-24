package dev.shtanko.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.shtanko.data.local.dao.UsersDao
import dev.shtanko.data.local.model.UserLocalModel

@Database(entities = [UserLocalModel::class], version = 1, exportSchema = false)
abstract class SystemDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object {
        fun newInstance(context: Context): SystemDatabase {
            return Room.databaseBuilder(context, SystemDatabase::class.java, "users-system.db").build()
        }
    }
}