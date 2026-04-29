package com.example.ufanet.feature_app.presentation.EmployeeStats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ufanet.R
import com.example.ufanet.common.interBold
import com.example.ufanet.common.ptSansBold

@Composable
fun EmployeeStatsScreen(
    navController: NavController,
    vm: EmployeeStatsVM = hiltViewModel()
) {

    val state = vm.state.value

    LaunchedEffect(Unit) {
        vm.onEvent(EmployeeStatsEvent.LoadStats)
    }

    Scaffold(
        containerColor = Color(0xFFF5F7FA)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
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
                Text(
                    text = "Моя статистика",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = ptSansBold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    StatCard("Всего заявок", state.stats.total, Color.Gray)
                }

                item {
                    StatCard("Новые", state.stats.new, Color(0xFFEF5350))
                }

                item {
                    StatCard("В работе", state.stats.inProgress, Color(0xFFFFA726))
                }

                item {
                    StatCard("Выполнено", state.stats.done, Color(0xFF66BB6A))
                }

                item {
                    StatCard("Просрочено", state.stats.overdue, Color(0xFFB71C1C))
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: Int, color: Color) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = interBold,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .background(color, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = value.toString(),
                    color = Color.White,
                    fontFamily = interBold
                )
            }
        }
    }
}