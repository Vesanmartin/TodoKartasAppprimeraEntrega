// Archivo: CardScreen.kt (Reemplaza a PostScreen.kt)
package com.example.login001v.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.login001v.viewmodel.PostViewModel
import androidx.lifecycle.viewmodel.compose.viewModel // Para obtener CardViewModel en AppNav
import com.example.login001v.data.model.Post


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()){
    // Observamos los flujos de datos
    val cards by viewModel.cardList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Búsqueda de Cartas Pokémon") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // *** INPUT DE BÚSQUEDA ***
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) }, // Llama al ViewModel en cada cambio
                label = { Text("Buscar cartas por nombre") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // *** INDICADOR DE CARGA ***
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (cards.isEmpty() && searchQuery.isNotBlank()) {
                Text(
                    text = "No se encontraron cartas para \"$searchQuery\".",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else if (cards.isEmpty() && searchQuery.isBlank()) {
                Text(
                    text = "Ingresa un nombre para buscar cartas de Pokémon TCG.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            // *** LISTA DE RESULTADOS (LazyColumn) ***
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(cards) { card ->
                    CardResultItem(card = card)
                }
            }
        }
    }
}

// Composable para mostrar una carta individual
@Composable
fun CardResultItem(card: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Imagen de la carta usando Coil
            Image(
                painter = rememberAsyncImagePainter(card.images.small),
                contentDescription = card.name,
                contentScale = ContentScale.Fit, // Usar Fit para que se vea bien la carta completa
                modifier = Modifier
                    .width(130.dp)
                    .padding(8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Tipo: ${card.supertype}", style = MaterialTheme.typography.bodyMedium)
                card.types?.let {
                    Text(text = "Energía: ${it.joinToString(", ")}", style = MaterialTheme.typography.bodySmall)
                }
                card.hp?.let {
                    Text(text = "HP: $it", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}