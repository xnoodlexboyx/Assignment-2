package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                // We'll pass the current capturedBitmap to ThirdScreen
                ThirdScreen(
                    onCaptureImageClick = { openCamera() },
                    capturedImage = capturedBitmap
                )
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.resolveActivity(packageManager)?.let {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as? Bitmap
            capturedBitmap = photo
            // Rebuild the UI with the captured image
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
    // but not necessarily captured anything yet.
    var cameraOpened by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button to launch the camera
        Button(
            onClick = {
                cameraOpened = true  // Mark that user attempted to capture
                onCaptureImageClick()
            }
        ) {
            Text("Capture Image")
        }

        Spacer(modifier = Modifier.height(20.dp))

        when {
            capturedImage != null -> {
                Image(
                    bitmap = capturedImage.asImageBitmap(),
                    contentDescription = "Captured Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            cameraOpened -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Black)
                )
            }
            else -> {
                Text("No image captured yet.")
            }
        }
    }
}
