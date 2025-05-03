package com.example.assignment2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.assignment2.ui.theme.Assignment2Theme

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted, navigate to SecondActivity
            startSecondActivity()
        } else {
            // Permission denied, show a message
            Toast.makeText(
                this,
                "MSE412 permission is required to access challenges list",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        onExplicitButtonClick = { checkAndRequestPermission(explicit = true) },
                        onImplicitButtonClick = { checkAndRequestPermission(explicit = false) },
                        onImageActivityButtonClick = { startThirdActivity() }
                    )
                }
            }
        }
    }

    private fun checkAndRequestPermission(explicit: Boolean) {
        when {
            // Check if permission is already granted
            ContextCompat.checkSelfPermission(
                this,
                "com.example.assignment2.MSE412"
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, start activity
                if (explicit) {
                    startSecondActivity()
                } else {
                    startSecondActivityImplicitly()
                }
            }
            // Request permission
            else -> {
                requestPermissionLauncher.launch("com.example.assignment2.MSE412")
            }
        }
    }

    private fun startSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    private fun startSecondActivityImplicitly() {
        val intent = Intent("com.example.assignment2.SHOW_CHALLENGES")
        startActivity(intent)
    }

    private fun startThirdActivity() {
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun MainScreen(
    onExplicitButtonClick: () -> Unit,
    onImplicitButtonClick: () -> Unit,
    onImageActivityButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show name & student ID
        Text(text = "Isaac Fuchs - 2417876")

        Spacer(modifier = Modifier.height(20.dp))

        // Assignment 2: Explicit Intent with permission request
        Button(onClick = onExplicitButtonClick) {
            Text("Start Activity Explicitly")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Assignment 2: Implicit Intent with permission request
        Button(onClick = onImplicitButtonClick) {
            Text("Start Activity Implicitly")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Assignment 4: Go to ThirdActivity
        Button(onClick = onImageActivityButtonClick) {
            Text("View Image Activity")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Assignment2Theme {
        MainScreen(
            onExplicitButtonClick = {},
            onImplicitButtonClick = {},
            onImageActivityButtonClick = {}
        )
    }
}
