package com.magica_technology.todolist.screens

import CategoryScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.magica_technology.todolist.R
import com.magica_technology.todolist.utils.TabObj
import com.magica_technology.todolist.viewModel.TaskViewModel



@Composable
fun MainView(){
    val navController:NavHostController = rememberNavController()
    var selectedIndex by remember { mutableStateOf(0) }
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route


    Scaffold(
        topBar = {
            if (currentScreen == null || !currentScreen.contains(Screen.MainScreen.AddUpdateScreen.route)) {
                // Default top bar for most screens
                AppTopBar(
                    tabs = TabObj.TabBarList,
                    onTabSelected = {index->
                        selectedIndex =index
                        navController.navigate(TabObj.TabBarList[index].route){
                            popUpTo(Screen.TabScreen.HomeScreen.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    selectedIndex = selectedIndex
                )
            }
        },
        floatingActionButton = {
            if(currentScreen=="home_screen"){
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.MainScreen.AddUpdateScreen.route+"/0L")
                },
                    modifier = Modifier.padding(20.dp),
                    backgroundColor = colorResource(id = R.color.black),
                    contentColor = colorResource(id = R.color.white),
                    elevation = FloatingActionButtonDefaults.elevation(20.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        NavigationMain(paddingValues = it,navController=navController,)
    }

}


@Composable
fun NavigationMain(
    paddingValues: PaddingValues,
    viewModel: TaskViewModel = viewModel(),
    navController: NavHostController
){
    NavHost(
        navController= navController,
        startDestination = Screen.TabScreen.HomeScreen.route,
        modifier = Modifier.padding(paddingValues)
    ){
        composable(Screen.TabScreen.HomeScreen.route){
            HomeScreen(viewModel,navController)
        }
        composable(Screen.TabScreen.CategoryScreen.route) {
            CategoryScreen(viewModel,navController)
        }
        composable(Screen.TabScreen.OverViewScreen.route) {
            OverviewScreen(viewModel,navController)
        }
        composable(Screen.MainScreen.AddUpdateScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ){entry->
            val id = if(entry.arguments != null)  entry.arguments!!.getLong("id") else 0L
            AddTask(id = id, viewModel = viewModel , navController = navController)
        }


    }
}


