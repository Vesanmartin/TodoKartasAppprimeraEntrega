package com.example.login001v.data.model

// Data class para representar un producto dentro del carrito.
// Asumimos que los detalles del producto (como la imagen) se pueden
// recuperar a través del nombre si es necesario en la UI del carrito.
data class CartItem(
    val nombre: String,
    val precioUnitario: Int,
    var cantidad: Int, // Cantidad seleccionada por el usuario
    val conCertificacion: Boolean = false // Asumimos false por defecto si no viene de la forma
) {
    // Subtotal calculado automáticamente
    val subtotalItem: Int
        get() = precioUnitario * cantidad

    // Total del item incluyendo el costo extra de certificación (si aplica)
    val totalItem: Int
        get() = subtotalItem + if (conCertificacion) 30000 else 0
}