package com.magica_technology.todolist.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.magica_technology.todolist.utils.MainAppTab

@Composable
fun AppTopBar(
    tabs: List<MainAppTab>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    ) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                tabs.forEachIndexed { index, tab ->
                    Row(
                        modifier = Modifier
                            .clickable { onTabSelected(index) }
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = if (selectedIndex == index) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
                        )

                        // Text (Only visible for the selected tab)
                        if (selectedIndex == index) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = tab.title,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.wrapContentWidth()
                            )
                        }
                    }
                }
            }

    }
}
