package com.danchez.stori.ui.signup

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danchez.stori.R

@Composable
fun ProfilePicSection(
    imageUri: Uri?,
    onChangePicture: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .placeholder(R.drawable.profile_icon)
                .error(R.drawable.profile_icon)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape,
                ),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(16.dp))
        TextButton(
            onClick = onChangePicture,
        ) {
            Text(
                text = stringResource(id = R.string.change_photo),
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)
            )
        }
    }
}
