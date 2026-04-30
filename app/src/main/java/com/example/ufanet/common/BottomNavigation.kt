package com.example.ufanet.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun PrevBottomNav() {
    BottomNavigation(rememberNavController(), 2)
}

@Composable
fun BottomNavigation(navController: NavController, activityNumber: Int) {
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
            .padding(horizontal = 32.dp, vertical = 12.dp)
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
                        navController.navigate(NavRoutes.ApplicationsScreen.createRoute(0))
                    }
                },
                modifier = Modifier
                    .size(if (activityNumber == 1) 56.dp else 48.dp)
                    .background(
                        color = if (activityNumber == 1)
                            colorResource(R.color.Orange)
                        else
                            Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.application_icon),
                    contentDescription = "Заявки",
                    tint = if (activityNumber == 1) Color.White else Color(0xFFBDBDBD),
                    modifier = Modifier.size(if (activityNumber == 1) 26.dp else 22.dp)
                )
            }

            IconButton(
                onClick = {
                    if (activityNumber != 2) {
                        navController.navigate(NavRoutes.HomeScreen.route)
                    }
                },
                modifier = Modifier
                    .size(if (activityNumber == 2) 56.dp else 48.dp)
                    .background(
                        color = if (activityNumber == 2)
                            colorResource(R.color.Orange)
                        else
                            Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.home_icon),
                    contentDescription = "Главная",
                    tint = if (activityNumber == 2) Color.White else Color(0xFFBDBDBD),
                    modifier = Modifier.size(if (activityNumber == 2) 26.dp else 22.dp)
                )
            }

            IconButton(
                onClick = {
                    if (activityNumber != 3) {
                        navController.navigate(NavRoutes.ProfileScreen.route)
                    }
                },
                modifier = Modifier
                    .size(if (activityNumber == 3) 56.dp else 48.dp)
                    .background(
                        color = if (activityNumber == 3)
                            colorResource(R.color.Orange)
                        else
                            Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile_icon),
                    contentDescription = "Профиль",
                    tint = if (activityNumber == 3) Color.White else Color(0xFFBDBDBD),
                    modifier = Modifier.size(if (activityNumber == 3) 26.dp else 22.dp)
                )
            }
        }
    }
}