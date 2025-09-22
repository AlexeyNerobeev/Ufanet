package com.example.ufanet.feature_app.presentation.Applications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.BottomNavigation
import com.example.ufanet.common.ErrorAlertDialog
import com.example.ufanet.common.interBold
import com.example.ufanet.common.ptSansBold
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationUseCase
import com.example.ufanet.feature_app.presentation.SignIn.SignInEvent
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun PrevApplications() {
    ApplicationsScreen(itemId = 0, rememberNavController())
}

@Composable
fun ApplicationsScreen(
    itemId: Int,
    navController: NavController,
    vm: ApplicationsVM = koinViewModel()
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
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(
                text =
                    if (state.id > 0) {
                        "Изменение заявки"
                    } else{
                        "Создание заявки"
                    }
                ,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.Black,
                fontFamily = ptSansBold,
                fontSize = 25.sp
            )
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 36.dp)
            ) {
                item {
                    Text(
                        text = "Название организации:",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontFamily = ptSansBold
                    )
                    TextField(
                        value = state.companyName,
                        onValueChange = {
                            vm.onEvent(ApplicationsEvent.EnteredCompanyName(it))
                        },
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(R.color.Orange),
                            unfocusedIndicatorColor = colorResource(R.color.Orange)
                        )
                    )
                    Text(
                        text = "Адрес:",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontFamily = ptSansBold,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                    TextField(
                        value = state.address,
                        onValueChange = {
                            vm.onEvent(ApplicationsEvent.EnteredAddress(it))
                        },
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(R.color.Orange),
                            unfocusedIndicatorColor = colorResource(R.color.Orange)
                        )
                    )
                    Text(
                        text = "Телефон:",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontFamily = ptSansBold,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                    TextField(
                        value = state.phone,
                        onValueChange = {
                            vm.onEvent(ApplicationsEvent.EnteredPhone(it))
                        },
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(R.color.Orange),
                            unfocusedIndicatorColor = colorResource(R.color.Orange)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    Text(
                        text = "Причина заявки:",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontFamily = ptSansBold,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                    TextField(
                        value = state.description,
                        onValueChange = {
                            vm.onEvent(ApplicationsEvent.EnteredDescription(it))
                        },
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(R.color.Orange),
                            unfocusedIndicatorColor = colorResource(R.color.Orange)
                        ),
                        textStyle = TextStyle(fontSize = 15.sp)
                    )
                    Button(
                        onClick = {
                            if (state.id > 0) {
                                vm.onEvent(ApplicationsEvent.UpdateApplication)
                            } else {
                                vm.onEvent(ApplicationsEvent.SaveApplication)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .height(60.dp)
                            .fillMaxWidth()
                            .background(
                                colorResource(R.color.Orange),
                                RoundedCornerShape(15.dp)
                            ),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Сохранить",
                            color = Color.White,
                            fontFamily = interBold,
                            fontSize = 18.sp
                        )
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
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigation(navController, 1)
        }
    }
}