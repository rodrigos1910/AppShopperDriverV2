package com.example.appshopperdriver.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.appshopperdriver.data.entities.DriverDTO
import com.example.appshopperdriver.model.orders.FilterRideModel
import com.example.appshopperdriver.model.ride.RideModel
import com.example.appshopperdriver.network.response.RideHistoryResponse
import com.example.appshopperdriver.service.repository.RideRepository
import com.example.appshopperdriver.singleton.SingletonFilterRide
import com.example.appshopperdriver.ui.orders.OrdersViewModel
import com.example.appshopperdriver.ui.ride.RideViewModel
import com.example.appshopperdriver.util.ApiListener
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], manifest = Config.NONE)
public class OrdersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val application = ApplicationProvider.getApplicationContext<Application>()
    private val repositoryMock = mock(RideRepository::class.java)

    private lateinit var viewModel: OrdersViewModel

    @Before
    fun setup() {
        viewModel = OrdersViewModel(application)
    }


    @Test
    fun validateFilter_retorna_false_quando_campos_estao_vazios() {
        val filter = FilterRideModel(customer_id = "", driver_id = "")
        val result = viewModel.validateFilter(filter)
        assertFalse(result)
        assertTrue(viewModel.invalidField.value?.isNotEmpty() == true)
    }

    @Test
    fun validateFilter_retorna_true_quando_campos_estao_preenchidos() {
        val filter = FilterRideModel(customer_id = "C123", driver_id = "D789")
        val result = viewModel.validateFilter(filter)
        assertTrue(result)
        assertTrue(viewModel.invalidField.value?.isEmpty() == true)


        val savedFilter = SingletonFilterRide.getInstance()
        assertEquals("C123", savedFilter.customer_id)
        assertEquals("D789", savedFilter.driver_id)
    }

    @Test
    fun getDriverIdByName_retorna_codigo_do_driver_se_existir() {

        val driverList = listOf(
            DriverDTO(1,"001", "Motorista A"),
            DriverDTO(2,"002", "Motorista B")
        )
        viewModel.setDriversForTest(driverList)

        val codigo = viewModel.getDriverIdByName("Motorista B")
        assertEquals("002", codigo)
    }

    @Test
    fun getDriverIdByName_retorna_null_se_nao_encontrar_nome() {
        val driverList = listOf(
            DriverDTO(1,"001", "Motorista A")
        )
        viewModel.setDriversForTest(driverList)

        val codigo = viewModel.getDriverIdByName("Motorista Inexistente")
        assertNull(codigo)
    }

}