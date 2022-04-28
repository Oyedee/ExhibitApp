package com.example.exhibitapp.data.models

import androidx.room.PrimaryKey

data class ExhibitItem(
    val images: List<String>,
    val title: String
)