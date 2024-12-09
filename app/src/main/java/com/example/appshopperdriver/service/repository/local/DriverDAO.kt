package com.example.appshopperdriver.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appshopperdriver.data.entities.DriverDTO

@Dao
interface DriverDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(drivers: List<DriverDTO>)


    @Query("SELECT * FROM Driver ")
    fun list():  List<DriverDTO>

}