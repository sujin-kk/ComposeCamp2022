package com.example.compose.rally

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get: Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            RallyNavHost(navController = navController)
        }
    }

    // StartDestination 테스트
    @Test
    fun rallyNavHost_verifyOverviewStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }

    // UI 클릭 및 화면 contentDescription을 통해 테스트
    @Test
    fun rallyNavHost_clickAllAccount_navigatesToAccounts() {
        composeTestRule
            .onNodeWithContentDescription("All Accounts")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    // UI 클릭 및 경로 비교 테스트
    @Test
    fun rallyNavHost_clickAllBills_navigateToBills() {
        composeTestRule.onNodeWithContentDescription("All Bills")
            .performScrollTo() // 하위 섹션으로 스크롤 -> All Bills인 노드를 찾아야 하기 때문
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "bills")
    }
}