package com.example.hubtrackerapp.data.utils

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

object PasswordHasher {
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray(UTF_8)
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(bytes)

        // Конвертируем в hex-строку
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(password: String, hash: String): Boolean {
        return hashPassword(password) == hash
    }
}