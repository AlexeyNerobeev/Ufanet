package com.example.ufanet.feature_app.presentation.SignIn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.ErrorAlertDialog
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun SignInPrev(){
    SignInScreen(rememberNavController())
}

@Composable
fun SignInScreen(navController: NavController, vm: SignInVM = koinViewModel()) {
    val state = vm.state.value
    LaunchedEffect(key1 = !state.isComplete) {
        if(state.isComplete){
            if(state.status == "клиент"){
                navController.navigate(NavRoutes.HomeScreen.route)
            } else if(state.status == "сотрудник"){
                navController.navigate(NavRoutes.EmployeeHomeScreen.route)
            }
        }
    }
    if(state.exception.isNotEmpty()){
        ErrorAlertDialog(state.exception) {
            vm.onEvent(SignInEvent.ExceptionClear)
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Вход",
                modifier = Modifier
                    .padding(top = 15.dp),
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = ptSansBold
            )
            Column(modifier = Modifier
                .padding(top = 138.dp)
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "ЭЛЕКТРОННАЯ ПОЧТА",
                    modifier = Modifier
                        .align(Alignment.Start),
                    fontFamily = ptSansBold,
                    fontSize = 13.sp,
                    color = Color.Black)
                OutlinedTextField(value = state.email,
                    onValueChange = {
                        vm.onEvent(SignInEvent.EnteredEmail(it))
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(R.color.StrokeTextField),
                        focusedBorderColor = colorResource(R.color.StrokeTextField),
                        unfocusedContainerColor = colorResource(R.color.FillTextField),
                        focusedContainerColor = colorResource(R.color.FillTextField),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    singleLine = true
                )
                Text(text = "ПАРОЛЬ",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Start),
                    fontFamily = ptSansBold,
                    fontSize = 13.sp,
                    color = Color.Black)
                OutlinedTextField(value = state.password,
                    onValueChange = {
                        vm.onEvent(SignInEvent.EnteredPassword(it))
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(R.color.StrokeTextField),
                        focusedBorderColor = colorResource(R.color.StrokeTextField),
                        unfocusedContainerColor = colorResource(R.color.FillTextField),
                        focusedContainerColor = colorResource(R.color.FillTextField),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    singleLine = true,
                    visualTransformation =
                    if (state.visual){
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    trailingIcon = {
                        Icon(painter = painterResource(R.drawable.password_icon),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .padding(end = 19.dp)
                                .padding(top = 11.dp)
                                .padding(bottom = 11.dp)
                                .clickable{
                                    vm.onEvent(SignInEvent.VisualTransformation)
                                })
                    }
                )
                Button(onClick = {
                    vm.onEvent(SignInEvent.SignIn)
                },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(colorResource(R.color.Orange),
                            RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Transparent
                    )) {
                    Text(text = "Войти",
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter){
            Row(modifier = Modifier
                .padding(bottom = 40.dp)) {
                Text(text = "Нет учетной записи? ",
                    color = Color.Gray,
                    fontFamily = interRegular,
                    fontSize = 15.sp)
                Text(text = "Регистрация",
                    fontSize = 15.sp,
                    fontFamily = interRegular,
                    color = colorResource(R.color.Orange),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate(NavRoutes.SignUpScreen.route)
                        })
                )
            }
        }
    }
}