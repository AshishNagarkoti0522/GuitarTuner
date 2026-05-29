package com.example.guitartuner.viewmodel

import androidx.lifecycle.ViewModel
import com.example.guitartuner.helper.Tuner
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.guitartuner.model.tuningModesList

class TunerViewModel : ViewModel() {

//    private val _uiState = MutableStateFlow<UiState>(UiState.Listen(43.76))
//    val uiState = _uiState.asStateFlow()
    private val tuner = Tuner()

    var tuningModeName by mutableStateOf(tuningModesList[0].name)
        private set

    var myStringNames by mutableStateOf(tuningModesList[0].stringNames)
        private set

    var myPitches by mutableStateOf(tuningModesList[0].pitches)
        private set

    fun changeMode(id: Int) {
        tuningModeName = tuningModesList[id].name
        myStringNames = tuningModesList[id].stringNames
        myPitches = tuningModesList[id].pitches

        targetPitch = myPitches[selectedButtonId - 1]
        onClickBottomSheet()
    }

    val frequency: StateFlow<Float> = tuner.currentPitch

    var targetPitch by mutableFloatStateOf(tuningModesList[0].pitches[5])
        private set

    var selectedButtonId by mutableIntStateOf(6)
        private set

    var showOptionsSheet by mutableStateOf(false)
        private set

    fun onClickBottomSheet() {
        showOptionsSheet = !showOptionsSheet
    }

    fun onClick(id : Int) {
        targetPitch = myPitches[id-1]
        selectedButtonId = id
    }

    fun startTuner() {
        tuner.startListening()
    }

    fun stopTuner() {
        tuner.stopListening()
    }

    override fun onCleared() {
        super.onCleared()
        tuner.stopListening()
    }
}