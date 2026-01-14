package com.maha.kotlin01.presentation.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maha.kotlin01.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    userId: String,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val user = state.users.find { it.id == userId }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "User Details",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (user != null) {
                Spacer(modifier = Modifier.height(24.dp))

                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(
                            width = 4.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Date of Birth: ${user.dateOfBirth}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "User ID: ${user.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "User not found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
