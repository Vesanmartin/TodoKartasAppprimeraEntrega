package com.example.login001v.data.model
data class CardResponse(
    val data: List<Post> // La lista real de cartas
    // Se pueden añadir "page", "pageSize", etc. si se necesitan
)

data class Post(
    val id: String,
    val name: String, // Nombre de la carta
    val supertype: String, // Tipo: Pokémon, Trainer, Energy, etc.
    val hp: String?, // HP de la carta, puede ser nulo para Trainer/Energy
    val types: List<String>?, // Tipo de Energía (Grass, Fire, etc.)
    val images: CardImages // Objeto que contiene las URLs de las imágenes
)

data class CardImages(
    val small: String, // URL de la imagen pequeña
    val large: String // URL de la imagen grande
)