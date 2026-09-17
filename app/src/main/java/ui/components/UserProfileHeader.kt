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
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


fun getCorrectlyOrientedBitmap(imagePath: String): Bitmap? {
    val bitmap = BitmapFactory.decodeFile(imagePath) ?: return null

    return try {
        val exif = ExifInterface(imagePath)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            else -> return bitmap // Si no requiere rotación
        }

        Bitmap.createBitmap(
            bitmap, 0, 0,
            bitmap.width, bitmap.height,
            matrix, true
        )
    } catch (e: Exception) {
        e.printStackTrace()
        bitmap
    }
}

fun createImageFileAndUri(context: Context): Pair<Uri, File> {
    val file = File.createTempFile("profile_",".jpg",context.cacheDir)
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
    return Pair(uri,file)
}
@Composable
fun UserProfileHeader(
    userBitmap: Bitmap?,
    onPhotoCaptured:(Bitmap) -> Unit
){
    val context = LocalContext.current
    var photoFile by remember { mutableStateOf<File?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val fileToProcess = photoFile ?: File(context.cacheDir, "profile_photo.jpg")
            if (fileToProcess.exists()) {
                val correctedBitmap = getCorrectlyOrientedBitmap(fileToProcess.absolutePath)
                if (correctedBitmap != null) {
                    onPhotoCaptured(correctedBitmap)
                }
            }
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        if(isGranted){
            val (uri,file)= createImageFileAndUri(context)
            photoFile =file
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
                        val (uri,file) = createImageFileAndUri(context)
                        photoFile = file
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


