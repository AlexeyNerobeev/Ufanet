package com.example.ufanet.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R

@Preview
@Composable
fun PrevEmployeeBottomNav() {
    EmployeeBottomNavigation(rememberNavController(), 2)
}

@Composable
fun EmployeeBottomNavigation(navController: NavController, activityNumber: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                clip = false
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    if (activityNumber != 1) {
                        navController.navigate(NavRoutes.CommentsScreen.createRoute(0))
                    }
                },
                modifier = Modifier
                    .size(if (activityNumber == 1) 52.dp else 44.dp)
                    .background(
                        color = if (activityNumber == 1)
                            colorResource(R.color.Orange)
                        else
                            Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.application_icon),
                    contentDescription = "Заявки",
                    tint = if (activityNumber == 1) Color.White else Color(0xFFBDBDBD),
                    modifier = Modifier.size(if (activityNumber == 1) 24.dp else 20.dp)
                )
            }

            IconButton(
                onClick = {
                    if (activityNumber != 2) {
                        navController.navigate(NavRoutes.EmployeeHomeScreen.route)
                    }
                },
                modifier = Modifier
                    .size(if (activityNumber == 2) 52.dp else 44.dp)
                    .background(
                        color = if (activityNumber == 2)
                            colorResource(R.color.Orange)
                        else
                            Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.home_icon),
                    contentDescription = "Главная",
                    tint = if (activityNumber == 2) Color.White else Color(0xFFBDBDBD),
                    modifier = Modifier.size(if (activityNumber == 2) 24.dp else 20.dp)
                )
            }

            IconButton(
                onClick = {
                    if (activityNumber != 3) {
                        navController.navigate(NavRoutes.EmployeeSearchScreen.route)
                    }
                },
                modifier = Modifier
                    .size(if (activityNumber == 3) 52.dp else 44.dp)
                    .background(
                        color = if (activityNumber == 3)
                            colorResource(R.color.Orange)
                        else
                            Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = "Поиск",
                    tint = if (activityNumber == 3) Color.White else Color(0xFFBDBDBD),
                    modifier = Modifier.size(if (activityNumber == 3) 24.dp else 20.dp)
                )
            }

            IconButton(
                onClick = {
                    if (activityNumber != 4) {
                        navController.navigate(NavRoutes.EmployeeProfileScreen.route)
                    }
                },
                modifier = Modifier
                    .size(if (activityNumber == 4) 52.dp else 44.dp)
                    .background(
                        color = if (activityNumber == 4)
                            colorResource(R.color.Orange)
                        else
                            Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile_icon),
                    contentDescription = "Профиль",
                    tint = if (activityNumber == 4) Color.White else Color(0xFFBDBDBD),
                    modifier = Modifier.size(if (activityNumber == 4) 24.dp else 20.dp)
                )
            }
        }
    }
}