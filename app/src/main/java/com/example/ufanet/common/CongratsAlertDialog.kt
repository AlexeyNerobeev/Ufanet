package com.example.ufanet.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp

@Composable
fun CongratsAlertDialog(ex: String,
                     click: () -> Unit) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = click,
        title = {
            Text(text = ex,
                color = Color.Black,
                fontSize = 20.sp)
        },
        confirmButton = {
            Button(
                onClick = click,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(com.example.ufanet.R.color.Orange),
                    contentColor = Color.White
                )
            ) {
                Text(text = "ОК",
                    color = Color.White,
                    fontSize = 14.sp)
            }
        }
    )
}