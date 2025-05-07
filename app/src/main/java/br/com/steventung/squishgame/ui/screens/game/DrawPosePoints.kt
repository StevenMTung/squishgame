package br.com.steventung.squishgame.ui.screens.game

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun DrawPosePoints(
    scaleFactor: Float,
    postScaleWidthOffset:Float,
    leftIndexPosition: PointF,
    leftShoulderPosition: PointF,
    leftElbowPosition: PointF,
    rightIndexPosition: PointF,
    rightShoulderPosition: PointF,
    rightElbowPosition: PointF,
    nosePosition: PointF,
) {
    val radius = 10f
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .graphicsLayer { scaleX = -1f }
        ,
        onDraw = {
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(
                    setScale(
                        coordinate = leftIndexPosition.x,
                        scaleFactor = scaleFactor,
                        extraOffset = postScaleWidthOffset
                    ),
                    setScale(
                        coordinate = leftIndexPosition.y,
                        scaleFactor = scaleFactor
                    )
                )
            )
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(
                    setScale(
                        coordinate = leftShoulderPosition.x,
                        scaleFactor = scaleFactor,
                        extraOffset = postScaleWidthOffset
                    ),
                    setScale(
                        coordinate = leftShoulderPosition.y,
                        scaleFactor = scaleFactor
                    )
                )
            )
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(
                    setScale(
                        coordinate = nosePosition.x,
                        scaleFactor = scaleFactor,
                        extraOffset = postScaleWidthOffset
                    ),
                    setScale(coordinate = nosePosition.y, scaleFactor = scaleFactor)
                )
            )

            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(
                    setScale(
                        coordinate = rightIndexPosition.x,
                        scaleFactor = scaleFactor,
                        extraOffset = postScaleWidthOffset
                    ),
                    setScale(
                        coordinate = rightIndexPosition.y,
                        scaleFactor = scaleFactor
                    )
                )
            )

            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(
                    setScale(
                        coordinate = rightShoulderPosition.x,
                        scaleFactor = scaleFactor,
                        extraOffset = postScaleWidthOffset
                    ),
                    setScale(
                        coordinate = rightShoulderPosition.y,
                        scaleFactor = scaleFactor
                    )
                )
            )

            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(
                    setScale(
                        coordinate = rightElbowPosition.x,
                        scaleFactor = scaleFactor,
                        extraOffset = postScaleWidthOffset
                    ),
                    setScale(
                        coordinate = rightElbowPosition.y,
                        scaleFactor = scaleFactor
                    )
                )
            )

            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(
                    setScale(
                        coordinate = leftElbowPosition.x,
                        scaleFactor = scaleFactor,
                        extraOffset = postScaleWidthOffset
                    ),
                    setScale(
                        coordinate = leftElbowPosition.y,
                        scaleFactor = scaleFactor
                    )
                )
            )
        }
    )
}

fun setScale(
    coordinate: Float,
    scaleFactor: Float,
    extraOffset: Float = 0f
): Float {
    return coordinate * scaleFactor - extraOffset
}