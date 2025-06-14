@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.mobileappsproject.employee.profileedit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileappsproject.data.entities.Employee

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel,
    onSave: (Employee) -> Unit,
    onBack: () -> Unit, // 👈 New callback
    originalEmployee: Employee
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val department by viewModel.department.collectAsState()
    val designation by viewModel.designation.collectAsState()

    var isSaved by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    viewModel.onNameChange(it)
                    isSaved = false
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    viewModel.onEmailChange(it)
                    isSaved = false
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = department,
                onValueChange = {
                    viewModel.onDepartmentChange(it)
                    isSaved = false
                },
                label = { Text("Department") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = designation,
                onValueChange = {
                    viewModel.onDesignationChange(it)
                    isSaved = false
                },
                label = { Text("Designation") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.saveProfile { updatedEmployee ->
                        onSave(updatedEmployee)
                        isSaved = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            if (isSaved) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "✔️ Profile saved successfully!",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
