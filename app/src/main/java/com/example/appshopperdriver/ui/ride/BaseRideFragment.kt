package com.example.appshopperdriver.ui.ride

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appshopperdriver.R
import com.example.appshopperdriver.util.DialogUtil
import com.example.appshopperdriver.util.RouteUtil.decodePolyline
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline


open class BaseRideFragment:  Fragment() {

    protected lateinit var myContext: Context

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onPermissionGranted()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context

        // Configurar o user agent do osmdroid globalmente
        val configuration = org.osmdroid.config.Configuration.getInstance()
        if (configuration.userAgentValue.isEmpty() || configuration.userAgentValue == "osmdroid") {
            configuration.userAgentValue = context.packageName
        }
    }

    protected fun checkLocationPermission(actionOnGranted: () -> Unit) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permissão já concedida, execute a ação
                actionOnGranted()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Mostrar explicação para o usuário
                mostrarExplicacaoPermissao {
                    // Após a explicação, solicitar a permissão novamente
                    requestLocationPermission()
                }
            }

            else -> {
                // Solicitar a permissão diretamente
                requestLocationPermission()
            }
        }
    }

    private fun mostrarExplicacaoPermissao(onContinuar: () -> Unit) {

        DialogUtil.showInformationDialog(myContext,myContext.getString(R.string.dialog_location_permition_title),
            myContext.getString(R.string.dialog_location_permition_description),"OK",
            {
                onContinuar()
            })
    }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    open fun onPermissionGranted() {
        // Subclasses podem implementar para carregar o mapa ou outras lógicas
    }

    // Método genérico para atualizar o mapa com uma localização e marcador
    protected fun updateMapLocation(mapView: org.osmdroid.views.MapView, geoPoint: GeoPoint) {
        val controller = mapView.controller
        controller.setZoom(15.0)
        controller.setCenter(geoPoint)

        val marker = Marker(mapView)
        marker.position = geoPoint
        marker.title =  myContext.getString(R.string.maps_title)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapView.overlays.clear()
        mapView.overlays.add(marker)
    }


    fun showRouteOnMap(mapView: MapView, encodedPolyline: String) {
        // Decodifica o polyline
        val routePoints = decodePolyline(encodedPolyline)

        if (routePoints.isNotEmpty()) {
            // Cria a Polyline e define os pontos
            val polyline = Polyline().apply {
                setPoints(routePoints)
                title = myContext.getString(R.string.maps_title_route)
            }

            // Adiciona a Polyline ao mapa
            mapView.overlays.add(polyline)

            // Obter pontos inicial e final
            val startPoint = routePoints.first()
            val endPoint = routePoints.last()

            // Adiciona marcador para o ponto inicial (A)
            val markerA = Marker(mapView).apply {
                position = startPoint
                title = "A"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
            mapView.overlays.add(markerA)

            // Adiciona marcador para o ponto final (B)
            val markerB = Marker(mapView).apply {
                position = endPoint
                title = "B"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
            mapView.overlays.add(markerB)

            // Ajusta o zoom para mostrar a rota completa
            val boundingBox = org.osmdroid.util.BoundingBox.fromGeoPoints(routePoints)
            mapView.zoomToBoundingBox(boundingBox, true)
        }

        // Atualiza o mapa
        mapView.invalidate()
    }




}