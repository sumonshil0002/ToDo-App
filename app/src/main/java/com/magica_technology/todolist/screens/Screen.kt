package com.magica_technology.todolist.screens

sealed class Screen(val route:String,title:String) {


    sealed class MainScreen(val aRoute:String, val aTitle:String):Screen(aRoute,aTitle){
        object AddUpdateScreen:MainScreen("add_screen","Add Task")
        object ManuScreen:MainScreen("main_screen","Add Task")
    }
    sealed class TabScreen(val mRoute:String, val mTitle:String):Screen(mRoute,mTitle){
        object HomeScreen:TabScreen("home_screen","Home")
        object CategoryScreen:TabScreen("category_screen","Category")
        object OverViewScreen:TabScreen("overview_screen","Overview")
    }

}