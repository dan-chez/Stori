package com.danchez.stori.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Bitmap.bitmapToUri(context: Context): Uri? {
    var uri: Uri? = null
    try {
        val file = File(context.externalCacheDir, "image.jpg")
        val outputStream = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        uri = Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return uri
}
