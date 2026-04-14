package com.example.ufanet.feature_app.presentation.Comments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.EmployeeBottomNavigation
import com.example.ufanet.common.ErrorAlertDialog
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import org.koin.androidx.compose.koinViewModel

@Composable
fun CommentsScreen(itemId: Int, navController: NavController, vm: CommentsVM = koinViewModel()) {
    val state = vm.state.value

    LaunchedEffect(key1 = !state.isComplete) {
        if (state.isComplete) {
            navController.navigate(NavRoutes.EmployeeHomeScreen.route)
        }
        if (itemId > 0) {
            vm.onEvent(CommentsEvent.GetApplicationId(itemId))
            vm.onEvent(CommentsEvent.GetAllInfo)
        } else {
            vm.onEvent(CommentsEvent.ShowNotSelectItems)
        }
    }

    if (state.error.isNotEmpty()) {
        ErrorAlertDialog(state.error) {
            vm.onEvent(CommentsEvent.ErrorClear)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F7FA)
    ) { innerPadding ->
        if (state.showNotSelectItems) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EmptySelectionView(navController)
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Заголовок
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
                            text = "Комментарии к заявке",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = ptSansBold,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.size(40.dp))
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp)
                ) {
                    item {
                        StatusCard(
                            currentStatus = state.applicationStatus,
                            onStatusChange = { newStatus ->
                                when (newStatus) {
                                    "Не принята" -> vm.onEvent(CommentsEvent.ApplicationStatusChangeNotAccepted)
                                    "Принята" -> vm.onEvent(CommentsEvent.ApplicationStatusChangeAccepted)
                                    "Выполнена" -> vm.onEvent(CommentsEvent.ApplicationStatusChangeCompleted)
                                }
                            }
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Комментарии (${state.commentsList.size})",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontFamily = ptSansBold
                            )

                            if (!state.newComment) {
                                Button(
                                    onClick = { vm.onEvent(CommentsEvent.AddNewComment) },
                                    modifier = Modifier
                                        .height(40.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.Orange)
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.add_icon),
                                        contentDescription = "Добавить комментарий",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Добавить",
                                        color = Color.White,
                                        fontFamily = interBold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    if (state.commentsList.isNotEmpty()) {
                        items(state.commentsList) { item ->
                            CommentCard(
                                comment = item,
                                index = state.commentsList.indexOf(item) + 1
                            )
                        }
                    } else {
                        item {
                            EmptyCommentsView()
                        }
                    }

                    if (state.newComment) {
                        item {
                            NewCommentCard(
                                commentText = state.commentDescription ?: "",
                                onCommentChange = { vm.onEvent(CommentsEvent.EnteredCommentDescription(it)) }
                            )
                        }
                    }

                    item {
                        SaveButton(
                            hasChanges = state.currentApplicationStatus != state.applicationStatus
                                    || (state.commentDescription?.isNotEmpty() == true),
                            isLoading = state.progressIndicator,
                            onSave = {
                                if (state.currentApplicationStatus != state.applicationStatus
                                    || (state.commentDescription?.isNotEmpty() == true)
                                ) {
                                    vm.onEvent(CommentsEvent.ShowProcessIndicator)
                                    vm.onEvent(CommentsEvent.UpdateStatus)
                                    vm.onEvent(CommentsEvent.WriteNewComment)
                                } else {
                                    vm.onEvent(CommentsEvent.ShowError)
                                }
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        EmployeeBottomNavigation(navController, 1)
    }
}

@Composable
fun StatusCard(
    currentStatus: String,
    onStatusChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
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
                Text(
                    text = "Статус заявки",
                    color = Color.Gray,
                    fontFamily = interRegular,
                    fontSize = 14.sp
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = when (currentStatus.lowercase()) {
                                "принята" -> Color(0xFFFFA726)
                                "выполнена" -> Color(0xFF66BB6A)
                                "не принята" -> Color(0xFFEF5350)
                                else -> colorResource(R.color.Orange)
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = currentStatus,
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Изменить статус",
                color = Color.Black,
                fontFamily = interBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    StatusButton(
                        text = "Не принята",
                        color = Color(0xFFEF5350),
                        isSelected = currentStatus == "Не принята",
                        onClick = { onStatusChange("Не принята") }
                    )
                }
                item {
                    StatusButton(
                        text = "Принята",
                        color = Color(0xFFFFA726),
                        isSelected = currentStatus == "Принята",
                        onClick = { onStatusChange("Принята") }
                    )
                }
                item {
                    StatusButton(
                        text = "Выполнена",
                        color = Color(0xFF66BB6A),
                        isSelected = currentStatus == "Выполнена",
                        onClick = { onStatusChange("Выполнена") }
                    )
                }
            }
        }
    }
}

@Composable
fun StatusButton(
    text: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(40.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else Color.White,
            contentColor = if (isSelected) Color.White else Color.Gray
        ),
        border = if (!isSelected) {
            ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(color.copy(alpha = 0.5f)),
                width = 1.dp
            )
        } else null
    ) {
        Text(
            text = text,
            fontFamily = interBold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun CommentCard(
    comment: com.example.ufanet.feature_app.domain.models.Comment,
    index: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp)),
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = index.toString(),
                            color = colorResource(R.color.Orange),
                            fontFamily = interBold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Комментарий #$index",
                        color = Color.Black,
                        fontFamily = interBold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = comment.comment_text,
                color = Color.DarkGray,
                fontFamily = interRegular,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun NewCommentCard(
    commentText: String,
    onCommentChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            colorResource(R.color.Orange).copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Новый комментарий",
                    color = Color.Black,
                    fontFamily = interBold,
                    fontSize = 16.sp
                )
            }

            TextField(
                value = commentText,
                onValueChange = onCommentChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = {
                    Text(
                        text = "Введите текст комментария...",
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
                    focusedIndicatorColor = colorResource(R.color.Orange),
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = colorResource(R.color.Orange)
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
fun SaveButton(
    hasChanges: Boolean,
    isLoading: Boolean,
    onSave: () -> Unit
) {
    Button(
        onClick = onSave,
        enabled = hasChanges && !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 24.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.Orange),
            disabledContainerColor = Color.LightGray
        )
    ) {
        if (!isLoading) {
            Icon(
                painter = painterResource(R.drawable.save_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Сохранить изменения",
                color = Color.White,
                fontFamily = interBold,
                fontSize = 16.sp
            )
        } else {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun EmptyCommentsView() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
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
                    .size(60.dp)
                    .background(
                        color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.application_icon),
                    contentDescription = null,
                    tint = colorResource(R.color.Orange).copy(alpha = 0.5f),
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Нет комментариев",
                color = Color.Black,
                fontFamily = interBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Нажмите 'Добавить' чтобы оставить комментарий",
                color = Color.Gray,
                fontFamily = interRegular,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun EmptySelectionView(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
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
                    .size(100.dp)
                    .background(
                        color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = null,
                    tint = colorResource(R.color.Orange),
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Заявка не выбрана",
                fontSize = 22.sp,
                fontFamily = ptSansBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Выберите заявку для просмотра комментариев",
                fontSize = 14.sp,
                fontFamily = interRegular,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(NavRoutes.EmployeeHomeScreen.route) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Orange)
                    )
                ) {
                    Text(
                        text = "На главную",
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { navController.navigate(NavRoutes.EmployeeSearchScreen.route) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(colorResource(R.color.Orange)),
                        width = 1.dp
                    )
                ) {
                    Text(
                        text = "Поиск",
                        color = colorResource(R.color.Orange),
                        fontFamily = interBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}