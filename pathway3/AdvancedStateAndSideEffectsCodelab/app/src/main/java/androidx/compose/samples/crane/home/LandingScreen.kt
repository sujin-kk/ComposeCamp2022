/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.samples.crane.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@Composable
fun LandingScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        // key1 key2 ... -> key중 하나가 변경될때마다 효과 다시 시작
        // MainScreen -> onTimeout은 showLandingScreen state 변수를 조작 -> 첫 접속 후에는 false로 바꿀텐데,,
        // 앱 나갔다 들어오면 다시 true로 변경 -> 스플래시 다시 시작??
        // onTimeout이 변경되더라도 LandingScreen이 나타나서는 안된다!

        // LandingScreen(onTimeout = { showLandingScreen = false })
        // onTimeout이 변경될 때 값을 업데이트 -> 가장 마지막 값만 변경할 수 있는 rememberUpdatedState
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        // 외부에서 rememberCoroutineScope를 만들고 scope.launch로 처리할 수 있지 않을까?
        // MainScreen -> Landing Screen을 호출할 때마다 코루틴 실행 -> 리소스 낭비
        LaunchedEffect(onTimeout) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }
        Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)
    }
}
