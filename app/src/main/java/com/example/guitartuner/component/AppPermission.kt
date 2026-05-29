package com.example.guitartuner.component

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun AppPermission(
    onPermissionGranted: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Check karo ki app khulte hi permission hai ya nahi
    var isPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Popup (Dialogue) dikhane wala launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        isPermissionGranted = isGranted // User ne Allow/Deny click kiya toh state update
    }

    // Jaise hi screen UI par aaye, permission maango (agar nahi hai toh)
    LaunchedEffect(Unit) {
        if (!isPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    // Condition: UI kya dikhana hai
    if (isPermissionGranted) {
        onPermissionGranted() // Permission mil gayi, Tuner ka UI dikhao!
    } else {
        // Permission nahi mili, toh center me ek text dikha do
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Bhai Mic ki permission de do, warna Tuner nahi chalega 🎙️")
        }
    }
}