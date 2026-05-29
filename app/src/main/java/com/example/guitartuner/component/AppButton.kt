package com.example.guitartuner.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Button(
        onClick = onClick,
        modifier = modifier, // Great default for responsive layouts
        enabled = enabled,
        shape = shape,
        colors = colors
    ) {
        Text(
            text = text,
            style = textStyle,
            fontWeight = fontWeight
        )
    }
}