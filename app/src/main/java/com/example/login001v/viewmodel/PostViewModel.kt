// Archivo: CardViewModel.kt (Reemplaza a PostViewModel.kt)
package com.example.login001v.viewmodel

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

// Viewmodel que mantiene el estado de los datos obtenidos
class PostViewModel: ViewModel(){
    private val repository = PostRepository()

    // 1. Estado del texto de búsqueda ingresado por el usuario
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // 2. Estado de la lista de cartas obtenidas
    private val _cardList = MutableStateFlow<List<Post>>(emptyList()) //Aquí se cambia el internal para los test
    val cardList: StateFlow<List<Post>> = _cardList

    // 3. Estado de la carga (opcional, pero útil)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Inicializa la búsqueda automática cuando cambia el query
        setupSearchFlow()
    }

    // Función para manejar el texto de búsqueda del usuario
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    // Corrutina para buscar cartas (llamada directa, útil para el botón de búsqueda)
    fun searchCards() {
        val query = _searchQuery.value
        if (query.isBlank()) {
            _cardList.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiQuery = "name:$query*"
                _cardList.value = repository.searchCards(apiQuery)
            } catch (e: Exception){
                // *** CAMBIO AQUÍ: Usar Log.e para ver el error en Logcat ***
                android.util.Log.e("CardViewModel", "Error al buscar cartas: ${e.localizedMessage}", e)
                _cardList.value = emptyList() // Asegurar que la lista se vacíe en caso de error
            } finally {
                _isLoading.value = false
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchFlow() {
        _searchQuery
            .debounce(300L) // Espera 300ms después de que el usuario deja de escribir
            .filter { it.isNotBlank() } // Solo busca si hay texto
            .onEach { searchCards() } // Llama a searchCards() cada vez que el texto cambia
            .launchIn(viewModelScope)
    }

}//fin CardViewModel