package com.example.ufanet.feature_app.presentation.EmployeeSearch

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ufanet.R
import com.example.ufanet.common.EmployeeBottomNavigation
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import com.example.ufanet.feature_app.presentation.Comments.CommentsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmployeeSearchScreen(navController: NavController, vm: EmployeeSearchVM = koinViewModel()) {
    val state = vm.state.value
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .alpha(if(state.showFilter) 0.3f else 1f)) { innerPadding ->
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
                    .background(colorResource(R.color.Orange)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .background(Color.Transparent)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    Icon(
                        painterResource(R.drawable.search_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Text(
                    text = "Поиск",
                    fontFamily = ptSansBold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            OutlinedTextField(
                value = state.searchText,
                onValueChange = {
                    vm.onEvent(EmployeeSearchEvent.EnteredSearchText(it))
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(top = 15.dp)
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .border(
                        shape = RoundedCornerShape(30.dp),
                        width = 1.dp,
                        color = colorResource(R.color.StrokeTextField)
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = colorResource(R.color.StrokeTextField),
                    unfocusedBorderColor = colorResource(R.color.StrokeTextField),
                    focusedContainerColor = colorResource(R.color.FillTextField),
                    unfocusedContainerColor = colorResource(R.color.FillTextField)
                ),
                textStyle = TextStyle(
                    fontFamily = interRegular,
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        vm.onEvent(EmployeeSearchEvent.ShowFilter)
                    }) {
                        Icon(
                            painterResource(R.drawable.filter_icon),
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = "Поиск",
                        color = Color.Gray,
                        fontFamily = interBold,
                        fontSize = 14.sp
                    )
                }
            )
        }
    }
    if (!state.showFilter) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            EmployeeBottomNavigation(navController, 3)
        }
    } else {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 41.dp)
                        .padding(horizontal = 25.dp)
                        .padding(bottom = 50.dp)
                ) {
                    Text(
                        text = "Фильтр",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontFamily = ptSansBold,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Статус заявки",
                        fontFamily = interRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 18.dp)
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        item {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .background(
                                        color = colorResource(R.color.FillTextField),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Не принята",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = colorResource(R.color.FillTextField),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Принята",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = colorResource(R.color.FillTextField),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Выполнена",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Text(
                        text = "Комментарии",
                        fontFamily = interRegular,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 23.dp)
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        item {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .background(
                                        color = colorResource(R.color.FillTextField),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Нет",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = colorResource(R.color.FillTextField),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Один",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = colorResource(R.color.FillTextField),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Больше одного",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}