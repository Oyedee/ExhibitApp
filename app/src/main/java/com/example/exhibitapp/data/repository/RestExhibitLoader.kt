package com.example.exhibitapp.data.repository

import com.example.exhibitapp.data.models.Exhibit
import com.example.exhibitapp.data.network.ExhibitsLoader
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call
import javax.inject.Inject

@ViewModelScoped
class RestExhibitLoader @Inject constructor(
    private val api: ExhibitsLoader
) {
    fun getExhibitList(): Exhibit {
        return api.getExhibitList()
    }
}