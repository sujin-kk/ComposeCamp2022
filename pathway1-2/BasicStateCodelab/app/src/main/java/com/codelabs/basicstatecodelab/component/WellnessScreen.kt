package com.codelabs.basicstatecodelab.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.codelabs.basicstatecodelab.model.WellnessTask

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    // WaterCounter(modifier)
    Column(modifier = modifier) {
        StatefulCounter()

        val list = remember { WellnessTask.getWellnessTasks().toMutableStateList() }
        WellnessTasksList(list = list, onCloseTask = { task -> list.remove(task) })
    }
}