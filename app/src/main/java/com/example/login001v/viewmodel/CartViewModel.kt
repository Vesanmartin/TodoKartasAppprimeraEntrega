package com.example.login001v.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

// ViewModel compartido para gestionar el estado del carrito de compras.
class CartViewModel : ViewModel() {

    // Estado privado y mutable de la lista de items en el carrito.
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    // Estado público de solo lectura.
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // Flujos de totales.
    private val _subtotal = MutableStateFlow(0)
    val subtotal: StateFlow<Int> = _subtotal

    private val _total = MutableStateFlow(0)
    val total: StateFlow<Int> = _total

    init {
        // Cada vez que cambia la lista, recalculamos totales
        _cartItems
            .onEach { items ->
                val newSubtotal = items.sumOf { it.subtotalItem }
                val newTotal = items.sumOf { it.totalItem }
                _subtotal.value = newSubtotal
                _total.value = newTotal
            }
            .launchIn(viewModelScope)
    }

    /**
     * Añade un producto al carrito o incrementa la cantidad si ya existe.
     */
    fun addItem(newItem: CartItem) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.nombre == newItem.nombre }

            if (existingItem != null) {
                currentItems.map { item ->
                    if (item.nombre == newItem.nombre) {
                        item.copy(cantidad = item.cantidad + newItem.cantidad)
                    } else {
                        item
                    }
                }
            } else {
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
                currentItems.filter { it.nombre != itemToUpdate.nombre }
            } else {
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
     * Actualiza si el item lleva certificación o no.
     */
    fun updateCertification(itemToUpdate: CartItem, newCert: Boolean) {
        _cartItems.update { currentItems ->
            currentItems.map { item ->
                if (item.nombre == itemToUpdate.nombre) {
                    item.copy(conCertificacion = newCert)
                } else {
                    item
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
