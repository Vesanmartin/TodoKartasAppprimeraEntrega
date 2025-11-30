// Archivo: PostViewModel.kt
package com.example.login001v.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.model.Post
import com.example.login001v.data.repository.PostRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// ViewModel que mantiene el estado de las cartas (posts) obtenidas
class PostViewModel(
    // Esto permite inyectar un fake/mock en los tests si quieres más adelante
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    // 1. Estado del texto de búsqueda ingresado por el usuario
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // 2. Estado de la lista de cartas obtenidas
    //    internal: el test puede acceder a _cardList, la UI usa cardList
    internal val _cardList = MutableStateFlow<List<Post>>(emptyList())
    val cardList: StateFlow<List<Post>> = _cardList   // SOLO una vez

    // 3. Estado de la carga (útil para mostrar spinner)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Inicializa la búsqueda automática cuando cambia el query
        setupSearchFlow()
    }

    // Actualiza el texto de búsqueda desde la UI
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    // Corrutina para buscar cartas (llamada directa, útil para el botón de búsqueda)
    fun searchCards() {
        val query = _searchQuery.value.trim()
        if (query.isBlank()) {
            _cardList.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiQuery = "name:$query*"
                val result = repository.searchCards(apiQuery)
                _cardList.value = result
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error al buscar cartas: ${e.localizedMessage}", e)
                _cardList.value = emptyList() // Deja la lista vacía en caso de error
            } finally {
                _isLoading.value = false
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchFlow() {
        _searchQuery
            .debounce(300L)              // Espera 300 ms después de que deja de escribir
            .filter { it.isNotBlank() }  // Solo busca si hay texto
            .onEach { searchCards() }    // Llama a searchCards cada vez que cambia
            .launchIn(viewModelScope)
    }
}
