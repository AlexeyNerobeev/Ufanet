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
import com.example.ufanet.NavRoutes
import com.example.ufanet.R

@Composable
fun EmployeeBottomNavigation() {
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

                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.application_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(R.drawable.home_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                IconButton(onClick = {

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