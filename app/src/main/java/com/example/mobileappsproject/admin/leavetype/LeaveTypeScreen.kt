package com.example.mobileappsproject.admin.leavetype

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileappsproject.data.entities.LeaveType

@Composable
fun LeaveTypeScreen(viewModel: LeaveTypeViewModel, navController: NavController? = null) {
    val leaveTypes by viewModel.leaveTypes.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var maxDays by remember { mutableStateOf("") }
    var requiresApproval by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editingId by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Push content down

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                navController?.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = if (isEditing) "Edit Leave Type" else "Add Leave Type",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Leave Type Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = maxDays, onValueChange = { maxDays = it }, label = { Text("Max Days") }, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Checkbox(checked = requiresApproval, onCheckedChange = { requiresApproval = it })
            Text("Requires Approval", modifier = Modifier.padding(start = 8.dp))
        }

        Button(
            onClick = {
                val id = if (isEditing) editingId else (leaveTypes.maxOfOrNull { it.leaveTypeID } ?: 0) + 1
                val leaveType = LeaveType(id, name, description, maxDays.toIntOrNull() ?: 0, requiresApproval)
                if (isEditing) {
                    viewModel.updateLeaveType(leaveType)
                } else {
                    viewModel.insertLeaveType(leaveType)
                }
                name = ""
                description = ""
                maxDays = ""
                requiresApproval = false
                isEditing = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Update Leave Type" else "Add Leave Type")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Leave Types", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(leaveTypes) { leaveType ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Name: ${leaveType.leaveTypeName}")
                        Text("Description: ${leaveType.description}")
                        Text("Max Days: ${leaveType.maxDays}")
                        Text("Requires Approval: ${if (leaveType.requiredApproval) "Yes" else "No"}")

                        Row {
                            TextButton(onClick = {
                                isEditing = true
                                editingId = leaveType.leaveTypeID
                                name = leaveType.leaveTypeName
                                description = leaveType.description
                                maxDays = leaveType.maxDays.toString()
                                requiresApproval = leaveType.requiredApproval
                            }) {
                                Text("Edit")
                            }
                            TextButton(onClick = { viewModel.deleteLeaveType(leaveType) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}