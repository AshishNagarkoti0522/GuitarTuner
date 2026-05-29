package com.example.guitartuner.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guitartuner.R
import com.example.guitartuner.component.AppButton
import com.example.guitartuner.component.AppIconButton
import com.example.guitartuner.component.AppModalBottomSheet
import com.example.guitartuner.component.AppPermission
import com.example.guitartuner.component.AppScaffold
import com.example.guitartuner.component.AppText
import com.example.guitartuner.model.tuningModesList
import com.example.guitartuner.viewmodel.TunerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(vm : TunerViewModel = viewModel()) {
    AppScaffold(
        topBarTitle = stringResource(R.string.app_name)
    ) {
        AppPermission {
            val currentHz by vm.frequency.collectAsState()

            LaunchedEffect(Unit) { vm.startTuner() }

            DisposableEffect(Unit) {
                onDispose {
                    vm.stopTuner()
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ){
                    AppText(
                        modifier = Modifier
                            .padding(bottom = 5.dp),
                        text = vm.tuningModeName,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    AppIconButton(
                        icon = Icons.Filled.MoreVert,
                        contentDescription = "Tuning Options",
                        onClick = { vm.onClickBottomSheet() },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        size = 40.dp
                    )
                }
                TurnerNeedle(
                    currentHz = currentHz,
                    targetHz = vm.targetPitch
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        painter = painterResource(id = R.drawable.guitar_outline),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    for (i in 1..6) {
                        val yBias = when(i) {
                            1 -> 0.39f
                            2 -> -0.11f
                            3 -> -0.63f
                            4 -> -0.63f
                            5 -> -0.11f
                            else -> 0.39f
                        }
                        val xBias = when(i) {
                            1 -> 0.86f
                            2 -> 0.86f
                            3 -> 0.91f
                            4 -> -0.91f
                            5 -> -0.87f
                            else -> -0.86f
                        }
                        AppButton(
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .aspectRatio(1f)
                                .align(
                                    BiasAlignment(
                                        horizontalBias = xBias,
                                        verticalBias = yBias
                                    )
                                ),
                            shape = CircleShape,
                            textStyle = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            onClick = { vm.onClick(i) },
                            text = vm.myStringNames[i-1],
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (vm.selectedButtonId != i) Color.Transparent else MaterialTheme.colorScheme.primary,
                                contentColor = if (vm.selectedButtonId != i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }

                AppModalBottomSheet(
                    showSheet = vm.showOptionsSheet,
                    onDismissRequest = { vm.onClickBottomSheet() },
                    title = "Select tuning Mode"
                ) {
                    for(i in 0..<tuningModesList.size) {
                        AppButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(vertical = 2.dp),
                            text = tuningModesList[i].name,
                            onClick = { vm.changeMode(i) },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface,
                                containerColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TurnerNeedle (
    currentHz: Float,
    targetHz: Float
) {
    val maxDeviation = 10f
    val difference = currentHz - targetHz

    val rawBias = (difference/ maxDeviation).coerceIn(-1f, 1f)

    val animatedBias by animateFloatAsState(
        targetValue = if (currentHz <= 0f || currentHz < targetHz-80 || currentHz > targetHz+80) 0f else rawBias,
        animationSpec = tween(durationMillis = 250),
        label = "Needle Animation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .background(MaterialTheme.colorScheme.onSurface, shape = RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .width(2.dp)
                .align(Alignment.Center)
                .background(MaterialTheme.colorScheme.surface)
        )

        AppText(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp),
            text = "Current: \n${currentHz.toInt()}Hz",
            color = if (currentHz.toInt() == targetHz.toInt()) Color(0xFF4CAF50) else MaterialTheme.colorScheme.background
        )

        AppText(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp),
            text = "Target: \n~${targetHz.toInt()}Hz",
            color = if (currentHz.toInt() == targetHz.toInt()) Color(0xFF4CAF50) else MaterialTheme.colorScheme.background
        )

        val text = if (rawBias > 0.1) {
            stringResource(R.string.sharp)
        } else if (rawBias < -0.1) {
            stringResource(R.string.flat)
        } else {
            stringResource(R.string.in_tune)
        }

        AppText(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            text = text,
            color = MaterialTheme.colorScheme.background
        )

        if (currentHz < targetHz-200 || currentHz > targetHz+200) {
            AppText(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                text = "Listening...",
                color = MaterialTheme.colorScheme.background
            )
        }

        androidx.compose.material3.Icon(
            painter = painterResource(R.drawable.arrow_up),
            contentDescription = "Needle",
            tint = if (text == stringResource(R.string.in_tune)) {
                Color(0xFF4CAF50)
            } else if (currentHz < targetHz-200 || currentHz > targetHz+200) {
                MaterialTheme.colorScheme.surface
            } else {
                Color(0xFFFF5722)
            },
            modifier = Modifier
                .size(48.dp)
                .align(
                    BiasAlignment(
                        horizontalBias = animatedBias,
                        verticalBias = 0f
                    )
                )
        )
    }
}