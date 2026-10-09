package com.todapp.tod.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.todapp.tod.ui.components.LoginIllustration
import com.todapp.tod.ui.components.MintBackdrop
import com.todapp.tod.ui.components.TodButton
import com.todapp.tod.ui.components.TodField
import com.todapp.tod.ui.theme.Ink
import com.todapp.tod.ui.theme.Muted
import com.todapp.tod.ui.theme.Teal

@Composable
fun LoginScreen(
    store: AppStore,
    onLoggedIn: () -> Unit,
    onSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var showReset by remember { mutableStateOf(false) }

    MintBackdrop {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(72.dp))
            Text("Welcome back", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Ink)
            Spacer(Modifier.height(16.dp))
            LoginIllustration()
            Spacer(Modifier.height(28.dp))
            TodField(email, { email = it }, "Enter your Email", keyboardType = KeyboardType.Email)
            Spacer(Modifier.height(14.dp))
            TodField(password, { password = it }, "Enter Password", password = true)
            Spacer(Modifier.height(10.dp))
            Text(
                "Forget password ?",
                color = Teal,
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { showReset = true }
                    .padding(8.dp)
            )
            if (error != null) {
                Text(error!!, color = androidx.compose.ui.graphics.Color(0xFFD64545), fontSize = 13.sp)
            }
            Spacer(Modifier.height(16.dp))
            TodButton("Login", onClick = {
                error = store.login(email, password)
                if (error == null) onLoggedIn()
            })
            Spacer(Modifier.height(18.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("Don’t have an account ? ", color = Muted, fontSize = 13.sp)
                Text(
                    "Sign Up",
                    color = Teal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(onClick = onSignUp)
                )
            }
        }
    }

    if (showReset) {
        var resetEmail by remember { mutableStateOf(email) }
        var newPass by remember { mutableStateOf("") }
        var resetMsg by remember { mutableStateOf<String?>(null) }
        AlertDialog(
            onDismissRequest = { showReset = false },
            title = { Text("Reset password") },
            text = {
                Column {
                    TodField(resetEmail, { resetEmail = it }, "Email")
                    Spacer(Modifier.height(10.dp))
                    TodField(newPass, { newPass = it }, "New password", password = true)
                    if (resetMsg != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(resetMsg!!, fontSize = 13.sp)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val result = store.resetPassword(resetEmail, newPass)
                    if (result == null) {
                        resetMsg = "Password updated. You can log in now."
                    } else {
                        resetMsg = result
                    }
                }) { Text("Update", color = Teal) }
            },
            dismissButton = {
                TextButton(onClick = { showReset = false }) { Text("Close") }
            }
        )
    }
}
