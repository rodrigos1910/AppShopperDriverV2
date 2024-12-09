package com.example.appshopperdriver.ui.ride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appshopperdriver.databinding.RowDriversBinding
import com.example.appshopperdriver.model.ride.DriverModel

class RideDriverAdapter: RecyclerView.Adapter<RideDriverViewHolder>()  {

    private var listItens: List<DriverModel> = arrayListOf()
    private lateinit var listener: RideDriverListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideDriverViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowDriversBinding.inflate(inflater, parent, false)
        return RideDriverViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: RideDriverViewHolder, position: Int) {
        holder.bindData(listItens[position])
    }

    override fun getItemCount(): Int {
        return listItens.count()
    }

    fun attachListener(rideDriverListener: RideDriverListener) {
        listener = rideDriverListener
    }

    fun getPositionForDriver(driver: DriverModel): Int {
        return listItens.indexOfFirst { it.name == driver.name }
    }

    fun update(list: List<DriverModel>){
        listItens = list
        notifyDataSetChanged()
    }

}