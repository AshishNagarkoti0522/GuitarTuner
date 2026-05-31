package com.example.guitartuner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.guitartuner.helper.Tuner
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.guitartuner.model.TunerScreenEvent
import com.example.guitartuner.model.TunerScreenState
import com.example.guitartuner.model.tuningModesList
import kotlinx.coroutines.launch

class TunerViewModel : ViewModel() {

    private val tuner = Tuner()
    val frequency: StateFlow<Float> = tuner.currentPitch

    var screenState by mutableStateOf(TunerScreenState(
        tuningModeName = tuningModesList[0].name,
        isAutoMode = false,
        stringNames = tuningModesList[0].stringNames,
        pitches = tuningModesList[0].pitches,
        targetPitch = tuningModesList[0].pitches[5],
        selectedButtonId = 6,
        showOptionsSheet = false
    ))
        private set

    init {
        viewModelScope.launch {
            frequency.collect{ currentHz ->
                if(screenState.isAutoMode) {
                    val detection = autoDetection(currentHz, screenState.pitches)

                    if (detection != null) {
                        screenState = screenState.copy(
                            selectedButtonId = detection.first,
                            targetPitch = detection.second
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: TunerScreenEvent) {
        when (event) {
            is TunerScreenEvent.ToggleAutoModeClicked -> {
                val newMode = !screenState.isAutoMode
                screenState = screenState.copy(
                    isAutoMode = newMode,
                    selectedButtonId = if (newMode) 0 else 6,
                    targetPitch = if (newMode) 0f else screenState.pitches[5]
                )
                Log.d("Tuner", "Auto Mode is now: $newMode")
            }
            is TunerScreenEvent.TuningOptionsToggled -> { screenState = screenState.copy(showOptionsSheet = !screenState.showOptionsSheet) }
            is TunerScreenEvent.TuningModeSelected -> {
                val newModeData = tuningModesList[event.modeIndex]

                screenState = screenState.copy(
                    tuningModeName = newModeData.name,
                    stringNames = newModeData.stringNames,
                    pitches = newModeData.pitches,
                    targetPitch = newModeData.pitches[5],
                    selectedButtonId = 6,
                    showOptionsSheet = false
                )
            }
            is TunerScreenEvent.TargetStringSelected -> {
                screenState = screenState.copy(
                    targetPitch = screenState.pitches[event.stringNumber-1],
                    selectedButtonId = event.stringNumber
                )
            }
        }
    }

    fun startTuner() { tuner.startListening() }

    fun stopTuner() { tuner.stopListening() }

    override fun onCleared() {
        super.onCleared()
        tuner.stopListening()
    }

    private fun autoDetection(currentHz: Float, pitch: List<Float>): Pair<Int, Float>? {
        if (currentHz !in 60f..500f) return null

        for (i in pitch.indices) {
            val target = pitch[i]
            val tolerance = target * 0.15f

            if (currentHz in (target - tolerance)..(target + tolerance)) {
                return Pair(i + 1, target)
            }
        }
        return null
    }
}