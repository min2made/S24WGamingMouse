package kr.ac.kumoh.ce.s20201086.s24wgamingmouse

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import java.text.NumberFormat
import java.util.Locale
import android.net.Uri
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

enum class MouseScreen {
    Mouse,
    MouseDetail,
    Site
}

@Composable
// 사이트 리스트 페이지
fun SiteList(
    viewModel: MouseViewModel = viewModel(),
) {
    val siteList by viewModel.siteList.observeAsState(emptyList())
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top, // 맨 위부터 시작
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        siteList.forEach { site ->
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(site.site_url)) // site_url로 이동
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(0.dp), // 네모난 모양
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#${site.background_color.substring(2)}")), // 색상 코드 변환 (gpt 사용)
                    contentColor = Color(android.graphics.Color.parseColor("#${site.text_color.substring(2)}")) // 텍스트 색상 변환 (gpt 사용)
                )
            ) {
                Text(text = site.title, fontSize = 16.sp)
            }
        }
    }
}



@Composable
// 마우스 리스트 페이지
fun MouseList(
    viewModel: MouseViewModel = viewModel(),
    onNavigate: (String) -> Unit,
) {
    val mouseList by viewModel.mouseList.observeAsState(emptyList())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 16.dp,
            bottom = 8.dp
        )
    ) {
        items(mouseList) { mouse ->
            MouseItem(mouse, onNavigate)
        }
    }
}

@Composable
fun MouseDetail(mouse: Mouse) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            mouse.title,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = "${mouse.image_url}",
            contentDescription = "마우스 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextPrice(mouse.price)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("${mouse.purchase_url}")
            )
            context.startActivity(intent)
        }) {
            Text("Danawa 페이지로 이동하기")
        }
        Spacer(modifier = Modifier.height(16.dp))
        mouse.detail?.let {
            Text(
                text = it.replace("\\n","\n"),
                fontSize = 25.sp,
                textAlign = TextAlign.Left,
                lineHeight = 35.sp
            )
        }
    }
}

@Composable
fun MouseItem(
    mouse: Mouse,
    onNavigate: (String) -> Unit,
    ) {

    Card(
        modifier = Modifier
            .clickable {
                onNavigate(MouseScreen.MouseDetail.name + "/${mouse.id}")
            },
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = "${mouse.image_url}",
                contentDescription = "마우스 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextTitle(mouse.title)
                TextPrice(mouse.price)
            }
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(
        title,
        fontSize = 30.sp,
        lineHeight = 35.sp
    )
}

@Composable
fun TextPrice(price: Int) {
    val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(price) + "₩"

    Text(formattedPrice, fontSize = 20.sp)
}

@Composable
fun DrawerSheet(
    drawerState: DrawerState,
    onNavigate: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            label = { Text("관련 사이트 리스트") },
            selected = false,
            onClick = {
                onNavigate(MouseScreen.Site.name)
                scope.launch {
                    drawerState.close()
                }
            },
        )
        NavigationDrawerItem(
            label = { Text("로지텍 유+무선 마우스 리스트") },
            selected = false,
            onClick = {
                onNavigate(MouseScreen.Mouse.name)
                scope.launch {
                    drawerState.close()
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = { Text("로지텍 유무선 마우스") },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "메뉴 아이콘"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF00A8E3),
            titleContentColor = Color(0xFFFFFFFF),
        ),
    )
}