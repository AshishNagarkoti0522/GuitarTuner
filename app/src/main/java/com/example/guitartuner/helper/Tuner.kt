package com.example.guitartuner.helper

import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Tuner {

    // 1. STATE: Yahan hum current frequency (Hz) save karenge
    // ViewModel isko collect karega aur UI ko dega
    private val _currentPitch = MutableStateFlow(0f)
    val currentPitch: StateFlow<Float> = _currentPitch.asStateFlow()

    private var dispatcher: AudioDispatcher? = null

    // 2. START LISTENING: Mic se raw audio lekar frequency nikalna
    fun startListening() {
        // Standard sample rate and buffer size
        val sampleRate = 44100
        val bufferSize = 2048
        val overlap = 0

        // TarsosDSP ka factory method jo automatically mic connect kar leta hai
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, bufferSize, overlap)

        // Yeh handler har fraction of second me nayi frequency calculate karke dega
        val pitchDetectionHandler = PitchDetectionHandler { pitchDetectionResult, _ ->
            val pitchInHz = pitchDetectionResult.pitch

            // Agar pitch -1.0 aati hai, matlab shanti hai (koi sound nahi)
            if (pitchInHz > 0) {
                // UI update karne ke liye state me daal do
                _currentPitch.value = pitchInHz
            }
        }

        // YIN Algorithm: Guitar aur music instruments ke liye best algorithm hota hai
        val pitchProcessor = PitchProcessor(
            PitchEstimationAlgorithm.YIN,
            sampleRate.toFloat(),
            bufferSize,
            pitchDetectionHandler
        )

        // Dispatcher ko bolo ki sound aaye toh PitchProcessor ko bhej de
        dispatcher?.addAudioProcessor(pitchProcessor)

        // TarsosDSP ko background thread me chalana padta hai taaki UI freeze na ho
        Thread(dispatcher, "Audio Dispatcher").start()
    }

    // 3. STOP LISTENING: App band ho toh mic band karna zaroori hai
    fun stopListening() {
        dispatcher?.stop()
        dispatcher = null
    }
}