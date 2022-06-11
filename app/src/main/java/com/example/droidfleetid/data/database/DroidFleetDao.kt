package com.example.droidfleetid.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.droidfleetid.data.DeviceEntityDbModel

@Dao
interface DroidFleetDao {
    @Query("SELECT * FROM device_entities")
     fun getDeviceEntityList(): LiveData<List<DeviceEntityDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceEntityList(devices : List<DeviceEntityDbModel>)
}