package com.example.guitartuner.model

data class TunerScreenState (
    val tuningModeName: String,
    val isAutoMode: Boolean,
    val stringNames: List<String>,
    val pitches: List<Float>,
    val targetPitch: Float,
    val selectedButtonId: Int,
    val showOptionsSheet: Boolean
)