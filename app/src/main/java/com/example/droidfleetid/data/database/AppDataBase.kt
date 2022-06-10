package com.example.droidfleetid.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.droidfleetid.data.DeviceEntityDbModel

@Database(entities = [DeviceEntityDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    companion object {

        private var db: AppDataBase? = null
        private const val DB_NAME = "droidfleet.db"
        private val LOCK = Any()
        fun getInstanse(application: Application): AppDataBase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(application, AppDataBase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }
    abstract fun droidFleetDao(): DroidFleetDao
}