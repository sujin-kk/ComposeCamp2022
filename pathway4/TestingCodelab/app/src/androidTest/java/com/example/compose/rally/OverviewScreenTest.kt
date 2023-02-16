package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // OverviewBody에 Alerts Text가 포함되어있는지 검사
    // Alerts가 들어있는 Composable이 영구적으로 애니메이션 실행 중
    // 앱이 유휴상태가 아니여서 테스트를 할 수 없음 -> 동기화 할 방법? 영구 애니메이션 지워!
    @Test
    fun overviewScreen_alertsDisplayed() {
        composeTestRule.setContent {
            OverviewBody()
        }

        composeTestRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed()
    }
}