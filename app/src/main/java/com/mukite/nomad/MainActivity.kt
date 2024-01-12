package com.mukite.nomad

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mukite.lenomadmvp.ui.theme.NomadTheme
import com.mukite.nomad.ui.NomadApp


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NomadTheme {
                val systemUiController = rememberSystemUiController()

                // Customize system bars colors
                val statusBarColor = MaterialTheme.colorScheme.surfaceVariant
                val navigationBarColor = MaterialTheme.colorScheme.surfaceVariant
                systemUiController.setStatusBarColor(statusBarColor)
                systemUiController.setNavigationBarColor(navigationBarColor)
                NomadApp()
            }
        }
    }
}
