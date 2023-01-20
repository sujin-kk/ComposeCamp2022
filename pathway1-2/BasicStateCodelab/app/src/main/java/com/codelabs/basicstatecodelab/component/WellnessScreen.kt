package com.codelabs.basicstatecodelab.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codelabs.basicstatecodelab.WaterCount

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    WaterCount(modifier)
}