@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodnote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodnote.data.model.Food
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    foodList: List<Food>,
    onAddClick: () -> Unit,
    onDeleteClick: (Food) -> Unit,
    onEditClick: (Food) -> Unit,
    onLogoutClick: () -> Unit
) {
    val pinkBackground = Color(0xFFFFF1F5)
    val pinkPrimary = Color(0xFFFF80AB)
    val pinkText = Color(0xFFD81B60)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🍓 Daftar Makanan Favorit 🍰",
                        color = pinkText,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = pinkPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFE4EC)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = pinkPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Food", tint = Color.White)
            }
        },
        containerColor = pinkBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(foodList) { food ->
                var showDialog by remember { mutableStateOf(false) }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Konfirmasi Hapus") },
                        text = { Text("Apakah Anda yakin ingin menghapus makanan favorit Anda?") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    onDeleteClick(food)
                                    showDialog = false
                                }
                            ) {
                                Text("Ya", color = Color.Red)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Batal")
                            }
                        }
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            food.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = pinkText,
                                fontSize = 20.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "${food.calories} Kalori",
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        AsyncImage(
                            model = food.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    onEditClick(food)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Berhasil melakukan pengeditan")
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(pinkPrimary),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text("Edit", color = Color.White)
                            }

                            Button(
                                onClick = { showDialog = true },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(Color(0xFFFF5252)),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text("Hapus", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
