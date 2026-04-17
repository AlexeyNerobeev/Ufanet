package com.example.ufanet.feature_app.presentation.Map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ufanet.R
import com.example.ufanet.common.interBold
import com.example.ufanet.common.interRegular
import com.example.ufanet.common.ptSansBold
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun MapScreen(navController: NavController, applicationId: Int, vm: MapVM = hiltViewModel()) {
    val state = vm.state.value
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val userObjects = remember { mapView.map.mapObjects.addCollection() }
    val enterpriseObjects = remember { mapView.map.mapObjects.addCollection() }
    val routeObjects = remember { mapView.map.mapObjects.addCollection() }
    var permissionGranted by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(state.userLocation) {
        state.userLocation?.let {
            vm.onEvent(MapEvent.GetApplicationMapInfo(applicationId))
        }
    }

    LaunchedEffect(state.route) {
        state.route?.let { polyline ->

            routeObjects.clear()

            val mapPolyline = routeObjects.addPolyline(polyline)

            mapPolyline.strokeWidth = 5f
            mapPolyline.setStrokeColor(android.graphics.Color.rgb(255, 140, 0))
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F7FA)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            DisposableEffect(Unit) {
                MapKitFactory.getInstance().onStart()
                mapView.onStart()

                onDispose {
                    mapView.onStop()
                    MapKitFactory.getInstance().onStop()
                }
            }

            if (!permissionGranted) {
                RequestLocationPermission {
                    permissionGranted = true
                }
            }

            LaunchedEffect(permissionGranted) {
                if (permissionGranted) {
                    getCurrentLocation(context) { point ->
                        vm.onEvent(MapEvent.OnUserLocationReceived(point))
                    }
                }
            }

            LaunchedEffect(state.enterprisePoint) {
                state.enterprisePoint?.let {
                    val placemark = enterpriseObjects.addPlacemark(state.enterprisePoint)
                    placemark.setIcon(
                        ImageProvider.fromResource(context, R.drawable.enterprise_location)
                    )
                    mapView.map.move(
                        CameraPosition(state.enterprisePoint, 16f, 0f, 0f)
                    )
                }
            }

            LaunchedEffect(state.userLocation) {
                state.userLocation?.let {
                    val placemark = userObjects.addPlacemark(it)

                    placemark.setIcon(
                        ImageProvider.fromResource(context, R.drawable.user_location)
                    )
                }
            }

            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .shadow(8.dp, CircleShape)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow),
                        contentDescription = "Назад",
                        tint = colorResource(R.color.Orange),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column {
                    IconButton(
                        onClick = {
                            state.userLocation?.let {
                                mapView.map.move(
                                    CameraPosition(state.userLocation, 16f, 0f, 0f)
                                )
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .shadow(8.dp, CircleShape)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.location_pin_icon),
                                contentDescription = null,
                                tint = colorResource(R.color.Orange),
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            state.enterprisePoint?.let {
                                mapView.map.move(
                                    CameraPosition(state.enterprisePoint, 16f, 0f, 0f)
                                )
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 10.dp,
                                end = 20.dp,
                                bottom = 20.dp)

                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .shadow(8.dp, CircleShape)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.company_icon),
                                contentDescription = null,
                                tint = colorResource(R.color.Orange),
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        if (state.isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(48.dp),
                                    color = colorResource(R.color.Orange)
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
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
                                                .size(48.dp)
                                                .background(
                                                    color = colorResource(R.color.Orange).copy(alpha = 0.1f),
                                                    shape = RoundedCornerShape(12.dp)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.company_icon),
                                                contentDescription = null,
                                                tint = colorResource(R.color.Orange),
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = state.enterpriseName,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            fontFamily = ptSansBold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                InfoRow(
                                    label = "Адрес",
                                    value = state.enterpriseAddress,
                                    icon = R.drawable.location_pin_icon
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                InfoRow(
                                    label = "Телефон",
                                    value = state.enterprisePhone,
                                    icon = R.drawable.phone_icon
                                )

                                if(state.timeText.isNotEmpty() && state.distanceText.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "🚗 ${state.timeText}",
                                            fontSize = 16.sp,
                                            fontFamily = interBold
                                        )

                                        Spacer(modifier = Modifier.width(16.dp))

                                        Text(
                                            text = state.distanceText,
                                            fontSize = 16.sp,
                                            fontFamily = interRegular
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = {
                                        vm.onEvent(MapEvent.BuildRoute)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.Orange)
                                    )
                                ) {
                                    if (state.isRouteLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = Color.White
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(R.drawable.route_icon),
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Построить маршрут",
                                            color = Color.White,
                                            fontFamily = interBold,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    icon: Int,
    maxLines: Int = 2
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = colorResource(R.color.Orange).copy(alpha = 0.7f),
            modifier = Modifier
                .size(18.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                color = Color.Gray,
                fontFamily = interRegular,
                fontSize = 13.sp
            )
            Text(
                text = value,
                color = Color.Black,
                fontFamily = interRegular,
                fontSize = 15.sp,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RequestLocationPermission(
    onGranted: () -> Unit
) {
    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onGranted()
            }
        }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    context: Context,
    onLocation: (Point) -> Unit
) {
    val client = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    client.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        location?.let {
            onLocation(Point(it.latitude, it.longitude))
        }
    }
}