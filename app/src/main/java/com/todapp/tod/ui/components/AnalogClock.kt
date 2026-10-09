package com.todapp.tod.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClock(modifier: Modifier = Modifier) {
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = System.currentTimeMillis()
            delay(1000)
        }
    }
    val cal = Calendar.getInstance().apply { timeInMillis = now }
    val hours = cal.get(Calendar.HOUR)
    val minutes = cal.get(Calendar.MINUTE)
    val seconds = cal.get(Calendar.SECOND)

    Canvas(modifier = modifier.size(150.dp)) {
        val r = size.minDimension / 2f
        val c = Offset(size.width / 2f, size.height / 2f)
        drawCircle(Color.White, r)
        drawCircle(Color(0xFFE4EEEC), r, style = Stroke(width = 3f))

        for (i in 0 until 12) {
            val a = Math.toRadians((i * 30 - 90).toDouble())
            val inner = r * 0.78f
            val outer = r * 0.90f
            drawLine(
                Color(0xFF9AAEAB),
                Offset(c.x + inner * cos(a).toFloat(), c.y + inner * sin(a).toFloat()),
                Offset(c.x + outer * cos(a).toFloat(), c.y + outer * sin(a).toFloat()),
                strokeWidth = if (i % 3 == 0) 3.5f else 2f,
                cap = StrokeCap.Round
            )
        }

        fun hand(value: Float, length: Float, width: Float, color: Color) {
            val a = Math.toRadians((value * 6 - 90).toDouble())
            drawLine(
                color,
                c,
                Offset(c.x + length * cos(a).toFloat(), c.y + length * sin(a).toFloat()),
                width,
                StrokeCap.Round
            )
        }

        val hourVal = (hours % 12) * 5f + minutes / 12f
        hand(hourVal, r * 0.45f, 7f, Color(0xFF4EC8C6))
        hand(minutes.toFloat(), r * 0.62f, 5f, Color(0xFF2C3A39))
        hand(seconds.toFloat(), r * 0.70f, 2f, Color(0xFF4EC8C6))
        drawCircle(Color(0xFF4EC8C6), 6f, c)
    }
}
