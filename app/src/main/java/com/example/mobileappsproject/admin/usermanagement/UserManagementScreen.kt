@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.mobileappsproject.admin.usermanagement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobileappsproject.data.entities.Employee

@Composable
fun UserManagementScreen(viewModel: UserManagementViewModel, navController: NavHostController) {
    val employees by viewModel.employees.collectAsState()
    var selectedEmployee by remember { mutableStateOf<Employee?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Management") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(employees) { employee ->
                EmployeeItem(
                    employee = employee,
                    onEdit = { selectedEmployee = it },
                    onDelete = { viewModel.deleteEmployee(employee) }
                )
            }
        }

        // Edit Dialog
        selectedEmployee?.let { employee ->
            EditEmployeeDialog(
                employee = employee,
                onDismiss = { selectedEmployee = null },
                onSave = { updatedEmployee ->
                    viewModel.updateEmployee(updatedEmployee)
                    selectedEmployee = null
                }
            )
        }
    }
}

@Composable
fun EmployeeItem(employee: Employee, onEdit: (Employee) -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Name: ${employee.name}")
            Text("Email: ${employee.email}")
            Text("Role: ${employee.role}")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { onEdit(employee) }) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

@Composable
fun EditEmployeeDialog(
    employee: Employee,
    onDismiss: () -> Unit,
    onSave: (Employee) -> Unit
) {
    var name by remember { mutableStateOf(employee.name) }
    var email by remember { mutableStateOf(employee.email) }
    var role by remember { mutableStateOf(employee.role) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onSave(employee.copy(name = name, email = email, role = role))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Edit User") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
