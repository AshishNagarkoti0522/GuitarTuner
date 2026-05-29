package com.example.guitartuner.model

data class TuningMode(
    val name: String,             // Jaise: "Standard", "Drop D"
    val pitches: List<Float>,     // Frequencies (e, B, G, D, A, E)
    val stringNames: List<String> // Buttons ke letters ("e", "B", "G", "D", "A", "E")
)

val tuningModesList = listOf(
    // 1. STANDARD TUNING
    TuningMode(
        name = "Standard",
        pitches = listOf(329.63f, 246.94f, 196.00f, 146.83f, 110.00f, 82.41f),
        stringNames = listOf("e", "B", "G", "D", "A", "E")
    ),

    // 2. DROP TUNING (Heavy Metal)
    TuningMode(
        name = "Drop D",
        pitches = listOf(329.63f, 246.94f, 196.00f, 146.83f, 110.00f, 73.42f),
        stringNames = listOf("e", "B", "G", "D", "A", "D") // 6th string drops from E to D
    ),

    // 3. STEP-DOWN TUNING (Vocal Match / Easy Bending)
    TuningMode(
        name = "Half-Step Down",
        pitches = listOf(311.13f, 233.08f, 185.00f, 138.59f, 103.83f, 77.78f),
        stringNames = listOf("eb", "Bb", "Gb", "Db", "Ab", "Eb") // All strings 1 semitone down
    ),

    // 4. ALTERNATE / FINGERSTYLE TUNING
    TuningMode(
        name = "DADGAD",
        pitches = listOf(293.66f, 220.00f, 196.00f, 146.83f, 110.00f, 73.42f),
        stringNames = listOf("d", "a", "G", "D", "A", "D") // 1st, 2nd, and 6th string dropped
    ),

    // 5. OPEN TUNING (Blues / Slide)
    TuningMode(
        name = "Open G",
        pitches = listOf(293.66f, 246.94f, 196.00f, 146.83f, 98.00f, 73.42f),
        stringNames = listOf("d", "B", "G", "D", "G", "D") // Open major G chord
    ),

    // 6. OPEN D TUNING
    TuningMode(
        name = "Open D",
        pitches = listOf(293.66f, 220.00f, 185.00f, 146.83f, 110.00f, 73.42f),
        stringNames = listOf("d", "a", "F#", "D", "A", "D") // Open major D chord
    )
)