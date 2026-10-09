package com.todapp.tod.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.todapp.tod.ui.theme.Teal

@Composable
fun SplashIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(260.dp, 180.dp)) {
        val w = size.width
        val h = size.height

        // Person
        val px = w * 0.28f
        drawCircle(Color(0xFF2C3A39), radius = 14f, center = Offset(px, h * 0.28f))
        drawRoundRect(
            color = Teal,
            topLeft = Offset(px - 18f, h * 0.36f),
            size = Size(36f, 42f),
            cornerRadius = CornerRadius(10f, 10f)
        )
        drawRoundRect(
            color = Color(0xFF2C3A39),
            topLeft = Offset(px - 16f, h * 0.72f),
            size = Size(14f, 36f),
            cornerRadius = CornerRadius(6f, 6f)
        )
        drawRoundRect(
            color = Color(0xFF2C3A39),
            topLeft = Offset(px + 2f, h * 0.72f),
            size = Size(14f, 36f),
            cornerRadius = CornerRadius(6f, 6f)
        )
        drawLine(Teal, Offset(px + 18f, h * 0.42f), Offset(px + 40f, h * 0.32f), 8f, StrokeCap.Round)

        fun card(x: Float, y: Float, done: Boolean) {
            drawRoundRect(
                Color.White,
                Offset(x, y),
                Size(70f, 88f),
                CornerRadius(10f, 10f)
            )
            drawRoundRect(
                Color(0xFFE8F4F2),
                Offset(x, y),
                Size(70f, 88f),
                CornerRadius(10f, 10f),
                style = Stroke(width = 2f)
            )
            repeat(4) { i ->
                drawRoundRect(
                    Color(0xFFD5E6E3),
                    Offset(x + 10f, y + 16f + i * 14f),
                    Size(if (i == 3) 28f else 48f, 6f),
                    CornerRadius(3f, 3f)
                )
            }
            drawCircle(if (done) Teal else Color(0xFFBFD9D6), 8f, Offset(x + 70f, y + 6f))
        }
        card(w * 0.48f, h * 0.18f, true)
        card(w * 0.68f, h * 0.38f, true)
    }
}

@Composable
fun LoginIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(220.dp, 150.dp)) {
        val w = size.width
        val h = size.height

        // Child
        drawCircle(Color(0xFF3A4A48), 12f, Offset(w * 0.38f, h * 0.28f))
        drawRoundRect(Color(0xFF5B9BD5), Offset(w * 0.30f, h * 0.38f), Size(34f, 40f), CornerRadius(10f))
        drawRoundRect(Color(0xFF2C3A39), Offset(w * 0.32f, h * 0.74f), Size(12f, 28f), CornerRadius(5f))
        drawRoundRect(Color(0xFF2C3A39), Offset(w * 0.48f, h * 0.74f), Size(12f, 28f), CornerRadius(5f))

        // Adult
        drawCircle(Color(0xFF3A4A48), 14f, Offset(w * 0.62f, h * 0.22f))
        val hair = Path().apply {
            addOval(
                androidx.compose.ui.geometry.Rect(
                    offset = Offset(w * 0.52f, h * 0.10f),
                    size = Size(40f, 28f)
                )
            )
        }
        drawPath(hair, Color(0xFF2A3332))
        drawRoundRect(Color(0xFFE56B6F), Offset(w * 0.50f, h * 0.34f), Size(44f, 52f), CornerRadius(14f))
        drawRoundRect(Color(0xFF2C3A39), Offset(w * 0.54f, h * 0.78f), Size(14f, 24f), CornerRadius(5f))
        drawRoundRect(Color(0xFF2C3A39), Offset(w * 0.68f, h * 0.78f), Size(14f, 24f), CornerRadius(5f))

        // Book
        drawRoundRect(Color(0xFF4EC8C6), Offset(w * 0.28f, h * 0.48f), Size(28f, 22f), CornerRadius(4f))
        drawRoundRect(Color.White, Offset(w * 0.30f, h * 0.50f), Size(24f, 18f), CornerRadius(2f))
    }
}
