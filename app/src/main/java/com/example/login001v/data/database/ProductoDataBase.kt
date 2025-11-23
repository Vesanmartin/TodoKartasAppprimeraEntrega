package com.example.login001v.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login001v.data.dao.ProductoDao
import com.example.login001v.data.model.Credential
import com.example.login001v.data.model.Producto
import com.example.login001v.data.dao.CredentialDao

@Database(
    entities = [
        Producto::class,
        Credential::class
    ],
    version = 2,            //una version 2 porque agregamos nueva tabla
    exportSchema = false
)
abstract class ProductoDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    // dao para credenciales login

    abstract fun credentialDao(): CredentialDao

    companion object {
        @Volatile
        private var INSTANCE: ProductoDatabase? = null

        fun getDatabase(context: Context): ProductoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "producto_database"
                )
                    .fallbackToDestructiveMigration() // útil mientras desarrollas
                    .build()
                    .also { INSTANCE = it }
            }
    }
} //fin abstract
