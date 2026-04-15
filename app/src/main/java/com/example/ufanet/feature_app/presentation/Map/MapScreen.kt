package com.example.ufanet.feature_app.presentation.Map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ufanet.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun MapScreen(navController: NavController, vm: MapVM = hiltViewModel()) {
    val state = vm.state.value
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val mapObjects = remember { mapView.map.mapObjects.addCollection() }
    var permissionGranted by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.White)
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
                mapObjects.clear()
                val placemark = mapObjects.addPlacemark(state.enterprisePoint)
                placemark.setIcon(
                    ImageProvider.fromResource(context, R.drawable.enterprise_location)
                )
                    }
            }

            LaunchedEffect(state.userLocation) {
                state.userLocation?.let {
                    val placemark = mapObjects.addPlacemark(it)

                    placemark.setIcon(
                        ImageProvider.fromResource(context, R.drawable.user_location)
                    )

                    mapView.map.move(
                        CameraPosition(state.userLocation, 16f, 0f, 0f)
                    )
                }
            }

            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
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

    client.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        if (location != null) {
            onLocation(Point(location.latitude, location.longitude))
        }
    }
}