package ui.components

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


fun createImageUri(context: Context): Uri {
    val directory = File(context.getExternalFilesDir(null), "Pictures")
    if (!directory.exists()) {
        directory.mkdirs()
    }
    val file = File.createTempFile("selected_image_", ".jpg", directory)
    val authority = "${context.packageName}.fileprovider"
    return FileProvider.getUriForFile(context, authority, file)
}
@Composable
fun UserProfileHeader(
    userBitmap: Bitmap?,
    onPhotoCaptured:(Bitmap) -> Unit
){
    val context = LocalContext.current
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess && tempImageUri != null) {
            try {
                // Usamos el operador let para garantizar que uri no es nulo
                tempImageUri?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()
                    if (bitmap != null) {
                        onPhotoCaptured(bitmap)
                    }
                }
            } catch (e: Exception) {
                Log.e("UserProfileHeader", "Error decodificando la imagen: ${e.message}")
            }
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        if(isGranted){
            val uri = createImageUri(context)
            tempImageUri = uri
            cameraLauncher.launch(uri)
        }else{
            Log.d(TAG,"Permiso de cámara denegado")
        }
    }
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Muestra la foto tomada o el avatar predeterminado
                if(userBitmap != null){
                    Image(
                        bitmap = userBitmap.asImageBitmap(),
                        contentDescription = "Foto de Perfil",
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .border(2.dp,Color(0xFF6366F1),CircleShape)
                    )
                }else{
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E7FF)),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar borrador",
                            tint = Color(0xFF4338CA)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column{

                    Text(
                        text = "Estudiante IUD",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color= Color(0xFF6366F1)
                    )
                    Text(
                        text = "Facultad de Ingenierias",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
            Button(
                onClick = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    )

                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        val uri = createImageUri(context)
                        tempImageUri = uri
                        cameraLauncher.launch(uri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                colors= ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text="Tomar Foto", fontSize = 11.sp)
            }

        }

    }

}


