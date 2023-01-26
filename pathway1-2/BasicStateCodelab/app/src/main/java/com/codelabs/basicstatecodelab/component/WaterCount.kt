package com.codelabs.basicstatecodelab.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.component.WellnessTaskItem

/** 7 - remember **/
//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today?",
//                    onClose = { showTask = false }
//                )
//            }
//            Text("You've had $count glasses")
//        }
//
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(
//                onClick = { count++ },
//                enabled = count < 10,
//                modifier = Modifier.padding(end = 8.dp)
//            ) {
//                Text("Add One")
//            }
//            Button(
//                onClick = { count = 0 },
//            ) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

/** 8 - 상태 복원 **/
@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // Bundle에 저장할 수 있는 모든 값을 저장 가능 -> 화면 회전, 언어 변경, 테마 변경 등의 상황에도 값 유지
        // 프로세스가 다시 시작된 이후에도 UI 상태 복원
        var count by rememberSaveable { mutableStateOf(0) }

        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}