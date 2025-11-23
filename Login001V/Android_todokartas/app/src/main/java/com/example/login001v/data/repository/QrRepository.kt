package com.example.login001v.data.repository

import com.example.login001v.data.model.QrResult

class QrRepository {
    fun processQrContent(content: String): QrResult {
        // Aquí podrías guardar o procesar el QR en una BD o API
        return QrResult(content)
    }
}