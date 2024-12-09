package com.example.appshopperdriver.ui.ride

import androidx.recyclerview.widget.RecyclerView
import com.example.appshopperdriver.databinding.RowDriversBinding
import com.example.appshopperdriver.model.ride.DriverModel
import com.example.appshopperdriver.singleton.SingletonRide
import com.example.appshopperdriver.util.DoubleUtil

class RideDriverViewHolder(private val itemBinding:  RowDriversBinding, val listener: RideDriverListener
) : RecyclerView.ViewHolder(itemBinding.root) {

    var listDrivers = ArrayList<DriverModel>()

    fun bindData(item: DriverModel) {

        itemBinding.textDriverName.text = item.name
        itemBinding.textDriverCar.text = item.vehicle
        itemBinding.textDriverValue.text = DoubleUtil.doubleToUiMoney(item.value)
        itemBinding.textDriverAssessment.text =  "${item.assessment.rating} ${item.assessment.comment}"
        itemBinding.textDriverDescription.text = item.description


        val ride = SingletonRide.getRideInstance()

        itemBinding.checkboxDriverSelect.isChecked = ride.driver?.name == item.name

        itemBinding.checkboxDriverSelect.setOnCheckedChangeListener { _, isChecked ->
            listener.onSelectItem(item, !isChecked)
        }
    }
}