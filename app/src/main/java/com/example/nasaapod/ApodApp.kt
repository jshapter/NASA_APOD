package com.example.nasaapod

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val toDate = LocalDate.parse(apod.date)
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    val date = toDate.format(dateFormatter)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = { Text(
                    text = "NASA APOD",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                ) },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "download",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = {

                    item {
                        Column(modifier = Modifier
                            .padding(top = 12.dp)
                        ) {
                            Text(
                                text = apod.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = date,
                                fontSize = 14.sp,
                                modifier = Modifier.alpha(0.8f)
                            )
                        }
                    }

                    item {
                        Column() {
                            Box (
                                modifier = Modifier
                                    .clipToBounds()
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    model = apod.hdurl,
                                    contentDescription = "astronomy photograph of the day"
                                )
                            }
                            Row(
                                modifier = Modifier.padding(top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Copyright, contentDescription = "copyright",
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = apod.copyright,
                                    fontSize = 14.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    }
                    
                    item {
                        var expandedState by remember { mutableStateOf(true) }
                        val rotationState by animateFloatAsState(
                            targetValue = if (expandedState) 180f else 0f
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(
                                    animationSpec = tween(
                                        easing = LinearOutSlowInEasing
                                    )
                                )
                                .padding(bottom = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        expandedState = !expandedState
                                    }
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Description",
                                    fontWeight = FontWeight.Bold,
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