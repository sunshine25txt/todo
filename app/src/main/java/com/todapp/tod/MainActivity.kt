package com.todapp.tod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.todapp.tod.data.AppStore
import com.todapp.tod.ui.screens.DashboardScreen
import com.todapp.tod.ui.screens.LoginScreen
import com.todapp.tod.ui.screens.RegisterScreen
import com.todapp.tod.ui.screens.SplashScreen
import com.todapp.tod.ui.theme.MintBg
import com.todapp.tod.ui.theme.TodTheme

enum class Screen { Splash, Register, Login, Dashboard }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MintBg) {
                    TodApp()
                }
            }
        }
    }
}

@Composable
fun TodApp() {
    val context = LocalContext.current
    val store = remember { AppStore(context) }
    var screen by remember {
        mutableStateOf(if (store.getCurrentUser() != null) Screen.Dashboard else Screen.Splash)
    }

    when (screen) {
        Screen.Splash -> SplashScreen(onGetStarted = { screen = Screen.Register })
        Screen.Register -> RegisterScreen(
            store = store,
            onRegistered = { screen = Screen.Dashboard },
            onSignIn = { screen = Screen.Login }
        )
        Screen.Login -> LoginScreen(
            store = store,
            onLoggedIn = { screen = Screen.Dashboard },
            onSignUp = { screen = Screen.Register }
        )
        Screen.Dashboard -> DashboardScreen(
            store = store,
            onLogout = {
                store.logout()
                screen = Screen.Login
            }
        )
    }
}
