package com.danchez.stori.domain.model

import android.net.Uri

data class SignUpModel(
    val photoUri: Uri,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
)
