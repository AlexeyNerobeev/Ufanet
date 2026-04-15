package com.example.ufanet.feature_app.presentation.Applications

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.BottomNavigation
import com.example.ufanet.common.ErrorAlertDialog
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold

@Preview
@Composable
fun PrevApplications() {
    ApplicationsScreen(itemId = 0, rememberNavController())
}

@Composable
fun ApplicationsScreen(
    itemId: Int,
    navController: NavController,
    vm: ApplicationsVM = hiltViewModel()
) {
    val state = vm.state.value

    LaunchedEffect(key1 = !state.isComplete) {
        if (state.isComplete) {
            navController.navigate(NavRoutes.HomeScreen.route)
        }
        if (itemId > 0) {
            vm.onEvent(ApplicationsEvent.GetApplicationForUpdate(itemId))
        }
    }

    if (state.error.isNotEmpty()) {
        ErrorAlertDialog(ex = state.error) {
            vm.onEvent(ApplicationsEvent.ExceptionClear)
        }
    }

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
                        text = if (state.id > 0) "Изменение заявки" else "Создание заявки",
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                            .shadow(2.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
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
                                    painter = painterResource(R.drawable.application_icon),
                                    contentDescription = null,
                                    tint = colorResource(R.color.Orange),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (state.id > 0)
                                    "Редактирование заявки №${state.id}"
                                else
                                    "Заполните данные для новой заявки",
                                color = Color.Gray,
                                fontFamily = interRegular,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                item {
                    FormField(
                        label = "Название организации",
                        value = state.companyName,
                        onValueChange = { vm.onEvent(ApplicationsEvent.EnteredCompanyName(it)) },
                        placeholder = "Введите название организации",
                        icon = R.drawable.company_icon,
                        maxLines = 3
                    )
                }

                item {
                    FormField(
                        label = "Адрес",
                        value = state.address,
                        onValueChange = { vm.onEvent(ApplicationsEvent.EnteredAddress(it)) },
                        placeholder = "Введите адрес",
                        icon = R.drawable.location_pin_icon,
                        maxLines = 3
                    )
                }

                item {
                    FormField(
                        label = "Телефон",
                        value = state.phone,
                        onValueChange = { vm.onEvent(ApplicationsEvent.EnteredPhone(it)) },
                        placeholder = "Введите номер телефона",
                        icon = R.drawable.phone_icon,
                        keyboardType = KeyboardType.Phone,
                        maxLines = 1
                    )
                }

                item {
                    FormField(
                        label = "Причина заявки",
                        value = state.description,
                        onValueChange = { vm.onEvent(ApplicationsEvent.EnteredDescription(it)) },
                        placeholder = "Опишите причину обращения",
                        icon = R.drawable.description_icon,
                        maxLines = 4
                    )
                }

                item {
                    SaveButton(
                        isLoading = state.isLoading,
                        isEdit = state.id > 0,
                        onSave = {
                            if (state.id > 0) {
                                vm.onEvent(ApplicationsEvent.UpdateApplication)
                            } else {
                                vm.onEvent(ApplicationsEvent.SaveApplication)
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigation(navController, 1)
        }
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = 1
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = colorResource(R.color.Orange),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = Color.Black,
                fontFamily = interBold,
                fontSize = 15.sp
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (maxLines > 1) (maxLines * 24 + 40).dp else 56.dp),
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
                    fontSize = 15.sp,
                    fontFamily = interRegular
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                maxLines = maxLines,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
fun SaveButton(
    isLoading: Boolean,
    isEdit: Boolean,
    onSave: () -> Unit
) {
    Button(
        onClick = onSave,
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 16.dp),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.Orange),
            disabledContainerColor = Color.LightGray
        )
    ) {
        if (!isLoading) {
            Icon(
                painter = painterResource(
                    if (isEdit) R.drawable.save_icon else R.drawable.add_icon
                ),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isEdit) "Сохранить изменения" else "Создать заявку",
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