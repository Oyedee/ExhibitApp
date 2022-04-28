package com.example.exhibitapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.exhibitapp.data.models.Exhibit
import com.example.exhibitapp.data.models.ExhibitItem
import com.example.exhibitapp.data.repository.RestExhibitLoader
import com.example.exhibitapp.di.AppModule
import com.example.exhibitapp.ui.viewmodel.SharedViewModel
import com.example.exhibitapp.util.RequestState
import junit.framework.Assert.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import java.net.HttpURLConnection
import kotlin.coroutines.CoroutineContext


@RunWith(MockitoJUnitRunner::class)
class SharedViewModelTest : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Unconfined

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel
    private lateinit var apiLoader: RestExhibitLoader
    private lateinit var retrofit: Retrofit
    private lateinit var actualResponse: RequestState<Exhibit>

    @Mock
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        retrofit = AppModule.provideRetrofit()
        apiLoader = RestExhibitLoader(AppModule.provideExhibitApi(retrofit))
        viewModel = SharedViewModel(apiLoader)

        mockWebServer = MockWebServer()
        mockWebServer.start()

    }

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `observe exhibit list`() {
        viewModel.allExhibits

        val result = arrayListOf<RequestState<Exhibit>>()
        val job = launch {
            viewModel.allExhibits.toList(result)
        }

        assertFalse(viewModel.allExhibits.value is RequestState.Success)
        job.cancel()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}