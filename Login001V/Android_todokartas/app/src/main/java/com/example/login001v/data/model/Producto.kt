package com.example.login001v.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val nombre: String,
    val precio:String,
    val cantidad:String,
    val direccion: String,
    val edicionEspecial: Boolean,
    val agregarPuntos:Boolean
)