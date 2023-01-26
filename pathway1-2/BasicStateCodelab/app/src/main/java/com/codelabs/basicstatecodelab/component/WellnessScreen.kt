package com.codelabs.basicstatecodelab.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    // WaterCounter(modifier)
    Column(modifier) {
        StatefulCounter()
        WellnessTasksList()
    }
}