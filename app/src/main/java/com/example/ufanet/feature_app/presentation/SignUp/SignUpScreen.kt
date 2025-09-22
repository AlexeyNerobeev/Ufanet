package com.example.ufanet.feature_app.presentation.SignUp

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
import com.example.ufanet.common.ErrorAlertDialog
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun PrevSignUp(){
    SignUpScreen(rememberNavController())
}

@Composable
fun SignUpScreen(navController: NavController, vm: SignUpVM = koinViewModel()) {
    val state = vm.state.value
    LaunchedEffect(key1 = !state.isComplete) {
        if(state.isComplete){
            navController.navigate(NavRoutes.HomeScreen.route)
        }
    }
    if(state.exception.isNotEmpty()){
        ErrorAlertDialog(state.exception) {
            vm.onEvent(SignUpEvent.ExceptionClear)
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)) {
            Text(text = "Регистрация",
                color = Color.Black,
                fontFamily = ptSansBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Column(modifier = Modifier
                .padding(top = 50.dp)
                .padding(horizontal = 36.dp)
                .fillMaxWidth()) {
                Text(text = "НАЗВАНИЕ ОРГАНИЗАЦИИ",
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 13.sp)
                OutlinedTextField(value = state.companyName,
                    onValueChange = {
                        vm.onEvent(SignUpEvent.EnteredCompanyName(it))
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        focusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        unfocusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        focusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    singleLine = true
                )
                Text(text = "ТЕЛЕФОН",
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 26.dp))
                OutlinedTextField(value = state.phone,
                    onValueChange = {
                        vm.onEvent(SignUpEvent.EnteredPhone(it))
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        focusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        unfocusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        focusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    singleLine = true
                )
                Text(text = "ЭЛЕКТРОННАЯ ПОЧТА",
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 26.dp))
                OutlinedTextField(value = state.email,
                    onValueChange = {
                        vm.onEvent(SignUpEvent.EnteredEmail(it))
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        focusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        unfocusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        focusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    singleLine = true
                )
                Text(text = "ПАРОЛЬ",
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                OutlinedTextField(value = state.password,
                    onValueChange = {
                        vm.onEvent(SignUpEvent.EnteredPassword(it))
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        focusedBorderColor = colorResource(com.example.ufanet.R.color.StrokeTextField),
                        unfocusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        focusedContainerColor = colorResource(com.example.ufanet.R.color.FillTextField),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    visualTransformation =
                    if(state.visual){
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    singleLine = true,
                    trailingIcon = {
                        Icon(painter = painterResource(com.example.ufanet.R.drawable.password_icon),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .padding(end = 19.dp)
                                .padding(top = 11.dp)
                                .padding(bottom = 11.dp)
                                .clickable{
                                    vm.onEvent(SignUpEvent.VisualTransformation)
                                })
                    }
                )
                Button(onClick = {
                    vm.onEvent(SignUpEvent.Registration)
                },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(colorResource(com.example.ufanet.R.color.Orange),
                            RoundedCornerShape(15.dp)),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Transparent
                    )) {
                    Text(text = "Зарегистрироваться",
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
                Text(text = "Уже есть учетная запись? ",
                    color = Color.Gray,
                    fontFamily = interRegular,
                    fontSize = 15.sp)
                Text(text = "Войти",
                    fontSize = 15.sp,
                    fontFamily = interRegular,
                    color = colorResource(com.example.ufanet.R.color.Orange),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate(NavRoutes.SignInScreen.route)
                        })
                )
            }
        }
    }
}