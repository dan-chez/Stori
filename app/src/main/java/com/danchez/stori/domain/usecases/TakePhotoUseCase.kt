package com.danchez.stori.domain.usecases

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface CameraPermisUseCase {
    suspend operator fun invoke()
}

class TakePhotoUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
): CameraPermisUseCase {
    override suspend fun invoke() {


    }

}
