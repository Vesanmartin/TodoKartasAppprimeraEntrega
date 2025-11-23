package com.example.login001v.data.repository

import android.content.Context
import com.example.login001v.data.database.ProductoDatabase
import com.example.login001v.data.model.Credential

class AuthRepository(
    private val context: Context? = null
) {

    // Ojo: Si existe contexto → usa BD real
    // Si NO existe (previews) → usa solo Credential.Admin
    private val dao = context?.let {
        ProductoDatabase.getDatabase(it).credentialDao()
    }

    /**
     * LOGIN REAL CON ROOM
     */
    suspend fun login(username: String, password: String): Boolean {

        // Caso 1: sin BD (preview): valida contra Admin
        if (dao == null) {
            return username == Credential.Admin.username &&
                    password == Credential.Admin.password
        }

        // Caso 2: BD real: buscar usuario
        val user = dao.getCredential(username)

        return user != null && user.password == password
    }

    /**
     * REGISTRO EN ROOM
     */
    suspend fun register(username: String, password: String, email:String): Boolean {

        if (dao == null) return false   // no debería ocurrir

        val exists = dao.getCredential(username)

        if (exists != null) {
            return false  // usuario ya existe
        }

        // insertar usuario nuevo
        dao.insertCredential(
            Credential(
                username = username,
                password = password
            )
        )
        return true
    }
} // fin Authrepository modificado para agregar usuario
