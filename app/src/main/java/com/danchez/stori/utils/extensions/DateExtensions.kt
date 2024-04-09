package com.danchez.stori.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDateToString(pattern: String = MMM_DD_YYYY): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(this)
}

const val MMM_DD_YYYY = "MMM dd, yyyy"
