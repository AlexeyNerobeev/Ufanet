package com.example.ufanet.feature_app.presentation.EmployeeStats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ufanet.R
import com.example.ufanet.common.interBold
import com.example.ufanet.common.ptSansBold
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EmployeeStatsScreen(
    navController: NavController,
    vm: EmployeeStatsVM = hiltViewModel()
) {
    val state = vm.state.value
    var showPicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.onEvent(EmployeeStatsEvent.LoadStats)
    }

    if (showPicker) {
        DateRangePickerDialog(
            onDismiss = { showPicker = false },
            onConfirm = { from, to ->
                showPicker = false
                vm.loadStatsByDate(from, to)
            }
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colorResource(R.color.Orange),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(70.dp)
                        .padding(horizontal = 16.dp),
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
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }

                    Text(
                        text = "Статистика",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontFamily = ptSansBold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(R.color.Orange),
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally),
                    strokeWidth = 7.dp
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        EmployeeDonutChart(
                            new = state.stats.new,
                            inProgress = state.stats.inProgress,
                            done = state.stats.done
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    item {
                        StatCard("Всего заявок", state.stats.total, Color.Gray)
                    }

                    item {
                        StatCard("Не приняты", state.stats.new, Color(0xFFEF5350))
                    }

                    item {
                        StatCard("В работе", state.stats.inProgress, Color(0xFFFFA726))
                    }

                    item {
                        StatCard("Выполнены", state.stats.done, Color(0xFF66BB6A))
                    }

                    item {
                        DatePickerButton(
                            from = state.fromDate,
                            to = state.toDate,
                            onClick = { showPicker = true }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
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

@Composable
fun EmployeeDonutChart(
    new: Int,
    inProgress: Int,
    done: Int
) {

    val total = (new + inProgress + done).takeIf { it > 0 } ?: 0

    val newPercent = new.toFloat() / total
    val progressPercent = inProgress.toFloat() / total
    val donePercent = done.toFloat() / total

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .size(220.dp)
        ) {

            val stroke = 40.dp.toPx()

            var startAngle = -90f

            drawArc(
                color = Color(0xFFEF5350),
                startAngle = startAngle,
                sweepAngle = 360f * newPercent,
                useCenter = false,
                style = Stroke(width = stroke)
            )

            startAngle += 360f * newPercent

            drawArc(
                color = Color(0xFFFD7633),
                startAngle = startAngle,
                sweepAngle = 360f * progressPercent,
                useCenter = false,
                style = Stroke(width = stroke)
            )

            startAngle += 360f * progressPercent

            drawArc(
                color = Color(0xFF66BB6A),
                startAngle = startAngle,
                sweepAngle = 360f * donePercent,
                useCenter = false,
                style = Stroke(width = stroke)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$total",
                fontSize = 28.sp,
                fontFamily = interBold
            )
            Text(
                text = "заявок",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long, Long) -> Unit
) {

    val state = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = "Выбрать",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        val start = state.selectedStartDateMillis
                        val end = state.selectedEndDateMillis

                        if (start != null && end != null) {
                            onConfirm(start, end)
                        }
                    }
            )
        },
        dismissButton = {
            Text(
                text = "Отмена",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onDismiss() }
            )
        }
    ) {
        Column {
            Text(
                text = "Выберите период",
                fontFamily = interBold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, bottom = 8.dp)
            )

            DateRangePicker(
                state = state,
                title = null,
                headline = null
            )
        }
    }
}

@Composable
fun DatePickerButton(
    from: Long?,
    to: Long?,
    onClick: () -> Unit
) {

    val text = if (from != null && to != null) {
        "${formatDate(from)} - ${formatDate(to)}"
    } else {
        "Выбрать период"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorResource(R.color.Orange).copy(alpha = 0.1f),
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorResource(R.color.Orange),
            fontFamily = interBold
        )
    }
}

fun formatDate(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM")
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}