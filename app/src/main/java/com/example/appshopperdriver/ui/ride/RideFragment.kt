package com.example.appshopperdriver.ui.ride

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.appshopperdriver.R
import com.example.appshopperdriver.databinding.FragmentRideBinding
import com.example.appshopperdriver.model.ride.RideModel
import com.example.appshopperdriver.service.constants.ShopperContants
import com.example.appshopperdriver.util.DialogUtil


class RideFragment : BaseRideFragment(), View.OnClickListener{

    private lateinit var viewModel: RideViewModel

    private var _binding: FragmentRideBinding? = null
    private val binding get() = _binding!!


    private val navController: NavController by lazy {
        findNavController()
    }
    private var directions: NavDirections? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RideViewModel::class.java)
        _binding = FragmentRideBinding.inflate(inflater, container, false)


        // Verifique permissão e obtenha localização
         checkLocationPermission {
            viewModel.loadCurrentLocation()
        }

        // Eventos
        registerViewListeners()

        //Observer
        observer()

        //carrega dados já existentes
        viewModel.loadRide()

        return  binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.rideMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.rideMap.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observer(){
        viewModel.invalidField.observe(viewLifecycleOwner){
            loadInvalidsFields(it)
        }

        viewModel.loadRide.observe(viewLifecycleOwner){
            if (it != null) {
                loadRide(it)
            }
        }
        viewModel.currentLocation.observe(viewLifecycleOwner) { geoPoint ->
            geoPoint?.let {
                updateMapLocation(binding.rideMap, it)
            }
        }

    }




    override fun onClick(v: View) {
        when (v.id){
            R.id.ride_buttonStartRide ->{
                handleRegisterRide()
            }
        }
    }


    private fun registerViewListeners() {
        binding.rideButtonStartRide.setOnClickListener(this)
    }

    private fun handleRegisterRide(){


        var ride = RideModel(
            binding.rideEditClient.text.toString(),
            binding.rideEditFrom.text.toString(),
            binding.rideEditTo.text.toString(),
            null
        )

        if (viewModel.validateRide(ride)){

                directions = RideFragmentDirections.actionRideFragmentToRideOptionsFragment()
                 navController.navigate(directions!!)


        }else{
            DialogUtil.showErrorDialog(myContext,"Houve Falha em alguns campos!")
        }
    }


    private fun loadInvalidsFields(hash : HashMap<String,String>){

        clearError()

        for(key in hash.keys){
            when (key){
                ShopperContants.INPUT.RIDE.CUSTOMER_ID -> {
                    binding.rideTextClient.error = hash[key]
                }
                ShopperContants.INPUT.RIDE.ORIGIN -> {
                    binding.rideTextFrom.error = hash[key]
                }
                ShopperContants.INPUT.RIDE.DESTINATION -> {
                    binding.rideTextTo.error = hash[key]
                }
            }
        }
    }

    private fun clearError(){
        binding.rideTextClient.error = null
        binding.rideEditFrom.error = null
        binding.rideEditTo.error = null
    }


    override fun onPermissionGranted() {
        // Quando a permissão é concedida, carregar a localização e atualizar o mapa
        viewModel.loadCurrentLocation()
        viewModel.currentLocation.observe(viewLifecycleOwner) { geoPoint ->
            geoPoint?.let {
                updateMapLocation(binding.rideMap, it)
            }
        }
    }



    fun loadRide(ride : RideModel){

        binding.rideEditClient.setText(ride.customer_id)
        binding.rideEditFrom.setText(ride.origin)
        binding.rideEditTo.setText(ride.destination)
    }

}