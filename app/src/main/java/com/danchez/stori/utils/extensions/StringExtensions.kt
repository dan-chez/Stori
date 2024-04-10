package com.danchez.stori.utils.extensions

import androidx.core.util.PatternsCompat
import java.text.NumberFormat
import java.util.Locale

fun String?.isEmailFormatValid(): Boolean {
    return this?.let { PatternsCompat.EMAIL_ADDRESS.matcher(it).matches() } ?: false
}

fun String.formatToMoney(): String {
    val parsedAmount = this.toDoubleOrNull() ?: return ""
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return formatter.format(parsedAmount)
}

fun String.formatToCard(): String {
    val formattedString = StringBuilder()
    this.filter { it.isDigit() }.forEachIndexed { index, char ->
        formattedString.append(char)
        if ((index + 1) % 4 == 0 && index < this.length - 1) {
            formattedString.append("-")
        }
    }
    return formattedString.toString()
}
