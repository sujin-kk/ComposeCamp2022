package com.codelabs.basicstatecodelab.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    // WaterCounter(modifier)
    StatefulCounter()
}