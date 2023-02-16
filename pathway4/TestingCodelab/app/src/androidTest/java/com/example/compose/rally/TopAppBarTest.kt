package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var allScreens: List<RallyScreen>

    @Before
    fun setUp() {
        allScreens = RallyScreen.values().toList()
    }

    @Test
    fun rallyTopAppBarTest() {
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        // 현재 Accounts 화면이 select 됐는지
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    // Semantics Tree -> 화면에서 요소를 찾고 해당 속성을 읽는 구조
    // 탭 선택했을 때 text가 잘 노출되는지
    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        // UnMergedTree ??
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        // text -> ACCOUNT 인 노드
        // ContentDescription -> Accounts 인 노드를 부모로 가지는 노드
        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                hasParent(
                    hasContentDescription(RallyScreen.Accounts.name)
                ),
                useUnmergedTree = true
            )

//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertExists()
    }
}