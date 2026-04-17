package com.example.ufanet.feature_app.presentation.EmployeeHome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.EmployeeBottomNavigation
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import com.example.ufanet.feature_app.domain.models.Application

@Composable
fun EmployeeHomeScreen(navController: NavController, vm: EmployeeHomeVM = hiltViewModel()) {
    val state = vm.state.value
    vm.onEvent(EmployeeHomeEvent.GetAllApplications)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F7FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(
                        color = colorResource(R.color.Orange),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_icon),
                            contentDescription = "Назад",
                            tint = Color.Unspecified
                        )
                    }

                    Text(
                        text = "Все заявки",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontFamily = ptSansBold,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    IconButton(
                        onClick = {
                            navController.navigate(NavRoutes.EmployeeSearchScreen.route)
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = "Поиск заявок",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            if (state.application.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp)
                        .padding(top = 20.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Всего заявок: ${state.application.size}",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = interRegular
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (state.application.isEmpty()) {
                    item {
                        EmptyStateView()
                    }
                } else {
                    items(state.application) { item ->
                        EmployeeApplicationCard(
                            application = item,
                            onClick = {
                                navController.navigate(NavRoutes.CommentsScreen.createRoute(item.id))
                            },
                            showOnMapClick = {
                                navController.navigate(NavRoutes.MapScreen.createRoute(item.id))
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.size(80.dp))
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        EmployeeBottomNavigation(navController, 2)
    }
}

@Composable
fun EmployeeApplicationCard(
    application: Application,
    onClick: () -> Unit,
    showOnMapClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.company_icon),
                            contentDescription = null,
                            tint = colorResource(R.color.Orange),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = application.company_name,
                        color = Color.Black,
                        fontFamily = interBold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .background(
                            color = when (application.status.lowercase()) {
                                "принята" -> Color(0xFFFFA726)
                                "выполнена" -> Color(0xFF66BB6A)
                                "не принята" -> Color(0xFFEF5350)
                                else -> colorResource(R.color.Orange)
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = application.status,
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 12.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(vertical = 12.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f))
            )

            InfoRow(
                label = "Адрес",
                value = application.address,
                onClick = {
                    showOnMapClick()
                },
                icon = R.drawable.location_pin_icon
            )

            InfoRow(
                label = "Телефон",
                value = application.phone,
                onClick = {},
                icon = R.drawable.phone_icon
            )

            InfoRow(
                label = "Описание",
                value = application.description,
                maxLines = 2,
                onClick = {},
                icon = R.drawable.description_icon
            )

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.Orange)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.application_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Просмотреть заявку",
                    color = Color.White,
                    fontFamily = interBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    maxLines: Int = 1,
    onClick: () -> Unit,
    icon: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable{
                onClick()
            },
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = colorResource(R.color.Orange),
            modifier = Modifier
                .padding(top = 2.dp)
                .size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                color = Color.Gray,
                fontFamily = interRegular,
                fontSize = 12.sp
            )
            Text(
                text = value,
                color = if(label == "Адрес"){
                    Color.Blue
                } else{
                    Color.Black
                },
                fontFamily = interRegular,
                fontSize = 15.sp,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                textDecoration = if(label == "Адрес"){
                    TextDecoration.Underline
                } else{
                    TextDecoration.None
                }
            )
        }
    }
}

@Composable
fun EmptyStateView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(60.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = null,
                tint = colorResource(R.color.Orange).copy(alpha = 0.5f),
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Нет доступных заявок",
            color = Color.Black,
            fontFamily = interBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Новые заявки появятся здесь",
            color = Color.Gray,
            fontFamily = interRegular,
            fontSize = 14.sp
        )
    }
}