package com.example.appshopperdriver.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.appshopperdriver.model.ride.RideModel
import com.example.appshopperdriver.network.response.ConfirmResponse
import com.example.appshopperdriver.network.response.EstimateResponse
import com.example.appshopperdriver.network.response.RouteResponse
import com.example.appshopperdriver.service.repository.RideRepository
import com.example.appshopperdriver.singleton.SingletonRide
import com.example.appshopperdriver.ui.ride.RideViewModel
import com.example.appshopperdriver.util.ApiListener
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.example.appshopperdriver.network.response.Routes

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28],manifest=Config.NONE) // escolhe uma versão do Android simulada
class RideViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val application = ApplicationProvider.getApplicationContext<Application>()
    private val repositoryMock = mock(RideRepository::class.java)

    private lateinit var viewModel: RideViewModel

    @Before
    fun setup() {
        viewModel = RideViewModel(application)
    }

    @Test
    fun validateRide_deve_retornar_falso_quando_campos_estao_vazios() {
        val ride = RideModel(
            customer_id = "",
            origin = "",
            destination = "",
            driver = null
        )

        val result = viewModel.validateRide(ride)
        assertFalse(result)
    }

    @Test
    fun `validateRide_deve_retornar_true_quando_campos_estao_preenchidos`() {
        val ride = RideModel(
            customer_id = "12345",
            origin = "Rua A",
            destination = "Rua B",
            driver = null
        )

        val result = viewModel.validateRide(ride)
        assertTrue(result)
    }


}
