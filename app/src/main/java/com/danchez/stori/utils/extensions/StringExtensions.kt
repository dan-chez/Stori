package com.danchez.stori.utils.extensions

import androidx.core.util.PatternsCompat

fun String?.isEmailFormatValid(): Boolean {
    return this?.let { PatternsCompat.EMAIL_ADDRESS.matcher(it).matches() } ?: false
}
