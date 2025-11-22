package com.example.ufanet.feature_app.presentation.Comments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import com.example.ufanet.common.interBold
import com.example.ufanet.common.ptSansBold
import com.example.ufanet.feature_app.presentation.Applications.ApplicationsEvent
import com.example.ufanet.feature_app.presentation.Home.HomeEvent
import com.example.ufanet.feature_app.presentation.SignIn.SignInEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun CommentsScreen(navController: NavController, vm: CommentsVM = koinViewModel()) {
    val state = vm.state.value
    Scaffold(modifier = Modifier.fillMaxSize()){ innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)) {
            Text(text = "Комментарии к заявке",
                color = Color.Black,
                fontFamily = ptSansBold,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(Alignment.CenterHorizontally))
            Column(modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 36.dp)) {
                Text(text = "Статус заявки: ${state.applicationStatus}",
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = ptSansBold)
                LazyRow(modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()) {
                    item {
                        Button(
                            onClick = {
                                vm.onEvent(CommentsEvent.ApplicationStatusChangeNotAccepted)
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
                                vm.onEvent(CommentsEvent.ApplicationStatusChangeAccepted)
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
                                vm.onEvent(CommentsEvent.ApplicationStatusChangeCompleted)
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
                Text(text = "Комментарии к заявке:",
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = ptSansBold,
                    modifier = Modifier
                        .padding(top = 20.dp))
                LazyColumn(modifier = Modifier
                    .padding(top = 10.dp)) {
                    items(10){
                        Text(text = "1.",
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontFamily = ptSansBold,
                            modifier = Modifier
                                .padding(top = 15.dp))
                        TextField(
                            value = "",
                            onValueChange = {

                            },
                            modifier = Modifier
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
                    }
                    item {
                        Column(modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()) {
                            if (!state.newComment) {
                                Button(
                                    onClick = {
                                        vm.onEvent(CommentsEvent.AddNewComment)
                                    },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
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
                                    Icon(
                                        painter = painterResource(R.drawable.add_icon),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            if (state.newComment) {
                                Text(
                                    text = "Новый комментарий",
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                    fontFamily = ptSansBold,
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                )
                                TextField(
                                    value = state.commentDescription,
                                    onValueChange = {
                                        vm.onEvent(CommentsEvent.EnteredCommentDescription(it))
                                    },
                                    modifier = Modifier
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
                            }
                        }
                    }
                    item {
                        Button(onClick = {

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
                            Text(text = "Сохранить",
                                color = Color.White,
                                fontFamily = interBold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}