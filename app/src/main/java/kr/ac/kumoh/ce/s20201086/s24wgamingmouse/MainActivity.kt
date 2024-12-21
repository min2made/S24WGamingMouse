package kr.ac.kumoh.ce.s20201086.s24wgamingmouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.s20201086.s24wgamingmouse.ui.theme.S24WGamingMouseTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S24WGamingMouseTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel: MouseViewModel = viewModel()
    val mouseList by viewModel.mouseList.observeAsState(emptyList())
    val navController = rememberNavController()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheet(drawerState) {
                navController.navigate(it) {
                    launchSingleTop = true
                    popUpTo(it) { inclusive = true }
                }
            }
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(drawerState) },
            ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = MouseScreen.Mouse.name,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(route = MouseScreen.Site.name) {
                    SiteList()
                }
                composable(route = MouseScreen.Mouse.name) {
                    MouseList() {
                        navController.navigate(it) {
                            launchSingleTop = true
                            popUpTo(it) { inclusive = true }
                        }
                    }
                }
                composable(
                    route = MouseScreen.MouseDetail.name + "/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    val id = it.arguments?.getInt("id") ?: -1
                    val mouse = mouseList.find { mouse -> mouse.id == id }
                    if (mouse != null)
                        MouseDetail(mouse)
                }
            }
        }
    }
}
