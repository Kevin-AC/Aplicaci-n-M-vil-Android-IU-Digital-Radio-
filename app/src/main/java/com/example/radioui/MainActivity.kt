package com.example.radioui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.components.UserProfileHeader
import com.example.radioui.ui.theme.RadioUITheme
import ui.components.MainAudioPlayer
import ui.components.UserProfileHeader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadioUITheme {
                // Estado para almacenar la foto capturada en tiempo real
                var capturedPhoto by remember { mutableStateOf<Bitmap?>(null) }
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8F9FE))
                        .padding(top = 46.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    // Renderizamos la cabecera pasando el estado y la función para actualizarlo
                    UserProfileHeader(
                        userBitmap = capturedPhoto,
                        onPhotoCaptured = { bitmap ->
                            capturedPhoto = bitmap
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    MainAudioPlayer()
                }
            }
        }
    }
}