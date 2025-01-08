package com.magica_technology.todolist.screens
import Helper
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.magica_technology.todolist.R
import com.magica_technology.todolist.room.Task
import com.magica_technology.todolist.utils.TaskGraph
import com.magica_technology.todolist.viewModel.TaskViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun AddTask(
    navController: NavHostController,
    id:Long=0L,
    viewModel: TaskViewModel
){
    //Coroutine..............
    val scope = rememberCoroutineScope()
    var alphaError by remember {
        mutableStateOf(0.001f)
    }
    var errorText by remember {
        mutableStateOf("")
    }

    //User I/O Variable......
    if (id != 0L){
        val task = viewModel.getTaskById(id).collectAsState(initial = Task(0L, "", "","",0L))
        viewModel.onTaskTitleChanged(task.value.title)
        viewModel.onTaskDescriptionChanged(task.value.description)
        val (date,time) =viewModel.getDateTimeFromTimestamp(task.value.dueTime)
        viewModel.onTaskDateChanged(date)
        viewModel.onTaskTimeChanged(time)
        if (task.value.category.isNotEmpty()){
        viewModel.onTaskCategoryChanged(task.value.category)
        }
    }else{
        viewModel.onTaskTitleChanged("")
        viewModel.onTaskDescriptionChanged("")
        viewModel.onTaskCategoryChanged("Select Category")
        viewModel.onTaskDateChanged("")
        viewModel.onTaskTimeChanged("")

    }
    //Drop Down Manu
    var isExpanded by remember { mutableStateOf(false) }

    // TopBar Variable...................
    val screenTitle=if (id!=0L) "Update Task" else "Add Task"
    // Material Theme Variable...................
    val colorScheme = MaterialTheme.colorScheme
    val typography= MaterialTheme.typography

    Scaffold (
        topBar = { AppBar(title = screenTitle, onBackClick = {
            navController.popBackStack()
        }) },
    ){
        Card (modifier = Modifier.wrapContentSize()
            .fillMaxHeight(.6f)
            .padding(top = it.calculateTopPadding()+3.dp, start = 8.dp, end = 8.dp, bottom = 30.dp),
            elevation = 12.dp,
            shape = RoundedCornerShape(5.dp),
            backgroundColor = MaterialTheme.colorScheme.surface.copy(.7f).compositeOver(Color.White)
        ){
            Column (modifier = Modifier.padding(8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally){

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value =viewModel.taskTitleState,
                    textStyle = typography.bodyMedium,
                    onValueChange = {viewModel.onTaskTitleChanged(it)},
                    label = {Text(text = "Title", style = typography.bodySmall)},
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                )
                OutlinedTextField(
                    value =viewModel.taskDescriptionState,
                    onValueChange = {
                        viewModel.onTaskDescriptionChanged(it)
                    },
                    textStyle = typography.bodyMedium,
                    label = {Text(text = "Description",style = typography.bodySmall)},
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text("Title, Description & Date is Mandatory field* to add Task",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                    color = colorScheme.error.copy(alphaError),
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp)

                Spacer(modifier = Modifier.height(2.dp))

                Row (modifier = Modifier.fillMaxWidth()
                    .padding(start = 8.dp,end=8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Row {
                        Button (onClick = {
                            DatePickerDialog(
                                navController.context,
                                { _, year, month, dayOfMonth ->
                                    viewModel.onTaskDateChanged("$year-${month + 1}-$dayOfMonth")
                                },
                                Calendar.getInstance().get(Calendar.YEAR),
                                Calendar.getInstance().get(Calendar.MONTH),
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                            ).show()

                        }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface.copy(.01f)),
                            modifier = Modifier.wrapContentSize()) {
                            Row {
                                Icon(
                                    painter = painterResource(R.drawable.ic_calendar),
                                    tint =colorScheme.onSurface,
                                    contentDescription = ""
                                )
                                Text(text = viewModel.taskDateState, style = typography.bodySmall,
                                    textAlign = TextAlign.Center,
                                    color = colorScheme.onSurface,
                                    modifier = Modifier.padding(4.dp))
                            }
                        }

                    }
                    Row {
                        Button (onClick = {
                            TimePickerDialog(
                                navController.context,
                                { _, hourOfDay, minute ->
                                    viewModel.onTaskTimeChanged("$hourOfDay:$minute")
                                },
                                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                Calendar.getInstance().get(Calendar.MINUTE),
                                false
                            ).show()
                        }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface.copy(.01f)),
                            modifier = Modifier.wrapContentSize()) {
                            Row { Icon(
                                painter = painterResource(R.drawable.ic_access),
                                contentDescription = "",
                                tint =colorScheme.onSurface,
                            )
                                Text(text = viewModel.taskTimeState, style = typography.bodySmall,
                                    textAlign = TextAlign.Center,
                                    color = colorScheme.onSurface,
                                    modifier = Modifier.padding(4.dp))
                            }
                        }

                    }

                }
                Row (modifier = Modifier.weight(5f)){
                    Box(modifier = Modifier.weight(3f)) {
                        Button(onClick = {isExpanded=true},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.primaryContainer, // Background color of the button
                                contentColor = colorScheme.onSurface   // Text/Icon color
                            ),
                            shape = RoundedCornerShape(8.dp), // Small roundness
                            modifier = Modifier.padding(8.dp)) {
                            Text(text = viewModel.selectedCategory)
                            androidx.compose.material3.Icon(Icons.Default.ArrowDropDown, contentDescription ="Drop Down" )
                        }
                        DropdownMenu(expanded = isExpanded,
                            onDismissRequest = {
                                isExpanded=false
                                viewModel.onTaskCategoryChanged("Select Category")
                        }) {
                            DropdownMenuItem(
                                text = { Text(text = "Finance") },
                                onClick = {
                                    viewModel.onTaskCategoryChanged("Finance")
                                    isExpanded = false
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Health") },
                                onClick = {
                                    viewModel.onTaskCategoryChanged("Health")
                                    isExpanded = false
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Shopping") },
                                onClick = {
                                    viewModel.onTaskCategoryChanged("Shopping")
                                    isExpanded = false
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Fitness") },
                                onClick = {
                                    viewModel.onTaskCategoryChanged("Fitness")
                                    isExpanded = false
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Education") },
                                onClick = {
                                    viewModel.onTaskCategoryChanged("Education")
                                    isExpanded = false
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Personal") },
                                onClick = {
                                    viewModel.onTaskCategoryChanged("Personal")
                                    isExpanded = false
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Hobbies") },
                                onClick = {
                                    viewModel.onTaskCategoryChanged("Hobbies")
                                    isExpanded = false
                                })
                        }
                    }

                    Button(
                        onClick = {
                            if(viewModel.taskTitleState.isNotEmpty() &&
                                viewModel.taskDescriptionState.isNotEmpty() &&
                                viewModel.taskDateState.isNotEmpty()){
                                val timestamp = viewModel.getTimestamp()
                                if (timestamp > System.currentTimeMillis())

                                alphaError=0.01f
                                errorText=""

                                if(id != 0L ){
                                    viewModel.updateTask(
                                        Task(
                                            id = id,
                                            title = viewModel.taskTitleState.trim(),
                                            description = viewModel.taskDescriptionState.trim(),
                                            dueTime = timestamp,
                                            category = if(viewModel.selectedCategory == "Select Category"){
                                                ""
                                            }else{
                                                viewModel.selectedCategory.trim()
                                            }

                                        )
                                    )

                                    if (timestamp > System.currentTimeMillis()) {
                                        //Todo add Alarm
                                        Helper.scheduleAlarm(context = navController.context,timestamp)
                                    }

                                    scope.launch {
                                        navController.navigateUp()
                                    }
                                }else{
                                    //  Add Task
                                    viewModel.addNewTask(
                                        Task(
                                            title = viewModel.taskTitleState.trim(),
                                            description = viewModel.taskDescriptionState.trim(),
                                            dueTime =timestamp ,
                                            category = if(viewModel.selectedCategory == "Select Category"){
                                                ""
                                            }else{
                                                viewModel.selectedCategory.trim()
                                            }
                                        )
                                    )
                                    if (timestamp > System.currentTimeMillis()) {
                                        //Todo add Alarm
                                        Helper.scheduleAlarm(context = navController.context,timestamp)
                                    }
                                    scope.launch {
                                        navController.navigateUp()
                                    }
                                }
                            }else{
                                //TODO add waring not add required field
                                alphaError=0.8f
                                errorText="Title, Description & Date is Mandatory field* to add Task"
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primaryContainer, // Background color of the button
                            contentColor = colorScheme.onSurface   // Text/Icon color
                        ),
                        shape = RoundedCornerShape(8.dp), // Small roundness
                        modifier = Modifier.padding(8.dp)
                            .weight(2f)
                    ) {
                        Text(screenTitle)
                    }
                }

            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddTaskPreview(){
    //AddTask(0L)
}