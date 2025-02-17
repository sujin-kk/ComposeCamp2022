/*
 * Copyright 2022 The Android Open Source Project
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

package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.overview.OverviewScreen
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        // navController는 컴포저블 계층 구조의 최상위 수준 (일반적으로 App Composable 내)에서 만들어야 함
        val navController = rememberNavController()

        // 반환된 destination을 우리 composable로 매칭할 방법을 찾아야 함
        // 화면에 어느 composable이 표시되어 있는지 확인하여 정보를 Tab에 전달한다.
        // 문자열 경로를 id로 사용하여 비교
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview

        // 탭을 탭하면 특정 대상으로 이동 -> 올바른 대상으로 이동하도록 navGraph에 연결
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    onTabSelected = { screen ->
                        navController.navigateSingleTopTo(screen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            RallyNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ) {
        // builder -> navGraph 정의 및 빌드
        composable(route = Overview.route) {
            // 이동하면 표시 할 UI
            // 화면의 클릭 이벤트를 콜러에서 전달해주는게 맞나? 화면 컴포저블 내부적으로 처리하는게 더 낫지않나,,
            // navController를 참조하기 위함인것같다?!
            // navController를 넘기는게 나을지 고민해봐야함
            OverviewScreen(
                onClickSeeAllAccounts = {
                    navController.navigateSingleTopTo(Accounts.route)
//                    navController.navigate(Accounts.route) {
//                        popUpTo(Bills.route)
//                    }
                },
                onClickSeeAllBills = {
                    navController.navigateSingleTopTo(Bills.route)
                },
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)

                }
            )
        }

        composable(route = Accounts.route) {
            AccountsScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }

        composable(route = Bills.route) {
            BillsScreen()
        }

        // argument와 함께 라우팅
        // "route/{argument}" 패턴
        composable(
            route = SingleAccount.routeWithArgs,
            arguments = SingleAccount.arguments,
            deepLinks = SingleAccount.deepLinks
        ) { navBackStackEntry ->
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)

            SingleAccountScreen(accountType)
        }
    }
}

/**
 * 백스택에 화면 인스턴스가 최대 1개만 있도록 -> 계속 인스턴스 생성 방지
 * launchSingleTop 플래그 준다.
 * popUpTo(startDestination) { saveState = true } -> 탭을 선택했을 때 백스택에 대규모 대상 스택이 빌드되지 않는다.
 * restoreState = true -> 이 탐색 동작이 이전에 popUpToSaveState에 의해 저장된 상태를 복원하는지 여부 정한다.
 * **/
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToSingleAccount(accountType: String) =
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")