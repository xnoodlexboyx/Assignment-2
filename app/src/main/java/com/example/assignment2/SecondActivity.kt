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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignment2.ui.theme.Assignment2Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SecondScreen(onReturnToMain = {
                        // Return to MainActivity
                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)
                    })
                }
            }
        }
    }
}

@Composable
fun SecondScreen(onReturnToMain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mobile Software Engineering Challenges:")
        Spacer(modifier = Modifier.height(20.dp))

        Text("1. Battery & Resource Optimization")
        Text("2. Supporting Various Screen Sizes")
        Text("3. Security & Data Privacy")
        Text("4. Network Connectivity & Performance")
        Text("5. Lifecycle & Background Task Management")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onReturnToMain) {
            Text("Main Activity")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    Assignment2Theme {
        SecondScreen(onReturnToMain = {})
    }
}
