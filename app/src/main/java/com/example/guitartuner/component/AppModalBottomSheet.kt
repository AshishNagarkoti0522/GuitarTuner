package com.example.guitartuner.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    // skipPartiallyExpanded = true se sheet seedha full height khulegi (half open nahi hogi)
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    title: String? = null, // Optional: Agar upar heading deni ho
    content: @Composable ColumnScope.() -> Unit // 🔥 Yahan tumhara custom UI aayega
) {
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            // Drag handle apne aap upar dikhega M3 me
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    // Niche se navigation bar jitni jagah chhodne ke liye
                    .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 16.dp)
            ) {
                // Agar title bheja hai, toh render karo
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                // Tumhara actual content yahan render hoga
                content()
            }
        }
    }
}