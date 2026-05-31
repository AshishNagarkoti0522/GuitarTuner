package com.example.guitartuner.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun AppSettingsToggle(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    // M3 Premium Feature: Thumb ke andar icon dikhana hai ya nahi
    showIcon: Boolean = true
) {
    // 🔥 UX HACK: Poori Row ko clickable banaya hai, sirf switch ko nahi
    Row(
        modifier = modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Switch // Accessibility/TalkBack ke liye zaroori
            )
            .padding(horizontal = 16.dp, vertical = 12.dp), // Standard touch target size
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Text left, Switch right
    ) {
        // 1. Setting ka Text
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        // 2. Material 3 Switch with Icon
        val icon: (@Composable () -> Unit)? = if (showIcon && checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    // M3 standard icon size thumb ke andar ke liye
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }

        Switch(
            checked = checked,
            onCheckedChange = null, // 🔥 Null isliye kyunki Row click handle kar rahi hai
            enabled = enabled,
            thumbContent = icon
        )
    }
}