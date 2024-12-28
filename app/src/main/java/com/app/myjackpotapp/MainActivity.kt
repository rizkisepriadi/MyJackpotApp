package com.app.jackpotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.min
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JackpotApp()
        }
    }
}

@Composable
fun JackpotApp() {
    var counter by remember { mutableIntStateOf(0) }
    var jackpotProbability by remember { mutableFloatStateOf(0.01f) }
    var showDialog by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = counter.toString(),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (counter % 2 == 0) "Status: Genap" else "Status: Ganjil",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = {
                counter++
                if (counter % 2 != 0 && counter > 10) {
                    val isJackpot = Random.nextFloat() < jackpotProbability
                    if (isJackpot) {
                        isButtonEnabled = false
                        showDialog = true
                    } else {
                        jackpotProbability = min(jackpotProbability + 0.01f, 0.05f)
                    }
                }
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text(text = "Jackpot")
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selamat!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Kamu mendapatkan 'Jackpot' pada angka $counter",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}