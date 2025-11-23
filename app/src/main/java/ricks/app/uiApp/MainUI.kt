package ricks.app.uiApp

// Imports ,,, Toast
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ricks.app.gemLogic.GeminiLogic
import ricks.app.gemLogic.api
import ricks.app.gemLogic.modelText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiHomeScreen() {
    val purple = Color(0xFF7C3AED)
    val darkBackground = Color(0xFF0A0A0A)
    val cardBackground = Color(0xFF1A1A1A)
    val lightGray = Color(0xFF6B7280)

    // State for input, response, loading, and the last sent query
    var query by remember { mutableStateOf("") }
    var response by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var lastSentQuery by remember { mutableStateOf<String?>(null) } // New state for the query that was sent
    val coroutineScope = rememberCoroutineScope()

    // State for Settings Dialog
    var showSettingsDialog by remember { mutableStateOf(false) }
    var settingsApiKey by remember { mutableStateOf(api) } // Initialize with current API key
    var settingsModelName by remember { mutableStateOf(modelText) } // Initialize with current model name

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ricks Ai abomination", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkBackground
                )
            )
        },
        containerColor = darkBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Bot Avatar
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(60.dp))
                        .background(purple.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Bot Avatar",
                        modifier = Modifier.size(80.dp),
                        tint = purple
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Main Title
                Text(
                    text = "whatchu want?",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "The boxes below are useless ," +
                            " they're present for aesthetic reasons",
                    color = lightGray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Suggestions Grid
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SuggestionCard(
                            title = "placeholder ",
                            icon = Icons.Filled.Lightbulb,
                            onClick = { /* Handle click */ },
                            modifier = Modifier.weight(1f)
                        )
                        SuggestionCard(
                            title = "Bla..bla",
                            icon = Icons.Filled.Tv, 
                            onClick = { /* Handle click */ },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SuggestionCard(
                            title = "yooooo",
                            icon = Icons.Filled.SignalCellularAlt,
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                        SuggestionCard(
                            title = "LoloL",
                            icon = Icons.Filled.PieChart,
                            onClick = { /* Handle click */ },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                if (response != null && lastSentQuery != null) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Your Lame query: ${lastSentQuery!!}", // Use the new state variable
                        color = lightGray,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                    Card(
                        colors = CardDefaults.cardColors(containerColor = cardBackground),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "$modelText's response",
                                color = purple,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = response!!,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                } else if (isLoading) {
                    Spacer(modifier = Modifier.height(40.dp))
                    CircularProgressIndicator(color = purple)
                }
            }

            // Input Field (Always at the bottom)
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Don't ask dumb shi.", color = lightGray) },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (query.isNotBlank() && !isLoading) {
                                isLoading = true
                                val currentQuery = query // Capture current query before clearing
                                lastSentQuery = currentQuery // Store the query that is being sent
                                coroutineScope.launch {
                                    response = null // Clear old response on new query
                                    val result = GeminiLogic.generateWithGoogleSearch(currentQuery)
                                    response = result
                                    isLoading = false
                                }
                                query = "" // Clear the input field after sending
                            }
                        },
                        enabled = query.isNotBlank() && !isLoading
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = if (query.isNotBlank() && !isLoading) purple else lightGray.copy(alpha = 0.5f)
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = purple,
                    unfocusedBorderColor = lightGray,
                    focusedContainerColor = cardBackground,
                    unfocusedContainerColor = cardBackground,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = lightGray,
                    unfocusedPlaceholderColor = lightGray,
                    focusedLeadingIconColor = lightGray,
                    unfocusedLeadingIconColor = lightGray,
                    focusedTrailingIconColor = purple,
                    unfocusedTrailingIconColor = purple,
                    focusedLabelColor = lightGray,
                    unfocusedLabelColor = lightGray,
                    cursorColor = purple
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp, max = 120.dp) // Allows for multi-line input
                    .padding(bottom = 16.dp) // Add padding at the bottom
            )
        }
    }

    // Settings Dialog
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Settings", color = Color.White) },
            text = {
                Column {
                    OutlinedTextField(
                        value = settingsApiKey,
                        onValueChange = { settingsApiKey = it },
                        label = { Text("API Key", color = lightGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = purple,
                            unfocusedBorderColor = lightGray,
                            focusedContainerColor = cardBackground,
                            unfocusedContainerColor = cardBackground,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = lightGray,
                            unfocusedLabelColor = lightGray,
                            cursorColor = purple
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = settingsModelName,
                        onValueChange = { settingsModelName = it },
                        label = { Text("Model Name", color = lightGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = purple,
                            unfocusedBorderColor = lightGray,
                            focusedContainerColor = cardBackground,
                            unfocusedContainerColor = cardBackground,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = lightGray,
                            unfocusedLabelColor = lightGray,
                            cursorColor = purple
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        GeminiLogic.updateSettings(settingsApiKey, settingsModelName)
                        showSettingsDialog = false
                    }
                ) {
                    Text("Update", color = purple)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSettingsDialog = false }
                ) {
                    Text("Cancel", color = lightGray)
                }
            },
            containerColor = darkBackground,
            textContentColor = Color.White,
            titleContentColor = Color.White
        )
    }
}

@Composable
fun SuggestionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // Added modifier parameter
) {
    val purple = Color(0xFF7C3AED)
    val cardBackground = Color(0xFF1A1A1A)
    val context = LocalContext.current // Get the current context

    Card(
        onClick = {
            Toast.makeText(context, "Just type the question nigga, don't be lazy", Toast.LENGTH_SHORT).show()
            onClick() // Call the original onClick function as well
        },
        modifier = modifier.height(100.dp), // Applied the passed modifier and height
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = purple
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun AiHomeScreenPreview() {
    AiHomeScreen()
}