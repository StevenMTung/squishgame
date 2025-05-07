package br.com.steventung.squishgame.ui.screens.game

import android.app.Activity
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.steventung.squishgame.ui.screens.camera.CameraPreview
import br.com.steventung.squishgame.ui.screens.camera.CameraViewModel
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions

@OptIn(ExperimentalGetImage::class)
@Composable
fun PoseDetectionScreen(
    onBackToHomeScreen: () -> Unit = {},
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val view = LocalView.current

    DisposableEffect(Unit) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val window = (activity)?.window

        window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }

        val controller = window?.let { WindowInsetsControllerCompat(it, view) }
        controller?.hide(WindowInsetsCompat.Type.navigationBars())
        controller?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            window?.let { WindowCompat.setDecorFitsSystemWindows(it, true) }
            controller?.show(WindowInsetsCompat.Type.navigationBars())
        }
    }


    val viewModel = hiltViewModel<CameraViewModel>()
    val state by viewModel.uiState.collectAsState()

    var pose by remember { mutableStateOf<Pose?>(null) }

    val options = remember {
        PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
    }

    val poseDetector = remember { PoseDetection.getClient(options) }

    val imageAnalyzer = ImageAnalysis.Analyzer { imageProxy ->
        imageProxy.image?.let { inputImage ->
            viewModel.setScreenSize(Pair(imageProxy.height, imageProxy.width))

            poseDetector
                .process(inputImage, imageProxy.imageInfo.rotationDegrees)
                .addOnSuccessListener { detectedPose ->
                    pose = detectedPose
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                viewModel.setPreviewSize(Pair(it.width, it.height))
            }
    ) {

        // 1 Camera Controller
        val cameraController = remember {
            LifecycleCameraController(context).apply {
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    imageAnalyzer
                )
            }
        }

        // 2 Camera Preview
        CameraPreview(cameraController)

        // 3 Pose Overlay
        pose?.let {
            GameOverlayScreen(
                pose = it,
                viewModel = viewModel,
                state = state,
                onBackToHomeScreen = onBackToHomeScreen,
            )
        }
    }
}

