package com.example.ufanet.feature_app.presentation.Map

import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetApplicationMapInfoUseCase
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouterType
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.runtime.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapVM @Inject constructor(
    private val getApplicationMapInfoUseCase: GetApplicationMapInfoUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MapState())
    val state: State<MapState> = _state
    var drivingRouter = DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.COMBINED)

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnUserLocationReceived -> {
                _state.value = state.value.copy(
                    userLocation = event.point
                )
            }

            is MapEvent.GetApplicationMapInfo -> {
                _state.value = state.value.copy(
                    applicationId = event.value
                )
                viewModelScope.launch {
                    try {
                        val info = withContext(Dispatchers.IO) {
                            getApplicationMapInfoUseCase.invoke(state.value.applicationId)
                        }

                        _state.value = state.value.copy(
                            enterpriseName = info.company_name,
                            enterprisePhone = info.phone,
                            enterpriseAddress = info.address,
                            isLoading = false
                        )

                        val searchManager = SearchFactory.getInstance()
                            .createSearchManager(SearchManagerType.ONLINE)

                        searchManager.submit(
                            info.address,
                            Geometry.fromPoint(state.value.userLocation ?: Point(0.0, 0.0)),
                            SearchOptions(),
                            object : Session.SearchListener {

                                override fun onSearchResponse(response: Response) {
                                    val point = response.collection.children.firstOrNull()
                                        ?.obj?.geometry?.firstOrNull()?.point

                                    point?.let {
                                        _state.value = state.value.copy(
                                            enterprisePoint = it
                                        )
                                    }
                                }

                                override fun onSearchError(error: Error) {
                                    Log.e("Geocoder", error.toString())
                                }
                            }
                        )

                    } catch (e: Exception) {
                        Log.e("getApplicationMapInfo", e.message.toString())
                    }
                }
            }

            MapEvent.BuildRoute -> {
                buildRoute()
            }
        }
    }

    fun buildRoute() {
        val user = state.value.userLocation
        val enterprise = state.value.enterprisePoint

        Log.d("RouteDebug", "user=$user enterprise=$enterprise")

        if (user == null || enterprise == null) return

        _state.value = state.value.copy(isRouteLoading = true)

        val requestPoints = listOf(
            RequestPoint(user, RequestPointType.WAYPOINT, null, null, null),
            RequestPoint(enterprise, RequestPointType.WAYPOINT, null, null, null)
        )

        drivingRouter.requestRoutes(
            requestPoints,
            DrivingOptions(),
            VehicleOptions(),
            object : DrivingSession.DrivingRouteListener {

                override fun onDrivingRoutes(routes: MutableList<DrivingRoute>) {
                    Log.d("RouteDebug", "routes=${routes.size}")

                    if (routes.isNotEmpty()) {
                        _state.value = state.value.copy(
                            route = routes.first().geometry,
                            isRouteLoading = false
                        )
                    }
                }

                override fun onDrivingRoutesError(error: Error) {
                    Log.e("RouteError", error.toString())
                    _state.value = state.value.copy(isRouteLoading = false)
                }
            }
        )
    }
}