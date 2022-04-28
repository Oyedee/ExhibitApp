package com.example.exhibitapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exhibitapp.data.models.Exhibit
import com.example.exhibitapp.data.repository.RestExhibitLoader
import com.example.exhibitapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val restExhibitLoader: RestExhibitLoader
) : ViewModel() {

    private val _allExhibits = MutableStateFlow<RequestState<Exhibit>>(RequestState.Idle)

    val allExhibits: StateFlow<RequestState<Exhibit>> get() = _allExhibits

    fun getAllExhibits() {
        _allExhibits.value = RequestState.Loading
        try {
            viewModelScope.launch {
                val response = restExhibitLoader.getExhibitList()
                _allExhibits.value = RequestState.Success(response)
            }
        } catch (e: Exception) {
            _allExhibits.value = RequestState.Error(e)
        }
    }
}