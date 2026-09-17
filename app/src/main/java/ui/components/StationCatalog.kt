package ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.radioui.RadioStation
import com.example.radioui.sampleStations

@Composable

fun StationCatalog(
    modifier: Modifier = Modifier,
    stations: List<RadioStation> = sampleStations,
    activeStationId: String = "1",
    onStationSelect: (RadioStation) -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Catálogo de Emisoras",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Text(
                text = "${stations.size} disponibles",
                fontSize = 12.sp,
                color = Color(0xFF64748B),
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stations.forEach { station ->
            StationItemCard(
                station = station,
                isSelected = station.id== activeStationId,
                onSelect = { onStationSelect(station) }
            )
        }
    }
}

@Composable
fun StationItemCard(
    station: RadioStation,
    isSelected: Boolean,
    onSelect: () -> Unit
){
    val view = LocalView.current
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFEEF2FF) else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ){
                // Ícono / Badge visual de la emisora
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) Color(0xFF4338CA) else Color(0xFFF1F5F9)),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = if (station.isLive) Icons.Default.CellTower else Icons.Default.Radio,
                        contentDescription = null,
                        tint = if (isSelected) Color.White else Color(0xFF475569),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                // Información de la emisora
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = station.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF0F172A)
                        )
                        if (station.isLive) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE11D48))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${station.category} • ${station.frequency}",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = station.listenersCount,
                        fontSize = 10.sp,
                        color = Color(0xFF4338CA),
                        fontWeight = FontWeight.Medium
                    )
                }

            }
            // Botón Sintonizar / Seleccionar
            Button(
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    onSelect()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF4338CA) else Color(0xFFF1F5F9),
                    contentColor = if (isSelected) Color.White else Color(0xFF334155)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Sintonizar",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isSelected) "Sintonizado" else "Escuchar",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}
