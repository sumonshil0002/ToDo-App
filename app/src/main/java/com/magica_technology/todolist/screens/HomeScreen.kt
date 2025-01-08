package com.magica_technology.todolist.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.magica_technology.todolist.R
import com.magica_technology.todolist.room.Task
import com.magica_technology.todolist.utils.Category
import com.magica_technology.todolist.utils.DummyCategory
import com.magica_technology.todolist.viewModel.TaskViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    navController: NavHostController,
){
    Scaffold (
        backgroundColor = MaterialTheme.colorScheme.surface,
    ){

        val taskList = viewModel.getAllPendingTask.collectAsState(initial = emptyList())
        LazyColumn(modifier = Modifier
            .padding(it)
            .fillMaxSize())
        {
            //Category Header
            item {
                Text("Category >> ",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }
            // LazyRow for categories
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(DummyCategory.categoryList) { menuItem ->
                        MenuItemView(menuItem)
                    }
                }
            }
            //Pending Task Header
            item {
                Text("All Pending Task >> ",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }

//Scroll View...............................................
            when{
                taskList.value.isEmpty()->{
                    //Todo loading............
                }
                else ->{
                items(taskList.value, key={task-> task.id}){item->

                    val dismissState = rememberDismissState(
                        confirmStateChange = { dismissValue ->
                            when (dismissValue) {
                                DismissValue.DismissedToEnd -> {
                                    // Mark as complete logic
                                    viewModel.updateTask(
                                        Task(
                                            id = item.id,
                                            title = item.title,
                                            description = item.description,
                                            dueTime = item.dueTime,
                                            isCompleted = 1
                                        )
                                    )
                                    true
                                }
                                DismissValue.DismissedToStart -> {
                                    // Delete logic
                                    viewModel.deleteTask(item)
                                    true
                                }
                                else -> false
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {

                            val color by animateColorAsState(
                                targetValue = when (dismissState.dismissDirection) {
                                    DismissDirection.EndToStart -> Color.Red.copy(0.5f)
                                    DismissDirection.StartToEnd -> Color.Green.copy(0.5f)
                                    else -> MaterialTheme.colorScheme.surface
                                }
                            )

                            val alignment = if(dismissState.dismissDirection ==DismissDirection.EndToStart)
                                Alignment.CenterEnd else Alignment.CenterStart
                            Box(
                                Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ){
                                Icon(imageVector = if(dismissState.dismissDirection
                                    == DismissDirection.EndToStart) Icons.Default.Delete else Icons.Default.CheckCircle,
                                    contentDescription = "Delete Icon",
                                    tint = Color.White)
                            }

                        },
                        directions = setOf(DismissDirection.EndToStart,DismissDirection.StartToEnd),
                        dismissThresholds = { FractionalThreshold(.7f) },
                        dismissContent = {

                            val (date,time) =viewModel.getDateTimeFromTimestamp(item.dueTime)
                            TaskItem(item, date = date, time = time){
                                navController.navigate(Screen.MainScreen.AddUpdateScreen.route+"/${item.id}")
                            }
                        }
                    )
                }}
            }
        }
    }
}
@Composable
fun TaskItem(task: Task,date:String,time:String,onClick: () -> Unit){
    val colorScheme =MaterialTheme.colorScheme
    val typography=MaterialTheme.typography
    val alphaAlarm =if (time=="0:0") 0.01f else .8f

    Card (modifier = Modifier.fillMaxWidth()
        .padding(top = 6.dp, start = 8.dp, end = 8.dp, bottom = 2.dp)
        .clickable { onClick() },
        elevation = 12.dp,
        shape = RoundedCornerShape(5.dp),
        backgroundColor = MaterialTheme.colorScheme.surface.copy(.7f).compositeOver(Color.White),
    ){
        Column (modifier = Modifier.padding(16.dp)
            .fillMaxWidth()){
            Text(text = task.title.uppercase(), style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 32.dp),
                color = colorScheme.outline.copy(alpha = .5f).compositeOver(colorScheme.surface))
            Text(text=task.description, style = typography.titleSmall,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp),
                textAlign = TextAlign.Justify,
                color = colorScheme.onSurface)

            Spacer(modifier = Modifier.padding(vertical = 6.dp, horizontal = 32.dp)
                .height(2.dp))

            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Row { Icon(
                    painter = painterResource(R.drawable.ic_calendar),
                    tint =colorScheme.onSurface,
                    contentDescription = ""
                )
                    Text(text = date, style = typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = colorScheme.onSurface.copy(alpha = .8f).compositeOver(colorScheme.surface),
                        modifier = Modifier.padding(4.dp))
                }
                Row { Icon(
                    painter = painterResource(R.drawable.ic_access),
                    contentDescription = "",
                    tint =colorScheme.onSurface.copy(alpha = alphaAlarm),
                    )
                }
            }
        }

    }

}

@Composable
fun MenuItemView(category: Category) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = androidx.compose.foundation.shape.CircleShape
            )
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(category.image),
            contentDescription = "",
            modifier = Modifier.alpha(.6f),
            contentScale = ContentScale.Crop)
        Text(
            text = category.title,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


