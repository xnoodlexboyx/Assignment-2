package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.assignment2.ui.theme.Assignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the full name and student ID
        Text(text = "Isaac F - 1412323")

        Spacer(modifier = Modifier.height(20.dp))

        // Button to start SecondActivity EXPLICITLY
        Button(onClick = {
            val explicitIntent = Intent(context, SecondActivity::class.java)
            context.startActivity(explicitIntent)
        }) {
            Text("Start Activity Explicitly")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Button to start SecondActivity IMPLICITLY
        Button(onClick = {
            // gotta match <intent-filter> action in SecondActivity
            val implicitIntent = Intent("com.example.assignment2.SHOW_CHALLENGES")
            context.startActivity(implicitIntent)
        }) {
            Text("Start Activity Implicitly")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Assignment2Theme {
        MainScreen()
    }
}
