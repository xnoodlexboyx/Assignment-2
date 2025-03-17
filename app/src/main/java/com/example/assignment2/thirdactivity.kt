package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.assignment2.ui.theme.Assignment2Theme

class ThirdActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
    }

    private var capturedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                ThirdScreen(
                    onCaptureImageClick = { openCamera() },
                    capturedImage = capturedBitmap
                )
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            capturedBitmap = imageBitmap
            setContent {
                Assignment2Theme {
                    ThirdScreen(
                        onCaptureImageClick = { openCamera() },
                        capturedImage = capturedBitmap
                    )
                }
            }
        }
    }
}

@Composable
fun ThirdScreen(
    onCaptureImageClick: () -> Unit,
    capturedImage: Bitmap?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // "Capture Image" button
        Button(onClick = onCaptureImageClick) {
            Text(text = "Capture Image")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // If there's a captured image, show it; else display a text placeholder which should be in line with what the video said
        if (capturedImage != null) {
            Image(
                bitmap = capturedImage.asImageBitmap(),
                contentDescription = "Captured Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        } else {
            Text("No image captured yet.")
        }
    }
}
