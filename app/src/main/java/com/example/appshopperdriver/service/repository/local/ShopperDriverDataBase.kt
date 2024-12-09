package com.example.appshopperdriver.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appshopperdriver.data.entities.DriverDTO

@Database(entities = [DriverDTO::class], version = 1)
abstract class ShopperDriverDataBase : RoomDatabase() {

    abstract fun DriverDAO(): DriverDAO

    companion object {

        //syngleton
        private lateinit var instance: ShopperDriverDataBase

        fun getDataBase(context: Context) : ShopperDriverDataBase{
            if (!::instance.isInitialized){
                synchronized(ShopperDriverDataBase::class){ //evita que treads executem a instancia ao mesmo tempo
                    instance = return androidx.room.Room.databaseBuilder(context, ShopperDriverDataBase::class.java, "shopperdriverdb")
                        //.addMigrations(MIGRATION_1_2) //adiciona a opção de scripts para atualizações de versões
                        .allowMainThreadQueries()
                        .build()
                }

            }

            return instance
        }


        private val MIGRATION_1_2: Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                // database.execSQL("DELETE FROM Guest")
                //se tiver alguma alteração no banco adicionar os script aqui e na addMigrations na instancia
            }

        }

    }

}