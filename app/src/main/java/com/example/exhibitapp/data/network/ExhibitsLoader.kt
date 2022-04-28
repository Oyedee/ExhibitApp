package com.example.exhibitapp.data.network

import com.example.exhibitapp.data.models.Exhibit
import retrofit2.Call
import retrofit2.http.GET

interface ExhibitsLoader {

    @GET("Reyst/exhibit_db/list")
    fun getExhibitList(): Exhibit


    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/"
    }
}