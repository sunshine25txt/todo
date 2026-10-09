package com.todapp.tod.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todapp.tod.ui.theme.CircleMint
import com.todapp.tod.ui.theme.CircleMintSoft
import com.todapp.tod.ui.theme.MintBg
import com.todapp.tod.ui.theme.Teal

@Composable
fun MintBackdrop(
    circleColor: Color = CircleMintSoft,
    extra: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MintBg)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val r = size.width * 0.42f
            drawCircle(
                color = circleColor.copy(alpha = 0.85f),
                radius = r,
                center = androidx.compose.ui.geometry.Offset(size.width * 0.18f, -r * 0.28f)
            )
            drawCircle(
                color = CircleMint.copy(alpha = 0.7f),
                radius = r * 0.95f,
                center = androidx.compose.ui.geometry.Offset(size.width * 0.55f, -r * 0.22f)
            )
        }
        extra()
        content()
    }
}

@Composable
fun TodField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    password: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder, color = Color(0xFFB0B8B7), fontSize = 14.sp)
        },
        singleLine = true,
        visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (password) KeyboardType.Password else keyboardType
        ),
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Teal
        )
    )
}

@Composable
fun TodButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Teal,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun DecorativeCircles(topOffset: Dp = (-40).dp) {
    Box(modifier = Modifier.fillMaxWidth().height(180.dp).offset(y = topOffset)) {
        Box(
            Modifier
                .size(180.dp)
                .offset(x = (-40).dp, y = (-20).dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(CircleMintSoft.copy(alpha = 0.9f))
        )
        Box(
            Modifier
                .size(170.dp)
                .offset(x = 90.dp, y = (-30).dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(CircleMint.copy(alpha = 0.75f))
        )
    }
}
