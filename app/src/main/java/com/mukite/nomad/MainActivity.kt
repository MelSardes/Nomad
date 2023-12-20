package com.mukite.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mukite.lenomadmvp.ui.theme.NomadTheme
import com.mukite.nomad.ui.NomadApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomadTheme {
                NomadApp()
            }
        }
    }
}
