package com.example.radioui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.components.UserProfileHeader
import com.example.radioui.ui.theme.RadioUITheme
import ui.components.MainAudioPlayer
import ui.components.StationCatalog


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadioUITheme {
                // Estado para almacenar la foto capturada en tiempo real
                var capturedPhoto by remember { mutableStateOf<Bitmap?>(null) }
                val scrollState = rememberScrollState()
                var selectedStationId by rememberSaveable { mutableStateOf("1") }
                val currentStation = sampleStations.find { it.id == selectedStationId } ?: sampleStations.first()



                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .background(Color(0xFFF8F9FE))
                        .statusBarsPadding()//adaptar padin superior a cualquier telefono
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    // cabecera
                    UserProfileHeader(
                        userBitmap = capturedPhoto,
                        onPhotoCaptured = { bitmap ->
                            capturedPhoto = bitmap
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    //componente de radio
                    val currentIndex = sampleStations.indexOfFirst { it.id == selectedStationId }.coerceAtLeast(0)
                    MainAudioPlayer(
                        station=currentStation,//seleccionar emisora
                        //funciones para cambiar de emisora
                        onNextStation = {
                            val nextIndex = (currentIndex + 1) % sampleStations.size
                            selectedStationId = sampleStations[nextIndex].id
                        },
                        onPreviousStation = {
                            // Retrocede al índice anterior de forma circular
                            val previousIndex = if (currentIndex - 1 < 0) sampleStations.size - 1 else currentIndex - 1
                            selectedStationId = sampleStations[previousIndex].id
                        }

                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    // Emisoras
                    StationCatalog(
                        stations = sampleStations,//Integrar seleccion de emisora
                        activeStationId = selectedStationId,
                        onStationSelect = { station ->
                            selectedStationId = station.id
                        }
                    )

                }
            }
        }
    }
}
