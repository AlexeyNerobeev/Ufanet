package com.example.ufanet.feature_app.presentation.EmployeeHome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.EmployeeBottomNavigation
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmployeeHomeScreen(navController: NavController, vm: EmployeeHomeVM = koinViewModel()) {
    val state = vm.state.value
    vm.onEvent(EmployeeHomeEvent.GetAllApplications)
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(colorResource(R.color.Orange))
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 5.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.back_icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        text = "Заявки",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = ptSansBold
                    )
                    IconButton(onClick = {
                        navController.navigate(NavRoutes.EmployeeSearchScreen.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 36.dp)
                    .fillMaxWidth()
            ) {
                items(state.application) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = colorResource(R.color.FillTextField),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(shape = RoundedCornerShape(10.dp))
                            .border(width = 2.dp, Color.LightGray, RoundedCornerShape(10.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .padding(vertical = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Название\nорганизации:",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 18.sp)
                                Text(
                                    text = item.status,
                                    color = Color.White,
                                    fontFamily = ptSansBold,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .background(colorResource(R.color.Orange),
                                            shape = RoundedCornerShape(10.dp))
                                        .padding(7.dp)
                                )
                            }
                            Text(
                                text = item.company_name,
                                color = Color.Black,
                                fontFamily = interRegular,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                            Text(
                                text = "Адрес:",
                                color = Color.Black,
                                fontFamily = interBold,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                            Text(
                                text = item.address,
                                color = Color.Black,
                                fontFamily = interRegular,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                            Text(
                                text = "Телефон:",
                                color = Color.Black,
                                fontFamily = interBold,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                            Text(
                                text = item.phone,
                                color = Color.Black,
                                fontFamily = interRegular,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                            Text(
                                text = "Описание проблемы:",
                                color = Color.Black,
                                fontFamily = interBold,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                            Text(
                                text = item.description,
                                color = Color.Black,
                                fontFamily = interRegular,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier
                        .size(20.dp))
                }
                item{
                    Spacer(modifier = Modifier
                        .height(100.dp))
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        EmployeeBottomNavigation(navController)
    }
}