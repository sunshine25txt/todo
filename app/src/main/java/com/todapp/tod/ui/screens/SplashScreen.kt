package com.todapp.tod.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todapp.tod.ui.components.MintBackdrop
import com.todapp.tod.ui.components.SplashIllustration
import com.todapp.tod.ui.components.TodButton
import com.todapp.tod.ui.theme.Ink
import com.todapp.tod.ui.theme.Muted

@Composable
fun SplashScreen(onGetStarted: () -> Unit) {
    MintBackdrop {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(120.dp))
            SplashIllustration()
            Spacer(Modifier.height(28.dp))
            Text(
                "Gets things with TODs",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Lorem ipsum dolor sit amet consectetur. Eget sit nec et euismod. Consequat nisl etiam sit interdum tristique ut eget sed.",
                fontSize = 13.sp,
                color = Muted,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(Modifier.weight(1f))
            TodButton("Get Started", onGetStarted)
            Spacer(Modifier.height(12.dp))
        }
    }
}
