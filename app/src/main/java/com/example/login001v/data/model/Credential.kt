package com.example.login001v.data.model

data class Credential (val username:String, val password:String){
    // objeto para que pueda acceder a la clase
    companion object{
        val Admin =Credential(username="admin", password="123")
    }

}