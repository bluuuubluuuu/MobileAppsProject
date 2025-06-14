package com.example.mobileappsproject.employee.leaveapplication

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileappsproject.data.entities.LeaveApplication
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun LeaveApplicationScreen(
    viewModel: LeaveApplicationViewModel,
    navController: NavController? = null
) {
    val applications by viewModel.leaveApplications.collectAsState()
    val context = LocalContext.current

    var employeeEmail by remember { mutableStateOf("") }
    val employeeIdState = viewModel.employeeId.collectAsState()
    val employeeID = employeeIdState.value
    var leaveTypeID by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Pending") }

    // Debounced fetch when email changes
    LaunchedEffect(employeeEmail) {
        if (employeeEmail.isNotBlank()) {
            delay(500)
            viewModel.fetchEmployeeIdByEmail(employeeEmail)
        }
    }

    // Date pickers
    val calendar = Calendar.getInstance()

    val startDatePicker = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            startDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endDatePicker = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            endDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val isFormValid = employeeID.isNotBlank() &&
            leaveTypeID.isNotBlank() &&
            startDate.isNotBlank() &&
            endDate.isNotBlank() &&
            reason.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                navController?.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Leave Application Form", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Form Fields
        OutlinedTextField(
            value = employeeEmail,
            onValueChange = { employeeEmail = it },
            label = { Text("Employee Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = employeeID,
            onValueChange = {},
            label = { Text("Employee ID") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = leaveTypeID,
            onValueChange = { leaveTypeID = it },
            label = { Text("Leave Type ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Start Date") },
            trailingIcon = {
                IconButton(onClick = { startDatePicker.show() }) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Pick Start Date")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = endDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("End Date") },
            trailingIcon = {
                IconButton(onClick = { endDatePicker.show() }) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Pick End Date")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = reason,
            onValueChange = { reason = it },
            label = { Text("Reason") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.insertLeaveApplication(
                    LeaveApplication(
                        leaveID = (0..1000000).random(),
                        employeeID = employeeID.toIntOrNull() ?: 0,
                        leaveTypeID = leaveTypeID.toIntOrNull() ?: 0,
                        startDate = startDate,
                        endDate = endDate,
                        reason = reason,
                        status = status
                    )
                )
                // Reset form fields (not employeeID, which depends on email)
                leaveTypeID = ""
                startDate = ""
                endDate = ""
                reason = ""
                navController?.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isFormValid
        ) {
            Text("Submit Leave")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Submitted Applications", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(applications) { app ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Employee ID: ${app.employeeID}")
                        Text("Leave Type ID: ${app.leaveTypeID}")
                        Text("From: ${app.startDate} To: ${app.endDate}")
                        Text("Reason: ${app.reason}")
                        Text("Status: ${app.status}")
                        Button(
                            onClick = { viewModel.deleteLeaveApplication(app) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Delete", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                }
            }
        }
    }
}
