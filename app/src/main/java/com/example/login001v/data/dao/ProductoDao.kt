package com.example.login001v.data.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.login001v.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao //Data Access Object

interface ProductoDao{

    // Guarda un producto nuevo o reemplaza si ya existe con el mismo ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    // Obtiene todos los productos guardados en la base de datos

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun getAll(): Flow<List<Producto>>

    //actualizar productos
    @Update
    suspend fun update(producto: Producto)

    // delete
    @Delete
    suspend fun delete(producto: Producto)

}// fin producto dao