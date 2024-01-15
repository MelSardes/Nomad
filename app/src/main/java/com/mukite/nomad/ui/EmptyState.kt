package com.mukite.nomad.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mukite.nomad.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyState(viewModel: NomadViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = uiState.currentPageName,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onBack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.working_animation))
                val logoAnimationState = animateLottieCompositionAsState(
                    composition = composition,
                    speed = 1.5f,
                    iterations = LottieConstants.IterateForever
                )
                LottieAnimation(
                    modifier = Modifier.fillMaxSize(0.6f),
                    composition = composition,
                    progress = { logoAnimationState.progress }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Page en cours de travaux", modifier = Modifier, style = MaterialTheme.typography.headlineSmall)
        }
    }
}