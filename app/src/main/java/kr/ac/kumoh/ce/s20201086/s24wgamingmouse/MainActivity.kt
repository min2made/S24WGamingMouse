package kr.ac.kumoh.ce.s20201086.s24wgamingmouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun MainScreen(viewModel: MouseViewModel = viewModel()) {
    val mouseList by viewModel.mouseList.observeAsState(emptyList())

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MouseResult(
            list = mouseList,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun MouseResult(list: List<Mouse>, modifier: Modifier) {
    Column(
        modifier
    ) {
        Text(list.toString())
    }
}

//@Composable
//fun MainScreen() {
//    val viewModel: SongViewModel = viewModel()
//    val songList by viewModel.songList.observeAsState(emptyList())
//    val navController = rememberNavController()
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            DrawerSheet(drawerState) {
//                navController.navigate(it) {
//                    launchSingleTop = true
//                    popUpTo(it) { inclusive = true }
//                }
//            }
//        },
//        gesturesEnabled = true,
//    ) {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            topBar = {
//                TopBar(drawerState)
//            },
//            bottomBar = {
//                BottomNavigationBar {
//                    navController.navigate(it) {
//                        launchSingleTop = true
//                        popUpTo(it) { inclusive = true }
//                    }
//                }
//            },
//        ) { innerPadding ->
//            NavHost(
//                navController = navController,
//                startDestination = SongScreen.Song.name,
//                modifier = Modifier.padding(innerPadding),
//            ) {
//                composable(route = SongScreen.Singer.name) {
//                    SingerList()
//                }
//                composable(route = SongScreen.Song.name) {
//                    SongList() {
//                        navController.navigate(it) {
//                            launchSingleTop = true
//                            popUpTo(it) { inclusive = true }
//                        }
//                    }
//                }
//                composable(
//                    route = SongScreen.SongDetail.name + "/{id}",
//                    arguments = listOf(navArgument("id") {
//                        type = NavType.IntType
//                    })
//                ) {
//                    val id = it.arguments?.getInt("id") ?: -1
//                    val song = songList.find { song -> song.id == id }
//                    if (song != null)
//                        SongDetail(song)
//                }
//            }
//        }
//    }
//
//}
//
//@Composable
//fun BottomNavigationBar(onNavigate: (String) -> Unit) {
//    NavigationBar {
//        NavigationBarItem(
//            label = {
//                Text("화면 1")
//            },
//            icon = {
//                Icon(
//                    Icons.Filled.Face,
//                    contentDescription = "Song_name"
//                )
//            },
//            selected = false,
//            onClick = {
//                onNavigate(SongScreen.Song.name)
//            }
//        )
//        NavigationBarItem(
//            label = {
//                Text("화면 2")
//            },
//            icon = {
//                Icon(
//                    Icons.Filled.Star,
//                    contentDescription = "screen2 icon"
//                )
//            },
//            selected = false,
//            onClick = {
//                onNavigate(SongScreen.Singer.name)
//            }
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBar(drawerState: DrawerState) {
//    val scope = rememberCoroutineScope()
//
//    CenterAlignedTopAppBar(
//        title = { Text("네비게이션") },
//        navigationIcon = {
//            IconButton(
//                onClick = {
//                    scope.launch {
//                        drawerState.open()
//                    }
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = "menu icon"
//                )
//            }
//        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//        ),
//    )
//}
//
//@Composable
//fun DrawerSheet(
//    drawerState: DrawerState,
//    onNavigate: (String) -> Unit,
//) {
//    val scope = rememberCoroutineScope()
//    ModalDrawerSheet {
//        NavigationDrawerItem(
//            label = { Text("화면 1") },
//            selected = false,
//            onClick = {
//                onNavigate(SongScreen.Song.name)
//                scope.launch {
//                    drawerState.close()
//                }
//            },
//            icon = {
//                Icon(
//                    Icons.Filled.Face,
//                    contentDescription = "Song.name"
//                )
//            }
//        )
//        NavigationDrawerItem(
//            label = { Text("화면 2") },
//            selected = false,
//            onClick = {
//                onNavigate(SongScreen.Singer.name)
//                scope.launch {
//                    drawerState.close()
//                }
//            },
//            icon = {
//                Icon(
//                    Icons.Filled.Star,
//                    contentDescription = "Singer.name"
//                )
//            }
//        )
//    }
//}