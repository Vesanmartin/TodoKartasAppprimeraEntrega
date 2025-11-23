package com.example.login001v.data.model

data class Pedido(
    val nombre: String,
    val precioUnitario: Int,
    val cantidad: Int,
    val conCertificacion: Boolean
) {
    val subtotal: Int = precioUnitario * cantidad
    val total: Int = subtotal + if (conCertificacion) 30000 else 0
}