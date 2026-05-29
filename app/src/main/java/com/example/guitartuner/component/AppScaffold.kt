package com.example.guitartuner.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBarTitle: String? = null,
    onBackClick: (() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    // 1. 🔥 SCROLL ENGINE: Ye decide karega ki TopBar kaise react karega
    // "enterAlwaysScrollBehavior" ka matlab: Niche jao toh chup jao, Upar aao toh dikh jao
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        // 2. 🔥 CONNECTION: Scaffold ko batao ki jab andar ka content scroll ho, toh engine ko bata dena
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (topBarTitle != null) {
                AppTopBar(
                    title = topBarTitle,
                    onBackClick = onBackClick,
                    // 3. 🔥 TopBar ko uska engine de do
                    scrollBehavior = scrollBehavior
                )
            }
        },
        floatingActionButton = floatingActionButton
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content(innerPadding)
        }
    }
}