package ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Vibration
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.radioui.RadioStation
import com.example.radioui.sampleStations

@Composable
fun MainAudioPlayer(
    modifier: Modifier = Modifier,
    station: RadioStation = sampleStations.first(),///Recibe la emisora seleccionada
    onNextStation: () -> Unit,
    onPreviousStation: () -> Unit
){
    var isPlaying by rememberSaveable { mutableStateOf(true)}
    var volume by rememberSaveable { mutableFloatStateOf(0.75f) }
    var view = LocalView.current

    fun performHapticFeedback(){
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }
    //compomente principal/contenedor
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        //Column bloque principal
        Column(
            modifier= Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            //Row contenedor de informacion/estado emisora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //en vivo
                Surface(
                    color = Color(0xFFFFE4E6),
                    shape = RoundedCornerShape(50)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE11D48))
                        )
                        Spacer(modifier = Modifier.width((6.dp)))
                        Text(
                            text = "En Vivo",
                            color = Color(0xFF9F1239),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                // numero de FM sintonizada
                Surface(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(50)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = Color(0xFF475569),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier= Modifier.width(4.dp))
                        Text(
                            text = "${station.frequency}",
                            color = Color(0xFF475569),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = station.category.uppercase(),
                color = Color(0xFF4338CA),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = station.name,//mostrar nombre emisora
                color = Color(0xFF0F172A),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text ="${station.frequency} • ${station.listenersCount}",//mostar datos extra de emisora
                color = Color(0xFF64748B),
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color((0xFFF8FAFC)))
                    .padding(14.dp)
            ){ //Column barras de audio
                Column(modifier = Modifier.fillMaxWidth()){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val barHeights = listOf(
                            20,
                            36,
                            12,
                            8,
                            38,
                            26,
                            42,
                            8,
                            32,
                            22,
                            16,
                            8,
                            34,
                            44,
                            18,
                            10,
                            28,
                            20,
                            32
                        )
                        val barColors = listOf(
                            Color(0xFF4338CA),
                            Color(0xFF4338CA),
                            Color(0xFF06B6D4),
                            Color(0xFF4338CA),
                            Color(0xFF0D9488),
                            Color(0xFF4338CA),
                            Color(0xFF4338CA),
                            Color(0xFF06B6D4),
                            Color(0xFF4338CA),
                            Color(0xFF4338CA),
                            Color(0xFF0D9488),
                            Color(0xFF4338CA),
                            Color(0xFF4338CA),
                            Color(0xFF06B6D4),
                            Color(0xFF4338CA),
                            Color(0xFF4338CA),
                            Color(0xFF0D9488),
                            Color(0xFF4338CA),
                            Color(0xFF06B6D4)
                        )
                        barHeights.forEachIndexed { index, height ->
                            Box(
                                modifier = Modifier
                                    .width(8.dp)
                                    .height(height.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(barColors[index % barColors.size])
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF059669))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Modulacion UI Antena",
                                color = Color(0xFF334155),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Latencia: 0.8s",
                            color = Color(0xFF475569),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            //Botones de control
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {performHapticFeedback()},
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFFF1F5F9),CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Timer,
                        contentDescription = "Temporizador",
                        tint = Color(0xFF334155)
                    )
                }
                IconButton(onClick = { //boton anterior
                    performHapticFeedback()
                    onPreviousStation()
                }) {

                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Anterior",
                        tint = Color(0xFF1E293B),
                        modifier = Modifier.size(28.dp)
                    )
                }

                IconButton(//boton play
                    onClick = {
                        performHapticFeedback()
                        isPlaying = !isPlaying
                    },
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color(0xFF3730A3), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(//boton siguiente
                    onClick = {
                        performHapticFeedback()
                        onNextStation()

                    }) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Siguiente",
                        tint = Color(0xFF1E293B),
                        modifier = Modifier.size(28.dp)
                    )
                }
                IconButton(
                    onClick = { performHapticFeedback() },
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFFF1F5F9), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.VolumeUp,
                        contentDescription = "Volumen",
                        tint = Color(0xFF334155)
                    )
                }
            }
            // --- control de volumen---
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeMute,
                    contentDescription = null,
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Slider(
                    value = volume,
                    onValueChange = {
                        volume = it
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Transparent,
                        activeTrackColor = Color(0xFF4338CA),
                        inactiveTrackColor = Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${(volume * 100).toInt()}%",
                    color = Color(0xFF1E293B),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            // mensaje de retroalimentacion haptica
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Outlined.Vibration,
                    contentDescription = null,
                    tint = Color(0xFF4338CA),
                    modifier = Modifier
                        .size(16.dp)
                        .padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Retroalimentación háptica activada (Vibración al pulsar)",
                    color = Color(0xFF64748B),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }
        }

    }
}
@Preview(showBackground = true, name = "Reproductor Principal - Modo Claro")
@Composable
fun MainAudioPlayerPreview() {
    // Usamos la emisora de prueba por defecto y lambdas vacías para la vista previa
    MainAudioPlayer(
        station = sampleStations.first(),
        onNextStation = {},
        onPreviousStation = {}
    )
}