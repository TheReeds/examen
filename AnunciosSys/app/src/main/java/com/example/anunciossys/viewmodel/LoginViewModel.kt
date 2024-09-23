package com.example.anunciossys.viewmodel

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    fun login(username: String, password: String): Boolean {
        // Aquí iría la lógica para autenticar al usuario
        return username == "admin" && password == "password" // Ejemplo simple
    }
}
