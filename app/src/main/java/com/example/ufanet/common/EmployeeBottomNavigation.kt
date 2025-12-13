package com.example.ufanet.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.example.ufanet.NavRoutes
import com.example.ufanet.R

@Composable
fun EmployeeBottomNavigation(navController: NavController, activityNumber: Int) {
    Box(
    modifier = Modifier
    .height(70.dp)
    .fillMaxWidth()
    .background(colorResource(R.color.Orange))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        if(activityNumber != 1){
                            navController.navigate(NavRoutes.CommentsScreen.createRoute(0))
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.application_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                IconButton(onClick = {
                        if(activityNumber != 2){
                            navController.navigate(NavRoutes.EmployeeHomeScreen.route)
                        }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.home_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                IconButton(onClick = {
                    if(activityNumber != 3){
                        navController.navigate(NavRoutes.EmployeeSearchScreen.route)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.search_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                IconButton(onClick = {
                    if(activityNumber != 4) {
                        navController.navigate (NavRoutes.EmployeeProfileScreen.route)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.profile_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}