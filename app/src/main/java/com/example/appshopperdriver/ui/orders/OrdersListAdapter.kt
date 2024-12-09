package com.example.appshopperdriver.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appshopperdriver.databinding.RowRidesBinding
import com.example.appshopperdriver.model.ride.DriverModel
import com.example.appshopperdriver.model.ride.RideModel

class OrdersListAdapter : RecyclerView.Adapter<OrdersListViewHolder>()  {

    private var listItens: List<RideModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowRidesBinding.inflate(inflater, parent, false)
        return OrdersListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: OrdersListViewHolder, position: Int) {
        holder.bindData(listItens[position])
    }

    override fun getItemCount(): Int {
        return listItens.count()
    }


    fun update(list: List<RideModel>){
        listItens = list
        notifyDataSetChanged()
    }

}