package net.ezra.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import net.ezra.R
import net.ezra.navigation.*
import net.ezra.ui.products.fetchProducts
import net.ezra.ui.products.Product

data class Screen(val title: String, val icon: Int)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var productList by remember { mutableStateOf(emptyList<Product>()) }

    val painterCover = painterResource(id = R.drawable.cc)
    val profilePainter = painterResource(id = R.drawable.index)
    val menuPainter = painterResource(id = R.drawable.logo)


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Trapstar mentality")
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(ROUTE_HOME) { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E1E1E),
                    titleContentColor = Color.White,
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (isDrawerOpen) {
                            isDrawerOpen = false
                        }
                    }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.Black)
                        .padding(paddingValues)
                ) {
                    item {
                        // Header with image and text
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = painterCover,
                                contentDescription = "Concert Cover",
                                contentScale = ContentScale.Crop,
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Blue,
                                                Color.Transparent,
                                                Color.Transparent,
                                                Color.Transparent,
                                                Color.Transparent,
                                                Color.Blue,
                                                Color.Blue,
                                            )
                                        )
                                    )
                            )

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopStart,
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(start = 16.dp, top = 16.dp)
                                        .width(34.dp)
                                        .height(34.dp)
                                        .clip(shape = CircleShape),
                                    painter = profilePainter,
                                    contentDescription = "Profile photo",
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopEnd,
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(end = 16.dp, top = 16.dp)
                                        .width(24.dp)
                                        .height(24.dp),
                                    painter = menuPainter,
                                    contentDescription = "Menu",
                                )
                            }

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Concert Catalogue",
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontSize = 24.sp
                                    )
                                    Text(
                                        text = "Find Your Favorite Concerts",
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Dual Buttons
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    navController.navigate(ROUTE_REGISTER)
                                }
                            ) {
                                Text(text = "Register")
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    navController.navigate(ROUTE_VIEW_PROD)
                                }
                            ) {
                                Text(text = "View All Concerts")
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    // Horizontal concert list
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Upcoming Concerts", color = Color.White, fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        LazyRow {
                            items(productList.take(3)) { product ->
                                ProductListItem(product)
                            }
                        }
                    }
                }
            }
        },
        bottomBar = { BottomBar(navController = navController) }
    )

    AnimatedDrawer(
        isOpen = isDrawerOpen,
        onClose = { isDrawerOpen = false }
    )
}

@Composable
fun ProductListItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle product click */ }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Product Image
            Image(
                painter = rememberImagePainter(product.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column {
                Text(text = product.name)
                Text(text = "Price: ${product.price}")
            }
        }
    }
}

@Composable
fun AnimatedDrawer(isOpen: Boolean, onClose: () -> Unit) {
    val drawerWidth = remember { Animatable(if (isOpen) 250f else 0f) }

    LaunchedEffect(isOpen) {
        drawerWidth.animateTo(if (isOpen) 250f else 0f, animationSpec = tween(durationMillis = 300))
    }

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth.value.dp),
        color = Color.DarkGray,
    ) {
        Column {
            Text(text = "Beautiful work done", color = Color.White)
            // Add more items to the drawer here
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(
        elevation = 10.dp,
        backgroundColor = Color(0xFF1E1E1E)
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.White) },
            selected = selectedIndex.value == 0,
            onClick = {
                selectedIndex.value = 0
                navController.navigate(ROUTE_HOME)
            }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = Color.White) },
            selected = selectedIndex.value == 1,
            onClick = {
                selectedIndex.value = 1
                navController.navigate(ROUTE_HOME)
            }
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White) },
            selected = selectedIndex.value == 2,
            onClick = {
                selectedIndex.value = 2
                navController.navigate(ROUTE_DASHBOARD)
            }
        )
    }
}
