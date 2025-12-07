package com.example.ufanet.feature_app.presentation.EmployeeSearch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.EmployeeBottomNavigation
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import com.example.ufanet.feature_app.presentation.Comments.CommentsEvent
import com.example.ufanet.feature_app.presentation.Home.HomeEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmployeeSearchScreen(navController: NavController, vm: EmployeeSearchVM = koinViewModel()) {
    val state = vm.state.value
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (state.showFilter) Color.Black else Color.Unspecified)
            .alpha(if (state.showFilter) 0.5f else 1f)
    ) { innerPadding ->
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
                        enabled = !state.showFilter,
                        modifier = Modifier
                            .background(Color.Transparent)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    IconButton(
                        onClick = {
                            vm.onEvent(EmployeeSearchEvent.ShowFilter)
                        },
                        enabled = !state.showFilter,
                        modifier = Modifier
                            .background(Color.Transparent)
                    ) {
                        Icon(
                            painterResource(R.drawable.filter_icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
                Text(
                    text = "Поиск",
                    fontFamily = ptSansBold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                userScrollEnabled = !state.showFilter
            ) {
                item {
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
                            IconButton(
                                onClick = {
                                    vm.onEvent(EmployeeSearchEvent.Search)
                                },
                                enabled = !state.showFilter
                            ) {
                                Icon(
                                    painterResource(R.drawable.search_icon),
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                text = state.placeholder,
                                color = Color.Gray,
                                fontFamily = interBold,
                                fontSize = 14.sp
                            )
                        },
                        enabled = !state.showFilter
                    )
                }
                items(state.applicationsList) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                            .padding(top = 15.dp)
                            .background(
                                color = colorResource(R.color.FillTextField),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(shape = RoundedCornerShape(10.dp))
                            .border(width = 2.dp, Color.LightGray, RoundedCornerShape(10.dp))
                            .clickable(enabled = !state.showFilter) {
                                navController.navigate(NavRoutes.CommentsScreen.createRoute(item.id))
                            }
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
                                Text(
                                    text = "Название\nорганизации:",
                                    color = Color.Black,
                                    fontFamily = interBold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = item.status,
                                    color = Color.White,
                                    fontFamily = ptSansBold,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .background(
                                            colorResource(R.color.Orange),
                                            shape = RoundedCornerShape(10.dp)
                                        )
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
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .height(100.dp)
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        EmployeeBottomNavigation(navController, 3)
    }
    AnimatedVisibility(
        visible = state.showFilter,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 500),
            initialOffsetY = { fullHeight -> fullHeight }
        ) + fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 500),
            targetOffsetY = { fullHeight -> fullHeight }
        ) + fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
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
                        text = "Поиск по",
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
                                    vm.onEvent(EmployeeSearchEvent.SelectedSearch(1))
                                },
                                modifier = Modifier
                                    .background(
                                        color = if (state.selectSearch == 1) colorResource(R.color.Orange) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectSearch == 1) Color.White else Color.Black,
                                    containerColor = if (state.selectSearch == 1) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Названию организации",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    vm.onEvent(EmployeeSearchEvent.SelectedSearch(2))
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectSearch == 2) colorResource(R.color.Orange) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectSearch == 2) Color.White else Color.Black,
                                    containerColor = if (state.selectSearch == 2) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Адресу",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    vm.onEvent(EmployeeSearchEvent.SelectedSearch(3))
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectSearch == 3) colorResource(R.color.Orange) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectSearch == 3) Color.White else Color.Black,
                                    containerColor = if (state.selectSearch == 3) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Телефону",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    vm.onEvent(EmployeeSearchEvent.SelectedSearch(4))
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectSearch == 4) colorResource(R.color.Orange) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectSearch == 4) Color.White else Color.Black,
                                    containerColor = if (state.selectSearch == 4) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Описанию проблемы",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
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
                                    if(state.selectStatus != 1){
                                        vm.onEvent(EmployeeSearchEvent.SelectedStatus(1))
                                    } else{
                                        vm.onEvent(EmployeeSearchEvent.SelectedStatus(0))
                                    }
                                },
                                modifier = Modifier
                                    .background(
                                        color = if (state.selectStatus == 1) colorResource(R.color.Orange) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectStatus == 1) Color.White else Color.Black,
                                    containerColor = if (state.selectStatus == 1) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Не принята",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    if(state.selectStatus != 2){
                                        vm.onEvent(EmployeeSearchEvent.SelectedStatus(2))
                                    } else{
                                        vm.onEvent(EmployeeSearchEvent.SelectedStatus(0))
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectStatus == 2) colorResource(R.color.Orange) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectStatus == 2) Color.White else Color.Black,
                                    containerColor = if (state.selectStatus == 2) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Принята",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    if(state.selectStatus != 3){
                                        vm.onEvent(EmployeeSearchEvent.SelectedStatus(3))
                                    } else{
                                        vm.onEvent(EmployeeSearchEvent.SelectedStatus(0))
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectStatus == 3) colorResource(R.color.Orange) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectStatus == 3) Color.White else Color.Black,
                                    containerColor = if (state.selectStatus == 3) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Выполнена",
                                    color = Color.Unspecified,
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
                                    if(state.selectComments != 1){
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(1))
                                    } else{
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(0))
                                    }
                                },
                                modifier = Modifier
                                    .background(
                                        color = if (state.selectComments == 1) colorResource(
                                            R.color.Orange
                                        ) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectComments == 1) Color.White else Color.Black,
                                    containerColor = if (state.selectComments == 1) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Нет",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    if(state.selectComments != 2){
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(2))
                                    } else{
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(0))
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectComments == 2) colorResource(
                                            R.color.Orange
                                        ) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectComments == 2) Color.White else Color.Black,
                                    containerColor = if (state.selectComments == 2) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Один",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    if(state.selectComments != 3){
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(3))
                                    } else{
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(0))
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectComments == 3) colorResource(
                                            R.color.Orange
                                        ) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectComments == 3) Color.White else Color.Black,
                                    containerColor = if (state.selectComments == 3) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Два",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    if(state.selectComments != 4){
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(4))
                                    } else{
                                        vm.onEvent(EmployeeSearchEvent.SelectedComments(0))
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .background(
                                        color = if (state.selectComments == 4) colorResource(
                                            R.color.Orange
                                        ) else colorResource(
                                            R.color.FillTextField
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 2.dp, shape = RoundedCornerShape(15.dp),
                                        color = colorResource(R.color.Orange)
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = if (state.selectComments == 4) Color.White else Color.Black,
                                    containerColor = if (state.selectComments == 4) colorResource(
                                        R.color.Orange
                                    ) else colorResource(R.color.FillTextField)
                                )
                            ) {
                                Text(
                                    text = "Больше двух",
                                    color = Color.Unspecified,
                                    fontFamily = interBold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                vm.onEvent(EmployeeSearchEvent.ApplyFilters)
                                if (state.searchText.isNotEmpty()) {
                                    vm.onEvent(EmployeeSearchEvent.Search)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = colorResource(R.color.Orange),
                                    shape = RoundedCornerShape(15.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = colorResource(R.color.Orange)
                            )
                        ) {
                            Text(
                                text = "Применить",
                                color = Color.White,
                                fontFamily = interBold,
                                fontSize = 16.sp
                            )
                        }
                        Button(
                            onClick = {
                                vm.onEvent(EmployeeSearchEvent.ShowFilter)
                            },
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .weight(1f)
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
                                text = "Отмена",
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