package com.example.login001v.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.database.ProductoDatabase
import com.example.login001v.data.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    // DAO
    private val productoDao = ProductoDatabase.getDatabase(application).productoDao()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    // Carga inicial de productos
    init {
        viewModelScope.launch(Dispatchers.IO) {
            productoDao.getAll().collect { lista ->
                _productos.value = lista
            }
        }
    }

    // CREATE
    fun guardarProducto(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            productoDao.insert(producto)
        }
    }

    // UPDATE
    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            productoDao.update(producto)
        }
    }

    // DELETE
    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            productoDao.delete(producto)
        }
    }
}// fin productoviewmodel

