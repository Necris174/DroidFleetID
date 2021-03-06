package com.example.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.DeviceEntityDbModel

@Dao
interface DroidFleetDao {
    @Query("SELECT * FROM device_entities")
     fun getDeviceEntityList(): LiveData<List<DeviceEntityDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceEntityList(devices : List<DeviceEntityDbModel>)

    @Query("DELETE FROM device_entities")
    suspend fun deleteEntityList()

}