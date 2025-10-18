package com.example.login001v.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login001v.data.dao.ProductoDao
import com.example.login001v.data.model.Producto


@Database(
    entities = [Producto::class],
    version=1,
    exportSchema = false   // evita warning
)

abstract class ProductoDatabase: RoomDatabase(){
    abstract fun productoDao(): ProductoDao

    companion object{
        @Volatile
        private var INSTANCE: ProductoDatabase? =null

        fun getDatabase(context: Context): ProductoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "producto_database"
                ).build() // builder
                INSTANCE = instance
                instance
            } // fin return



        }

    }// fin companion




}// fin abstract