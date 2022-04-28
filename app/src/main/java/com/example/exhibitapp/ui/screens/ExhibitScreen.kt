package com.example.exhibitapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.exhibitapp.data.models.Exhibit
import com.example.exhibitapp.data.models.ExhibitItem
import com.example.exhibitapp.ui.viewmodel.SharedViewModel
import com.example.exhibitapp.util.RequestState
import retrofit2.Call

@Composable
fun ExhibitScreen(
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllExhibits()
    }

    val allExhibits by sharedViewModel.allExhibits.collectAsState()

    Scaffold(
        backgroundColor = Color.White,
        topBar = { TopAppBar(
            title = { Text("Exhibits", color = Color.White)},
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) },
        content = { ExhibitContent(allExhibits)},
    )
}

@Composable
fun ExhibitContent( exhibits: RequestState<Exhibit>) {
    if (exhibits is RequestState.Success) {
        DisplayExhibits(exhibits = exhibits.data)
    }
}

@Composable
fun DisplayExhibits(exhibits: List<ExhibitItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(
            items = exhibits,
        ) { exhibit ->
            DisplayExhibitItem(exhibitItem = exhibit)
            Divider(color = Color.Gray)
        }
    }
}

@Composable
fun DisplayExhibitItem(exhibitItem: ExhibitItem) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(all = 20.dp),
        shape = RectangleShape,
        color = Color.White,
        elevation = 15.dp,
    ) {

        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
        ) {
            //display title
           Text(
               text = exhibitItem.title,
               color = Color.Black,
               style = MaterialTheme.typography.h6,
               fontWeight = FontWeight.Bold
           )
            //display images in a row
            LazyRow(
                contentPadding = PaddingValues(all = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(
                    items = exhibitItem.images,
                ) { image ->
                    Image(
                        painter = rememberAsyncImagePainter(image),
                        contentDescription = "Phone images",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        }
    }
}
