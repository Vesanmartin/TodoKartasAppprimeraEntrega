package com.example.login001v.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.login001v.data.repository.AuthRepository

class LoginViewModel (
    private val repo: AuthRepository = AuthRepository()
): ViewModel(){ // inicio VM
    var uiState by mutableStateOf(LoginUiState() )

    fun onUsernameChange(value:String){
        uiState=uiState.copy(username=value,error=null)
    }

    fun onPasswordChange(value:String){
        uiState=uiState.copy(password=value,error=null)
    }

    fun submit(onSucces:(String) -> Unit  ){

        uiState=uiState.copy(isLoading=true, error=null)

        val oK=repo.login(uiState.username.trim(),uiState.password)

        // Resultados
        if(oK) onSucces(uiState.username.trim())
        else uiState =uiState.copy(error="Credenciales invalidas")

    }


}// fin VM