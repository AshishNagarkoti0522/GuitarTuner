package com.example.guitartuner.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    centerTitle: Boolean = true,
    // 🔥 NAYA PARAMETER: Ye batayega ki scroll hone pe kya karna hai
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface,
    )

    val navigationIcon: @Composable () -> Unit = {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }

    val titleContent: @Composable () -> Unit = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    if (centerTitle) {
        CenterAlignedTopAppBar(
            title = titleContent,
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = topAppBarColors,
            scrollBehavior = scrollBehavior // 🔥 Yahan attach kar diya
        )
    } else {
        TopAppBar(
            title = titleContent,
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = topAppBarColors,
            scrollBehavior = scrollBehavior // 🔥 Yahan attach kar diya
        )
    }
}