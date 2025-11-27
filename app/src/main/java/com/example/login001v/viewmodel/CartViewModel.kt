package com.example.login001v.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Necesario para launchIn
import com.example.login001v.data.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn // Necesario para iniciar la corrutina de recolección
import kotlinx.coroutines.flow.onEach // Necesario para procesar cada cambio
import kotlinx.coroutines.flow.update

// ViewModel compartido para gestionar el estado del carrito de compras.
class CartViewModel : ViewModel() {

    // Estado privado y mutable de la lista de items en el carrito.
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    // Estado público de solo lectura.
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // Declaración de los flujos de totales.
    private val _subtotal = MutableStateFlow(0)
    val subtotal: StateFlow<Int> = _subtotal

    private val _total = MutableStateFlow(0)
    val total: StateFlow<Int> = _total

    init {
        // Inicializa la lógica de cálculo de totales en una corrutina.
        // Usamos onEach para reaccionar a cada cambio en _cartItems.
        _cartItems
            .onEach { items ->
                val newSubtotal = items.sumOf { it.subtotalItem }
                val newTotal = items.sumOf { it.totalItem }
                // Actualizamos los flujos de totales.
                _subtotal.value = newSubtotal
                _total.value = newTotal
            }
            // Lanza la recolección dentro del ámbito del ViewModel
            // para que se detenga automáticamente cuando el ViewModel se destruya.
            .launchIn(viewModelScope)
    }

    /**
     * Añade un producto al carrito o incrementa la cantidad si ya existe.
     */
    fun addItem(newItem: CartItem) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.nombre == newItem.nombre }

            if (existingItem != null) {
                // El producto ya existe: crea una nueva lista con la cantidad actualizada.
                currentItems.map { item ->
                    if (item.nombre == newItem.nombre) {
                        item.copy(cantidad = item.cantidad + newItem.cantidad)
                    } else {
                        item
                    }
                }
            } else {
                // Producto nuevo: lo añade a la lista.
                currentItems + newItem
            }
        }
    }

    /**
     * Modifica la cantidad de un item existente.
     * Si la nueva cantidad es 0 o menor, elimina el item.
     */
    fun updateQuantity(itemToUpdate: CartItem, newQuantity: Int) {
        _cartItems.update { currentItems ->
            if (newQuantity <= 0) {
                // Eliminar el item
                currentItems.filter { it.nombre != itemToUpdate.nombre }
            } else {
                // Actualizar la cantidad
                currentItems.map { item ->
                    if (item.nombre == itemToUpdate.nombre) {
                        item.copy(cantidad = newQuantity)
                    } else {
                        item
                    }
                }
            }
        }
    }

    /**
     * Elimina un item del carrito.
     */
    fun removeItem(itemToRemove: CartItem) {
        _cartItems.update { currentItems ->
            currentItems.filter { it.nombre != itemToRemove.nombre }
        }
    }

    /**
     * Vacía el carrito completamente.
     */
    fun clearCart() {
        _cartItems.value = emptyList()
    }
}