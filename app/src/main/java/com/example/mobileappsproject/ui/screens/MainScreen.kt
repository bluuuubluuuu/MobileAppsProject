package com.example.mobileappsproject.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text("Employee Leave Management", style = MaterialTheme.typography.titleLarge)

        Button(onClick = { navController.navigate("employee") }, modifier = Modifier.fillMaxWidth()) {
            Text("Employee Management")
        }

        Button(onClick = { navController.navigate("leaveType") }, modifier = Modifier.fillMaxWidth()) {
            Text("Leave Type Management")
        }

        Button(onClick = { navController.navigate("leaveApplication") }, modifier = Modifier.fillMaxWidth()) {
            Text("Leave Application")
        }

        Button(onClick = { navController.navigate("leaveApproval") }, modifier = Modifier.fillMaxWidth()) {
            Text("Leave Approval Workflow")
        }
    }
}