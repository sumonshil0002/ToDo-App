package com.magica_technology.todolist.utils

import com.magica_technology.todolist.R
import com.magica_technology.todolist.screens.Screen

data class Category(
    val title:String,
    val image: Int
)
object DummyCategory{
    val categoryList:List<Category> = listOf(
        Category("Finance", R.drawable.finance),
        Category("Health", R.drawable.health),
        Category("Shopping", R.drawable.shopping),
        Category("Fitness", R.drawable.jogging),
        Category("Education", R.drawable.reading),
        Category("Personal", R.drawable.personal),
        Category("Hobbies", R.drawable.hobbie)
    )

}

data class MainAppTab(
    val title:String,
    val icon: Int,
    val route:String
)

object TabObj{
    val TabBarList = listOf(
        MainAppTab(title = "Main", icon = R.drawable.ic_home_filled_24,Screen.TabScreen.HomeScreen.route),
        MainAppTab(title = "Category", icon = R.drawable.ic_category,Screen.TabScreen.CategoryScreen.route),
        MainAppTab(title = "Overview", icon = R.drawable.ic_overview,Screen.TabScreen.OverViewScreen.route)
    )
}

