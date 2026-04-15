package com.example.ufanet.feature_app.presentation.EmployeeProfile

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.CongratsAlertDialog
import com.example.ufanet.common.EmployeeBottomNavigation
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold

@Composable
fun EmployeeProfileScreen(navController: NavController, vm: EmployeeProfileVM = hiltViewModel()) {
    val state = vm.state.value

    LaunchedEffect(key1 = null, key2 = !state.signOut) {
        vm.onEvent(EmployeeProfileEvent.GetProfile)
        if (state.signOut) {
            navController.navigate(NavRoutes.SignInScreen.route) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

    if (state.info.isNotEmpty()) {
        CongratsAlertDialog(state.info) {
            vm.onEvent(EmployeeProfileEvent.ClearInfo)
        }
    }

    if (state.alertSignOut) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { vm.onEvent(EmployeeProfileEvent.ShowAlertSignOut) },
            shape = RoundedCornerShape(20.dp),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.signout_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Выход из аккаунта",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontFamily = ptSansBold
                    )
                }
            },
            text = {
                Text(
                    text = "Вы уверены, что хотите выйти из профиля?",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontFamily = interRegular
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        vm.onEvent(EmployeeProfileEvent.SignOut)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Orange),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Выйти",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = interBold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { vm.onEvent(EmployeeProfileEvent.ShowAlertSignOut) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Отмена",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = interBold
                    )
                }
            }
        )
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
                        text = "Профиль сотрудника",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontFamily = ptSansBold,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    IconButton(
                        onClick = { vm.onEvent(EmployeeProfileEvent.ShowAlertSignOut) },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.signout_icon),
                            contentDescription = "Выйти",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
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
                                painter = painterResource(R.drawable.client_icon),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(60.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = state.email,
                            color = Color.Black,
                            fontFamily = ptSansBold,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Сотрудник",
                            color = Color.Gray,
                            fontFamily = interRegular,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Информация об организации",
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                EmployeeProfileField(
                    label = "Название организации",
                    value = state.companyName,
                    onValueChange = { vm.onEvent(EmployeeProfileEvent.EnteredCompanyName(it)) },
                    icon = R.drawable.company_icon,
                    placeholder = "Введите название организации"
                )

                EmployeeProfileField(
                    label = "Телефон",
                    value = state.phone,
                    onValueChange = { vm.onEvent(EmployeeProfileEvent.EnteredPhone(it)) },
                    icon = R.drawable.phone_icon,
                    placeholder = "Введите номер телефона"
                )

                Button(
                    onClick = { vm.onEvent(EmployeeProfileEvent.UpdateProfile) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 16.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Orange)
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.save_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Обновить профиль",
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            EmployeeBottomNavigation(navController, 4)
        }
    }
}

@Composable
fun EmployeeProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Int,
    placeholder: String
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
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}