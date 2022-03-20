package com.dc.canvasgame

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dc.canvasgame.ui.theme.CanvasGameTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    var points by remember {
        mutableStateOf(0)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                text = "Point: $points",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = {
                isTimerRunning =! isTimerRunning
            })
            {
                if (isTimerRunning) Text(text = "Reset") else Text(text = "Start")
            }
            CountdownTimer(isTimerRunning = isTimerRunning){
                    isTimerRunning = false
            }
        }
    }
}

@Composable
fun CountdownTimer(
    time: Int = 30000,
    isTimerRunning: Boolean = false,
    onTimerEnd: () -> Unit = {}
) {
    var curTime by remember {
        mutableStateOf(time)
    }
    LaunchedEffect(key1 = curTime, key2 = isTimerRunning) {
        if (!isTimerRunning) {
            curTime = time
            return@LaunchedEffect
        }
        if (curTime > 0) {
            delay(1000)
            curTime -= 1000
        } else onTimerEnd()
    }
    Text(
        text = (curTime / 1000).toString(),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CanvasGameTheme {
        MyScreen()
    }
}