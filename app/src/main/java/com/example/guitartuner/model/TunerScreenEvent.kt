package com.example.guitartuner.model

sealed interface TunerScreenEvent {
    // Auto/Manual switch
    object ToggleAutoModeClicked : TunerScreenEvent

    // 3-dots wale menu click to open bottom sheet
    object TuningOptionsToggled : TunerScreenEvent

    // to select tunning mode in bottom sheet (e.g. Drop D)
    data class TuningModeSelected(val modeIndex: Int) : TunerScreenEvent

    // when user select specific string (e.g. 6th string E) in manual mode
    data class TargetStringSelected(val stringNumber: Int) : TunerScreenEvent
}