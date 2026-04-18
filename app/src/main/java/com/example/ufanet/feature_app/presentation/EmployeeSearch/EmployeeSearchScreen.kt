package com.example.ufanet.feature_app.presentation.EmployeeSearch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
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
fun EmployeeSearchScreen(navController: NavController, vm: EmployeeSearchVM = hiltViewModel()) {
    val state = vm.state.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F7FA)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
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
                            enabled = !state.showFilter,
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
                            text = "Поиск заявок",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = ptSansBold,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        IconButton(
                            onClick = { vm.onEvent(EmployeeSearchEvent.ShowFilter) },
                            enabled = !state.showFilter,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.filter_icon),
                                contentDescription = "Фильтр",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                SearchField(
                    value = state.searchText,
                    onValueChange = { vm.onEvent(EmployeeSearchEvent.EnteredSearchText(it)) },
                    placeholder = state.placeholder,
                    onSearch = { vm.onEvent(EmployeeSearchEvent.Search) },
                    enabled = !state.showFilter
                )

                if (state.applicationsList.isNotEmpty() && !state.showFilter) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 8.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Найдено заявок: ${state.applicationsList.size}",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontFamily = interRegular
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = !state.showFilter
                ) {
                    if (state.applicationsList.isEmpty() && !state.showFilter) {
                        item {
                            EmptySearchResult(
                                searchPerformed = state.searchText.isNotEmpty() ||
                                        state.selectSearch > 0 ||
                                        state.selectStatus > 0 ||
                                        state.selectComments > 0
                            )
                        }
                    } else {
                        items(state.applicationsList) { item ->
                            SearchResultCard(
                                application = item,
                                onClick = {
                                    navController.navigate(NavRoutes.CommentsScreen.createRoute(item.id))
                                },
                                enabled = !state.showFilter,
                                navController = navController,
                                itemId = item.id
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.BottomCenter
            ) {
                EmployeeBottomNavigation(navController, 3)
            }

            if (state.showFilter) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { vm.onEvent(EmployeeSearchEvent.ShowFilter) }
                )
            }
        }
    }

    AnimatedVisibility(
        visible = state.showFilter,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 300),
            initialOffsetY = { fullHeight -> fullHeight }
        ) + fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 300),
            targetOffsetY = { fullHeight -> fullHeight }
        ) + fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        var dragOffset by remember { mutableStateOf(0f) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragEnd = {
                            if (dragOffset > 100f) {
                                vm.onEvent(EmployeeSearchEvent.ShowFilter)
                            }
                            dragOffset = 0f
                        },
                        onDragCancel = { dragOffset = 0f },
                        onVerticalDrag = { _, dragAmount ->
                            dragOffset += dragAmount
                        }
                    )
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            FilterBottomSheet(
                state = state,
                onEvent = { vm.onEvent(it) }
            )
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onSearch: () -> Unit,
    enabled: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontFamily = interRegular,
                    fontSize = 14.sp
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(R.color.Orange)
            ),
            textStyle = TextStyle(
                fontFamily = interRegular,
                fontSize = 15.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                IconButton(
                    onClick = onSearch,
                    enabled = enabled
                ) {
                    Icon(
                        painter = painterResource(R.drawable.search_icon),
                        contentDescription = "Поиск",
                        tint = colorResource(R.color.Orange)
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onAny = {onSearch()}
            )
        )
    }
}

@Composable
fun SearchResultCard(
    application: Application,
    onClick: () -> Unit,
    enabled: Boolean,
    navController: NavController,
    itemId: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable(enabled = enabled) { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                            .size(36.dp)
                            .background(
                                color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.company_icon),
                            contentDescription = null,
                            tint = colorResource(R.color.Orange),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = application.company_name,
                        color = Color.Black,
                        fontFamily = interBold,
                        fontSize = 16.sp,
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
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = application.status,
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            InfoRowCompact(
                icon = R.drawable.location_pin_icon,
                text = application.address,
                navController = navController,
                itemId = itemId
            )

            InfoRowCompact(
                icon = R.drawable.phone_icon,
                text = application.phone,
                navController = navController,
                itemId = itemId
            )

            if (application.description.isNotEmpty()) {
                InfoRowCompact(
                    icon = R.drawable.description_icon,
                    text = application.description,
                    maxLines = 1,
                    navController = navController,
                    itemId = itemId
                )
            }
        }
    }
}

@Composable
fun InfoRowCompact(
    icon: Int,
    text: String,
    maxLines: Int = 1,
    navController: NavController,
    itemId: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = colorResource(R.color.Orange).copy(alpha = 0.6f),
            modifier = Modifier
                .size(14.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = if (icon == R.drawable.location_pin_icon) {
                Color.Blue
            } else {
                Color.Gray
            },
            fontFamily = interRegular,
            fontSize = 13.sp,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            textDecoration = if (icon == R.drawable.location_pin_icon) {
                TextDecoration.Underline
            } else {
                TextDecoration.None
            },
            modifier = Modifier
                .clickable{
                    navController.navigate(NavRoutes.MapScreen.createRoute(itemId))
                }
        )
    }
}

@Composable
fun EmptySearchResult(searchPerformed: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = null,
                    tint = colorResource(R.color.Orange).copy(alpha = 0.5f),
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (searchPerformed) "Ничего не найдено" else "Начните поиск",
                color = Color.Black,
                fontFamily = interBold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (searchPerformed)
                    "Попробуйте изменить параметры поиска"
                else
                    "Введите текст или используйте фильтры",
                color = Color.Gray,
                fontFamily = interRegular,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun FilterBottomSheet(
    state: EmployeeSearchState,
    onEvent: (EmployeeSearchEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
            Text(
                text = "Фильтры",
                color = Color.Black,
                fontSize = 22.sp,
                fontFamily = ptSansBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 30.dp)
            )
            FilterSection(
                title = "Поиск по",
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            text = "Названию",
                            isSelected = state.selectSearch == 1,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedSearch(
                                        if (state.selectSearch != 1) 1 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Адресу",
                            isSelected = state.selectSearch == 2,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedSearch(
                                        if (state.selectSearch != 2) 2 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Телефону",
                            isSelected = state.selectSearch == 3,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedSearch(
                                        if (state.selectSearch != 3) 3 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Описанию",
                            isSelected = state.selectSearch == 4,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedSearch(
                                        if (state.selectSearch != 4) 4 else 0
                                    )
                                )
                            }
                        )
                    }
                }
            }

            FilterSection(
                title = "Статус заявки",
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            text = "Не принята",
                            color = Color(0xFFEF5350),
                            isSelected = state.selectStatus == 1,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedStatus(
                                        if (state.selectStatus != 1) 1 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Принята",
                            color = Color(0xFFFFA726),
                            isSelected = state.selectStatus == 2,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedStatus(
                                        if (state.selectStatus != 2) 2 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Выполнена",
                            color = Color(0xFF66BB6A),
                            isSelected = state.selectStatus == 3,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedStatus(
                                        if (state.selectStatus != 3) 3 else 0
                                    )
                                )
                            }
                        )
                    }
                }
            }

            FilterSection(
                title = "Количество комментариев",
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            text = "Нет",
                            isSelected = state.selectComments == 1,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedComments(
                                        if (state.selectComments != 1) 1 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Один",
                            isSelected = state.selectComments == 2,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedComments(
                                        if (state.selectComments != 2) 2 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Два",
                            isSelected = state.selectComments == 3,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedComments(
                                        if (state.selectComments != 3) 3 else 0
                                    )
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            text = "Больше двух",
                            isSelected = state.selectComments == 4,
                            onClick = {
                                onEvent(
                                    EmployeeSearchEvent.SelectedComments(
                                        if (state.selectComments != 4) 4 else 0
                                    )
                                )
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        onEvent(EmployeeSearchEvent.ShowFilter)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color.LightGray),
                        width = 1.dp
                    )
                ) {
                    Text(
                        text = "Отменить",
                        color = Color.Gray,
                        fontFamily = interBold,
                        fontSize = 15.sp
                    )
                }

                Button(
                    onClick = {
                        onEvent(EmployeeSearchEvent.ApplyFilters)
                        onEvent(EmployeeSearchEvent.Search)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Orange)
                    )
                ) {
                    Text(
                        text = "Применить",
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 15.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FilterSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = Color.Black,
            fontFamily = interBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        content()
    }
}

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    color: Color = colorResource(R.color.Orange)
) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else Color.White,
            contentColor = if (isSelected) Color.White else Color.Gray
        ),
        border = if (!isSelected) {
            ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(Color.LightGray),
                width = 1.dp
            )
        } else null
    ) {
        Text(
            text = text,
            fontFamily = interRegular,
            fontSize = 13.sp
        )
    }
}