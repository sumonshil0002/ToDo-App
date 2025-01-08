package com.magica_technology.todolist.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.magica_technology.todolist.viewModel.TaskViewModel

@Composable
fun OverviewScreen(viewModel: TaskViewModel, navController: NavHostController) {

    Scaffold(modifier = Modifier
        .fillMaxSize()) {

        Column(modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "this is OverViewPage"
            )

        }

    }

}