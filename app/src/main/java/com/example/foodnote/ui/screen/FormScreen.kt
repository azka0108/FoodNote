package com.example.foodnote.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodnote.data.model.Food

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    existingFood: Food? = null,   // ✅ kalau null = tambah, kalau ada data = edit
    onSaveClick: (Food) -> Unit
) {
    // ✅ State data
    var name by remember { mutableStateOf(existingFood?.name ?: "") }
    var calories by remember { mutableStateOf(existingFood?.calories?.toString() ?: "") }
    var imageUri by remember { mutableStateOf(existingFood?.imageUrl ?: "") }

    // 🎀 Warna tema pink
    val pinkBackground = Color(0xFFFFF1F5)
    val pinkPrimary = Color(0xFFFF80AB)
    val pinkText = Color(0xFFD81B60)

    // ✅ Launcher untuk pilih gambar dari galeri
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { imageUri = it.toString() }   // Simpan URI jadi String
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pinkBackground) // 🎀 Background pink lembut
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🎀 Judul lucu
        Text(
            text = if (existingFood != null) "✨ Edit Makanan ✨" else "🍓 Tambah Makanan 🍰",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = pinkText,
                fontSize = 24.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Input Nama
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama Makanan") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = pinkPrimary,
                focusedLabelColor = pinkText,
                cursorColor = pinkPrimary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Input Kalori
        TextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text("Kalori") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = pinkPrimary,
                focusedLabelColor = pinkText,
                cursorColor = pinkPrimary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Preview gambar kalau sudah dipilih
        if (imageUri.isNotEmpty()) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Preview Gambar",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // 🎀 Tombol pilih gambar dari galeri
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(pinkPrimary),
            shape = RoundedCornerShape(50)
        ) {
            Text("📷 Pilih Gambar dari Galeri", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🎀 Tombol Simpan / Update
        Button(
            onClick = {
                if (name.isNotBlank() && calories.isNotBlank() && imageUri.isNotEmpty()) {
                    onSaveClick(
                        Food(
                            id = existingFood?.id ?: 0,
                            name = name,
                            calories = calories.toIntOrNull() ?: 0,
                            imageUrl = imageUri
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(pinkPrimary),
            shape = RoundedCornerShape(50)
        ) {
            Text(if (existingFood != null) "✨ Update ✨" else "💾 Simpan", color = Color.White)
        }
    }
}
