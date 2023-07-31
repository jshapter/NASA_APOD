package com.example.nasaapod

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nasaapod.data.Apod
import com.example.nasaapod.ui.theme.ApodUiState
import com.example.nasaapod.ui.theme.ApodViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ApodApp() {
    val apodViewModel: ApodViewModel = viewModel()

    when (val apodUiState = apodViewModel.apodUiState) {
        is ApodUiState.Loading -> Text(text = "Loading")
        is ApodUiState.Success -> DisplayApod(apod = apodUiState.apod)
        is ApodUiState.Error -> Text(text = "Error")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayApod(apod: Apod) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = { Text(
                    text = "NASA APOD",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                ) }
            )
        },
        content = { innerPadding ->

            val toDate = LocalDate.parse(apod.date)
            val dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
            val date = toDate.format(dateFormatter)

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = {
                    item {
                        Text(
                            text = apod.title,
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize
                        )
                    }
                    item {
                        Text(text = date)
                    }
                    item {
                        AsyncImage(
                            model = apod.hdurl,
                            contentDescription = "astronomy photograph of the day")
                    }
                    item {
                        var expandedState by remember { mutableStateOf(false) }
                        val rotationState by animateFloatAsState(
                            targetValue = if (expandedState) 180f else 0f
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(
                                    animationSpec = tween(
                                        durationMillis = 800,
                                        easing = LinearOutSlowInEasing
                                    )
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable { expandedState = !expandedState }
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Description"
                                )
                                IconButton(
                                    modifier = Modifier.rotate(rotationState),
                                    onClick = { expandedState = !expandedState }
                                ) {
                                    Icon(
                                        Icons.Default.ArrowDropDown, contentDescription = "expand"
                                    )
                                }
                            }
                            if (expandedState) {
                                Text(text = apod.explanation)
                            }
                        }
                    }
                }
            )
        }
    )
}