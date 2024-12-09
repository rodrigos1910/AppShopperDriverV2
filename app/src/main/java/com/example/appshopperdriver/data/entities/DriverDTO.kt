package com.example.appshopperdriver.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Driver",
    indices = [Index(value = ["codigo"], unique = true)]
)
data class DriverDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "codigo")
    val codigo: String,
    @ColumnInfo(name = "nome")
    val name: String
)