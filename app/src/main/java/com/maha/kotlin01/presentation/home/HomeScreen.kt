package com.maha.kotlin01.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maha.kotlin01.presentation.components.UserListItem
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onItemClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Menu Item 1",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(vertical = 12.dp)
                    )

                    Text(
                        text = "Menu Item 2",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(vertical = 12.dp)
                    )

                    Text(
                        text = "Menu Item 3",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(vertical = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    ) {
                        Text("Close Menu")
                    }
                }
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "HEADER",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier.clickable {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Count: ${state.users.size}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Row {
                            Button(
                                onClick = { viewModel.removeUser() },
                                enabled = state.users.isNotEmpty() && !state.isLoading
                            ) {
                                Text("-")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { viewModel.addUser() },
                                enabled = !state.isLoading
                            ) {
                                if (state.isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                } else {
                                    Text("+")
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                state.error?.let { error ->
                    item {
                        Text(
                            text = "Error: $error",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                if (state.users.isNotEmpty()) {
                    items(count = state.users.size) { index ->
                        val user = state.users[index]
                        UserListItem(
                            name = user.fullName,
                            description = "Born: ${user.dateOfBirth}",
                            avatarUrl = user.avatarUrl,
                            onClick = { onItemClick(user.id) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
