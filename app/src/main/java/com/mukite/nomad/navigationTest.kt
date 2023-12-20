import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    var selectedScreen by remember { mutableStateOf(Screen.Home) }

    val iconSize = 24.dp

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black,
                contentColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedScreen == Screen.Home,
                    onClick = { selectedScreen = Screen.Home },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(iconSize)
                        )
                    },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedScreen == Screen.Bookings,
                    onClick = { selectedScreen = Screen.Bookings },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Bookings",
                            modifier = Modifier.size(iconSize)
                        )
                    },
                    label = { Text("Bookings") }
                )
                NavigationBarItem(
                    selected = selectedScreen == Screen.User,
                    onClick = { selectedScreen = Screen.User },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User",
                            modifier = Modifier.size(iconSize)
                        )
                    },
                    label = { Text("User") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedScreen) {
                Screen.Home -> HomeScreen()
                Screen.Bookings -> BookingsScreen()
                Screen.User -> UserScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    // Implement the content of the Home screen here
    Text("Home Screen")
}

@Composable
fun BookingsScreen() {
    // Implement the content of the Bookings screen here
    Text("Bookings Screen")
}

@Composable
fun UserScreen() {
    // Implement the content of the User screen here
    Text("User Screen")
}

enum class Screen {
    Home,
    Bookings,
    User
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
