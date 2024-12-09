package com.example.appshopperdriver.ui.ride

import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appshopperdriver.util.RouteUtil.decodePolyline
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline


open class BaseRideFragment:  Fragment() {

    protected lateinit var myContext: Context

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
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

    // Método genérico para verificar permissões
    protected fun checkLocationPermission(actionOnGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            actionOnGranted()
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Método para lidar com o resultado de permissões
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                Toast.makeText(myContext, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
        }
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
        marker.title = "Localização Atual"
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
                title = "Rota"
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