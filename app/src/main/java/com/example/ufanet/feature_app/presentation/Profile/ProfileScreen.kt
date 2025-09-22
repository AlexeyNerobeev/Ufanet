package com.example.ufanet.feature_app.presentation.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.BottomNavigation
import com.example.ufanet.common.CongratsAlertDialog
import com.example.ufanet.common.ErrorAlertDialog
import com.example.ufanet.common.interBold
import com.example.ufanet.common.ptSansBold
import com.example.ufanet.feature_app.presentation.SignUp.SignUpEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavController, vm: ProfileVM = koinViewModel()) {
    val state = vm.state.value
    LaunchedEffect(key1 = null) {
        vm.onEvent(ProfileEvent.GetProfile)
    }
    if(state.info.isNotEmpty()){
        CongratsAlertDialog(state.info) {
            vm.onEvent(ProfileEvent.ClearInfo)
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                    .background(colorResource(R.color.Orange))
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 5.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.back_icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        text = "Профиль",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = ptSansBold
                    )
                    Icon(
                        painter = painterResource(R.drawable.account_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
            Column(modifier = Modifier
                .padding(top = 35.dp)
                .padding(horizontal = 36.dp)
                .fillMaxWidth()) {
                Icon(painterResource(R.drawable.client_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally))
                Text(text = state.email,
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally))
                Text(text = "Название организации",
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 40.dp))
                OutlinedTextField(value = state.companyName,
                    onValueChange = {
                        vm.onEvent(ProfileEvent.EnteredCompanyName(it))
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
                Text(text = "Телефон",
                    color = Color.Black,
                    fontFamily = ptSansBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 26.dp))
                OutlinedTextField(value = state.phone,
                    onValueChange = {
                        vm.onEvent(ProfileEvent.EnteredPhone(it))
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
                Button(onClick = {
                    vm.onEvent(ProfileEvent.UpdateProfile)

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
                    Text(text = "Обновить профиль",
                        color = Color.White,
                        fontFamily = interBold,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigation(navController, 3)
        }
    }
}