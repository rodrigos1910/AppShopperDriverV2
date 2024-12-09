package com.example.appshopperdriver.ui.orders

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.appshopperdriver.R
import com.example.appshopperdriver.data.entities.DriverDTO
import com.example.appshopperdriver.databinding.FragmentOrdersBinding
import com.example.appshopperdriver.model.orders.FilterRideModel
import com.example.appshopperdriver.model.ride.RideModel
import com.example.appshopperdriver.service.constants.ShopperContants
import com.example.appshopperdriver.ui.ride.RideFragmentDirections
import com.example.appshopperdriver.util.DialogUtil


class OrdersFragment : Fragment(), View.OnClickListener {

    protected lateinit var myContext: Context

    private lateinit var viewModel: OrdersViewModel

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!


    private val navController: NavController by lazy {
        findNavController()
    }
    private var directions: NavDirections? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         viewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        myContext =  requireContext()

        // Eventos
        registerViewListeners()

        //Observer
        observer()

        // Carrega motoristas do banco de dados
        viewModel.loadDrivers()


        return  binding.root
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.orders_buttonFilter ->{
                handleFilter()
            }
        }
    }

    private fun observer(){
        viewModel.invalidField.observe(viewLifecycleOwner){
            loadInvalidsFields(it)
        }

        viewModel.drivers.observe(viewLifecycleOwner) { drivers ->
            populateDriverSpinner(drivers)
        }
    }

    private fun registerViewListeners(){
        binding.ordersButtonFilter.setOnClickListener(this)
        binding.ordersSpinnerDriverContent.setOnClickListener {
            binding.ordersSpinnerDriverContent.showDropDown()
        }
    }

    private fun loadInvalidsFields(hash : HashMap<String,String>){
        clearError()

        for(key in hash.keys){
            when (key){
                ShopperContants.INPUT.ORDER.CUSTOMER_ID -> {
                    binding.ordersTextClient.error = hash[key]
                }
                ShopperContants.INPUT.ORDER.DRIVER_ID -> {
                    binding.ordersSpinnerDriver.error = hash[key]
                }
            }
        }
    }

    private fun clearError(){
        binding.ordersSpinnerDriver.error = null
        binding.ordersTextClient.error = null
    }


    private fun populateDriverSpinner(drivers: List<DriverDTO>) {
        val driverNames = drivers.map { it.name }
        val adapter = ArrayAdapter(
            myContext,
            android.R.layout.simple_list_item_1,
            driverNames
        )
        binding.ordersSpinnerDriverContent.setAdapter(adapter)
    }


    fun handleFilter(){

        val selectedName = binding.ordersSpinnerDriverContent.text.toString()
        val selectedDriverId = viewModel.getDriverIdByName(selectedName) ?: ""

        var filter = FilterRideModel(

            binding.ordersEditClient.text.toString(),
            selectedDriverId
        )

        if (viewModel.validateFilter(filter)){

            directions = OrdersFragmentDirections.actionOrdersFragmentToOrdersListFragment()
            navController.navigate(directions!!)


        }else{
            DialogUtil.showErrorDialog(myContext,"Houve Falha em alguns campos!")
        }
    }

}