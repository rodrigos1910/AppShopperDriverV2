package com.example.appshopperdriver.ui.orders

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshopperdriver.R
import com.example.appshopperdriver.databinding.FragmentOrdersListBinding
import com.example.appshopperdriver.util.DialogUtil


class OrdersListFragment : Fragment() {

    protected lateinit var myContext: Context

    private lateinit var viewModel: OrdersViewModel

    private var _binding: FragmentOrdersListBinding? = null
    private val binding get() = _binding!!


    private lateinit var dialogLoading: AlertDialog


    private val adapter = OrdersListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        _binding = FragmentOrdersListBinding.inflate(inflater, container, false)

        myContext =  requireContext()

        binding.recyclerRides.layoutManager = LinearLayoutManager(context)
        binding.recyclerRides.adapter = adapter

        //Observer
        observer()

        //carrega dados
        dialogLoading = DialogUtil.showLoadingDialog(myContext,myContext.getString(R.string.dialog_loading_history))
        viewModel.requestRides()

        return  binding.root
    }

    private fun observer(){

        viewModel.listRides.observe(viewLifecycleOwner){

            adapter.update(it)

            if (::dialogLoading.isInitialized) {
                dialogLoading.dismiss()
            }
        }

        viewModel.loadRides.observe(viewLifecycleOwner){
            if (::dialogLoading.isInitialized) {
                dialogLoading.dismiss()
            }
            if (!it.status()) {
                DialogUtil.showErrorDialog(myContext,it.message())
            }

        }
    }

}