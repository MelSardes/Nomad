package com.mukite.nomad.ui

import android.app.Activity
import android.app.ActivityManager
import android.media.Image
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mukite.nomad.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(
    appBarScrollState: TopAppBarState,
    listScrollState: ScrollState,
    modifier: Modifier = Modifier
) {

    val activity = (LocalContext.current as? Activity)
    val exitModalShown = remember { mutableStateOf(false) }

    Surface {
        Column(modifier.padding(horizontal = 16.dp)) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.profile),
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarScrollState)
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = listScrollState)
            ) {

                ProfileSection(modifier = Modifier)
                Divider(modifier = Modifier.padding(36.dp))

                settingList.forEach {
                    if (it.second == "Se deconnecter") {
                        SettingItem(
                            it.first,
                            it.second,
                            exitColor = MaterialTheme.colorScheme.error
                        ) { exitModalShown.value = true }
                    } else SettingItem(it.first, it.second)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        AnimatedVisibility(visible = exitModalShown.value) {
            AlertDialog(
                title = {
                    Text(text = "Se deconnecter")
                },
                icon = {
                    Icon(Icons.Filled.ExitToApp, contentDescription = "Exit")
                },
                text = {
                    Text(text = "Voulez-vous vraiment vous deconnecter ?")
                },
                onDismissRequest = { exitModalShown.value = false },
                confirmButton = {
                    Row {
                        TextButton(
                            onClick = { exitModalShown.value = false }
                        ) { Text("Annuler", color = MaterialTheme.colorScheme.secondary) }

                        TextButton(onClick = { activity?.finish() }
                        ) { Text("Se déconnecter") }
                    }
                },
            )
        }

    }
}

val settingList = listOf(
    Icons.Filled.Person to "Modifier le profil",
    Icons.Filled.List to "Mon historique",
    Icons.Filled.Notifications to "Notifications",
    Icons.Filled.Help to "Aide",
    Icons.Filled.Warning to "FAQ",
    Icons.Filled.Info to "A propos",
    Icons.Filled.ExitToApp to "Se deconnecter",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    exitColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    Card(onClick = { onClick() } ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = exitColor)
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = exitColor
                )
            )
        }
    }
}

@Composable
fun ProfileSection(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfilePhotoSection(modifier = Modifier)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.user_profile_name),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = modifier
            )

            Text(
                text = stringResource(R.string.user_profile_email),
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(R.string.user_profile_location),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun ProfilePhotoSection(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .size(150.dp)
        .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "UserProfile", showSystemUi = true, device = "id:pixel_7")
@Composable
private fun PreviewUserProfile() {
    UserProfile(rememberTopAppBarState(), rememberScrollState())
}