package com.todapp.tod.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todapp.tod.data.AppStore
import com.todapp.tod.data.Task
import com.todapp.tod.ui.components.AnalogClock
import com.todapp.tod.ui.components.TodField
import com.todapp.tod.ui.theme.CircleMint
import com.todapp.tod.ui.theme.Ink
import com.todapp.tod.ui.theme.MintBg
import com.todapp.tod.ui.theme.Muted
import com.todapp.tod.ui.theme.Teal
import java.util.Calendar

@Composable
fun DashboardScreen(store: AppStore, onLogout: () -> Unit) {
    val user = store.getCurrentUser()
    var tasks by remember { mutableStateOf(store.tasks()) }
    var showAdd by remember { mutableStateOf(false) }
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MintBg)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(Teal)
        ) {
            Box(
                Modifier
                    .size(180.dp)
                    .offset(x = (-30).dp, y = (-50).dp)
                    .clip(CircleShape)
                    .background(CircleMint.copy(alpha = 0.35f))
            )
            Box(
                Modifier
                    .size(170.dp)
                    .align(Alignment.TopCenter)
                    .offset(x = 40.dp, y = (-40).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.18f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFFFFB347), Color(0xFFE56B6F), Color(0xFF2C3A39))
                            )
                        )
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("JG", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    "Welcome ${user?.name ?: "there"}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Sign out",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clickable(onClick = onLogout)
                        .padding(6.dp)
                )
            }
        }

        Column(Modifier.padding(horizontal = 22.dp)) {
            Text(
                greeting,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 12.dp, end = 8.dp),
                color = Muted,
                fontSize = 13.sp
            )
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AnalogClock()
            }
            Spacer(Modifier.height(8.dp))
            Text("Task list", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Ink)
            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Daily Task", fontWeight = FontWeight.SemiBold, color = Ink)
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { showAdd = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add task", tint = Teal)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    tasks.forEach { task ->
                        TaskRow(task) {
                            store.toggleTask(task.id)
                            tasks = store.tasks()
                        }
                    }
                }
            }
        }
    }

    if (showAdd) {
        var title by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAdd = false },
            title = { Text("Add daily task") },
            text = { TodField(title, { title = it }, "Task title") },
            confirmButton = {
                TextButton(onClick = {
                    store.addTask(title)
                    tasks = store.tasks()
                    showAdd = false
                }) { Text("Add", color = Teal) }
            },
            dismissButton = {
                TextButton(onClick = { showAdd = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun TaskRow(task: Task, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.done,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = Teal,
                uncheckedColor = Teal
            )
        )
        Spacer(Modifier.width(4.dp))
        Text(task.title, color = Ink, fontSize = 14.sp)
    }
}
