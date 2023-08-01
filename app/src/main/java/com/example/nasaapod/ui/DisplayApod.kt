package com.example.nasaapod.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nasaapod.ui.theme.Nasalization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayApod(
    title: String,
    date: String,
    url: String,
    copyright: String,
    description: String
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = "APOD",
                        fontSize = 28.sp,
                        fontFamily = Nasalization,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
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
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = title,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                IconButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier
                                        .size(28.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Download,
                                        contentDescription = "download"
                                    )
                                }
                            }
                            Text(
                                text = date,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .alpha(0.8f)
                                    .padding(bottom = 4.dp)
                            )
                        }
                    }

                    item {
                        Column {
                            Box (
                                modifier = Modifier
                                    .clipToBounds()
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .border(4.dp, MaterialTheme.colorScheme.onBackground),
                                    model = url,
                                    contentDescription = "astronomy photograph of the day"
                                )
                            }
                            if (copyright != "null") {
                                Row(
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Copyright, contentDescription = "copyright",
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = copyright,
                                        fontSize = 14.sp,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp)
                        ) {
                            Text(
                                text = "Description",
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 16.dp)
                                    .alpha(0.8f),
                                text = description,
                                fontSize = 14.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            )
        }
    )
}