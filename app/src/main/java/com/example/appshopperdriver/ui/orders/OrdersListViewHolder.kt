package com.example.appshopperdriver.ui.orders

import androidx.recyclerview.widget.RecyclerView
import com.example.appshopperdriver.databinding.RowRidesBinding
import com.example.appshopperdriver.model.ride.RideModel
import com.example.appshopperdriver.util.DateUtil
import com.example.appshopperdriver.util.DoubleUtil

class OrdersListViewHolder(private val itemBinding: RowRidesBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    var list = ArrayList<RideModel>()

    fun bindData(item: RideModel) {
        val driver = item.driver

        itemBinding.textRideName.text = driver?.name ?: ""
        itemBinding.textRidedata.text = DateUtil.formatDateToBrazilian(driver?.date) ?: ""
        itemBinding.textRideOrigin.text = "Origem: ${item.origin}"
        itemBinding.textRideDestination.text =  "Destino: ${item.destination}"
        itemBinding.textRideDistance.text = "Distância: ${ DoubleUtil.formatToTwoDecimal(driver?.distance) ?: 0.0} km"
        itemBinding.textRideTime.text = "Duração: ${driver?.duration ?: "Indisponível"}"
        itemBinding.textRideValue.text = "R$: ${DoubleUtil.formatToTwoDecimal(driver?.value) ?: 0.0}"

    }
}