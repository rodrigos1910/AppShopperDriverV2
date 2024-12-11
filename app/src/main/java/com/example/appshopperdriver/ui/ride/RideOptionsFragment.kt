package com.example.appshopperdriver.ui.ride

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshopperdriver.R
import com.example.appshopperdriver.databinding.FragmentRideOptionsBinding
import com.example.appshopperdriver.model.ride.DriverModel
import com.example.appshopperdriver.util.DialogUtil


class RideOptionsFragment : BaseRideFragment(), View.OnClickListener {

    private lateinit var viewModel: RideViewModel

    private var _binding: FragmentRideOptionsBinding? = null
    private val binding get() = _binding!!

    private val adapter = RideDriverAdapter()



    private val navController: NavController by lazy {
        findNavController()
    }
    private var directions: NavDirections? = null

    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RideViewModel::class.java)
        _binding = FragmentRideOptionsBinding.inflate(inflater, container, false)

        binding.recyclerDrivers.layoutManager = LinearLayoutManager(context)
        binding.recyclerDrivers.adapter = adapter


        // Verifique permissão e obtenha localização
        checkLocationPermission {
            viewModel.loadCurrentLocation()
        }

        registerViewListeners()

        //carrega dados já existentes
        viewModel.loadRide()

        //Observer
        observe()

        val listener = object : RideDriverListener {
            override fun onSelectItem(driver: DriverModel, remove: Boolean) {
                super.onSelectItem(driver, remove)

                if (remove) {
                    viewModel.removeSelectedDriver()
                } else {
                    viewModel.setSelectedDriver(driver)
                }

                //atualista list inteira apo´s termino da interacao
                binding.recyclerDrivers.post {
                    adapter.notifyDataSetChanged()
                }


            }


        }

        adapter.attachListener(listener)

        return  binding.root
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.ride_buttonConfirmRide ->{
                handleConfirmRide()
            }
        }
    }


    fun handleConfirmRide(){
        dialogLoading = DialogUtil.showLoadingDialog(myContext,myContext.getString(R.string.dialog_loading_ride))

        viewModel.confirmRide()
    }


    private fun observe() {

        viewModel.loadRide.observe(viewLifecycleOwner){
            if (it != null) {
                dialogLoading = DialogUtil.showLoadingDialog(myContext,myContext.getString(R.string.dialog_loading_drivers))

                viewModel.requestDrivers(it)
            }
        }
        viewModel.listDrivers.observe(viewLifecycleOwner){

            adapter.update(it)

            if (::dialogLoading.isInitialized) {
                dialogLoading.dismiss()
            }
        }

        viewModel.loadDrivers.observe(viewLifecycleOwner){
            if (::dialogLoading.isInitialized) {
                dialogLoading.dismiss()
            }
            if (!it.status()) {
                DialogUtil.showErrorDialog(myContext,it.message())
                navController.popBackStack()
            }

        }
        viewModel.currentLocation.observe(viewLifecycleOwner) { geoPoint ->
            geoPoint?.let {
                updateMapLocation(binding.rideMapDriver, it)
            }
        }
        viewModel.listRoute.observe(viewLifecycleOwner) { encodedPolyline ->
            if (encodedPolyline != null) {
                showRouteOnMap(binding.rideMapDriver, encodedPolyline)
            }
        }
        viewModel.confirmRide.observe(viewLifecycleOwner){
            if (::dialogLoading.isInitialized) {
                dialogLoading.dismiss()
            }
            if (!it.status()) {
                DialogUtil.showErrorDialog(myContext,it.message())
            }

        }
        viewModel.finishRide.observe(viewLifecycleOwner){
            if (::dialogLoading.isInitialized) {
                dialogLoading.dismiss()
            }
            if (it) {

                directions = RideOptionsFragmentDirections.actionRideOptionsFragmentToOrders()
                navController.navigate(directions!!)
            }

        }

    }

    private fun registerViewListeners() {
        binding.rideButtonConfirmRide.setOnClickListener(this)
    }


}