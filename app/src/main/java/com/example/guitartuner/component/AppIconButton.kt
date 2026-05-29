package com.example.guitartuner.component

import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors()
) {
    val tooltipState = rememberTooltipState()

    TooltipBox (
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Above
        ),
        tooltip = {
            PlainTooltip {
                Text(contentDescription)
            }
        },
        state = tooltipState
    ) {
        FilledIconButton(
            onClick = onClick,
            modifier = modifier.size(size),
            enabled = enabled,
            colors = colors
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                // Pro-Tip: Icon ka size button ke size se thoda chota rakha hai taaki padding theek dikhe
                modifier = Modifier.size(size)
            )
        }
    }
}