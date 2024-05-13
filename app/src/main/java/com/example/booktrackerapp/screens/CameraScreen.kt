package com.example.booktrackerapp.screens

import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.core.content.ContextCompat


@Composable
fun CameraScreen(navController: NavController){

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context)}

    val lensFacing = remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }

    Box(
        modifier = Modifier
            .fillMaxSize()
        ){

        //Camera Preview
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    PreviewView(context).apply{
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        setBackgroundColor(Color.BLACK)
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also {previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                })

        //Camera Button-Funktionen
            CameraControls(
                modifier = Modifier.align(Alignment.BottomCenter),

                onTakePicture = {
                    val mainExecutor = ContextCompat.getMainExecutor(context)
                    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback(){
                       override fun onCaptureSuccess(image: ImageProxy) {
                           image.toBitmap()
                       }
                   })
                },

                onSwitchCamera = {
                    lensFacing.value = if (lensFacing.value == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else {
                        CameraSelector.DEFAULT_BACK_CAMERA
                    }
                    cameraController.cameraSelector = lensFacing.value
                },

                onCancelPreview = {
                    navController.navigate("homescreen")
                },

                onOpenGallery = {
                    // TODO
                }
            )
        }



}

//ICONS
@Composable
fun CameraControls(
    modifier: Modifier = Modifier,
    onTakePicture: () -> Unit,
    onSwitchCamera: () -> Unit,
    onCancelPreview: () -> Unit,
    onOpenGallery: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        IconButton(onClick = onCancelPreview, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cancel Preview",
                tint = androidx.compose.ui.graphics.Color.White
            )
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onOpenGallery , modifier = Modifier.size(60.dp)) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Switch Camera",
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onTakePicture, modifier = Modifier.size(60.dp)) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Take Picture",
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onSwitchCamera, modifier = Modifier.size(60.dp)) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Switch Camera",
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}