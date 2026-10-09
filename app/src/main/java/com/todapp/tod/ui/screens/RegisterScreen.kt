package com.todapp.tod.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todapp.tod.data.AppStore
import com.todapp.tod.ui.components.MintBackdrop
import com.todapp.tod.ui.components.TodButton
import com.todapp.tod.ui.components.TodField
import com.todapp.tod.ui.theme.Ink
import com.todapp.tod.ui.theme.Muted
import com.todapp.tod.ui.theme.Teal

@Composable
fun RegisterScreen(
    store: AppStore,
    onRegistered: () -> Unit,
    onSignIn: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    MintBackdrop {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(88.dp))
            Text("Welcome to Onboard!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Ink)
            Spacer(Modifier.height(8.dp))
            Text("Let’s help to meet up your tasks.", fontSize = 14.sp, color = Muted)
            Spacer(Modifier.height(36.dp))
            TodField(name, { name = it }, "Enter your full name")
            Spacer(Modifier.height(14.dp))
            TodField(email, { email = it }, "Enter your Email", keyboardType = KeyboardType.Email)
            Spacer(Modifier.height(14.dp))
            TodField(password, { password = it }, "Enter Password", password = true)
            Spacer(Modifier.height(14.dp))
            TodField(confirm, { confirm = it }, "Confirm password", password = true)
            if (error != null) {
                Spacer(Modifier.height(10.dp))
                Text(error!!, color = androidx.compose.ui.graphics.Color(0xFFD64545), fontSize = 13.sp)
            }
            Spacer(Modifier.height(28.dp))
            TodButton("Register") {
                error = when {
                    password != confirm -> "Passwords do not match."
                    else -> store.register(name, email, password)
                }
                if (error == null) onRegistered()
            }
            Spacer(Modifier.height(18.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Text("Already have an account ? ", color = Muted, fontSize = 13.sp)
                Text(
                    "Sign In",
                    color = Teal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(onClick = onSignIn)
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
