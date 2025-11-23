package com.example.login001v.ui.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repo = AuthRepository(application)

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, error = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }

    //Para el correo
    fun onEmailChange(value: String) {
        uiState = uiState.copy(email = value, error = null)
    }

    // confirmar contraseña
    fun onConfirmPasswordChange(value: String) {
        uiState = uiState.copy(confirmPassword = value, error = null)
    }

    fun submit(onSuccess: (String) -> Unit) {

        val username = uiState.username.trim()
        val password = uiState.password

        if (username.isBlank() || password.isBlank()) {
            uiState = uiState.copy(error = "Ingresa usuario y contraseña")
            return
        }

        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val ok = repo.login(username, password)

            if (ok) {
                onSuccess(username)
            } else {
                uiState = uiState.copy(error = "La contraseña no es correcta")
            }

            uiState = uiState.copy(isLoading = false)
        }
    }

    fun register(onSuccess: () -> Unit) {
        val username = uiState.username.trim()
        val email = uiState.email.trim()
        val password = uiState.password
        val confirm = uiState.confirmPassword

        // ✔ Validaciones básicas
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirm.isBlank()) {
            uiState = uiState.copy(error = "Completa todos los campos")
            return
        }

        if (password != confirm) {
            uiState = uiState.copy(error = "Las contraseñas no coinciden")
            return
        }

        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            //  cambia la firma de tu AuthRepository si hoy sólo recibe user/pass
            val ok = repo.register(username, password, email)

            if (ok) {
                onSuccess()
            } else {
                uiState = uiState.copy(error = "Usuario ya existe!!")
            }

            uiState = uiState.copy(isLoading = false)
        }
    }
}
