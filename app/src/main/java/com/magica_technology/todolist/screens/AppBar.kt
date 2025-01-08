package com.magica_technology.todolist.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    title:String,
    onBackClick:()->Unit ={}
){


    TopAppBar(
        title = { Text(text = title) },
        navigationIcon={Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .clickable { onBackClick() }
        )},

        backgroundColor = MaterialTheme.colors.surface
    )
    
}

@Preview()
@Composable
fun AppBarPreview(){
    AppBar(title = "ToDo List")
}