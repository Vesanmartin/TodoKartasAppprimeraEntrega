package com.example.login001v.data.repository

import com.example.login001v.data.dao.ProductoDao
import com.example.login001v.data.model.Producto
import kotlinx.coroutines.flow.Flow


class ProductoRepository(private val productoDao: ProductoDao) {

    // READ
    fun getProductos(): Flow<List<Producto>> =
        productoDao.getAll()

    // CREATE
    suspend fun insert(producto: Producto) =
        productoDao.insert(producto)

    // UPDATE
    suspend fun update(producto: Producto) =
        productoDao.update(producto)

    // DELETE
    suspend fun delete(producto: Producto) =
        productoDao.delete(producto)


}// fin producto repository