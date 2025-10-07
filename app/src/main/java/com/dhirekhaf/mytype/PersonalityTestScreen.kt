// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestScreen.kt

package com.dhirekhaf.mytype

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dhirekhaf.mytype.data.UserDataRepository

// Konstanta dan gaya
private val primaryColor = Color(0xff735283)
private val backgroundColorStart = Color(0xFFF8F5FA)
private val backgroundColorEnd = Color(0xFFEEEAF2)
private val cardBackgroundColor = Color.White
private val progressTrackColor = Color.Black.copy(alpha = 0.1f)
private val selectedOptionColor = primaryColor.copy(alpha = 0.1f)
private val unselectedOptionColor = Color.Transparent
private val selectedBorderColor = primaryColor
private val unselectedBorderColor = Color.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalityTestScreen(
    navController: NavController,
    onTestComplete: () -> Unit
) {
    val viewModel: PersonalityTestViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val meViewModel: MeViewModel = viewModel(factory = MeViewModelFactory(UserDataRepository(LocalContext.current)))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(backgroundColorStart, backgroundColorEnd)))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Personality Test") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                // --- [PERBAIKAN LOGIKA WHEN] ---
                when {
                    uiState.isLoading -> CircularProgressIndicator(color = primaryColor)

                    uiState.error != null -> Text(
                        uiState.error!!,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )

                    uiState.testResult != null -> {
                        LaunchedEffect(uiState.testResult) {
                            val scores = viewModel.getFinalScores()
                            if (scores != null) {
                                meViewModel.saveMbtiResult(uiState.testResult!!, scores)
                            }
                        }
                        TestResultView(result = uiState.testResult!!, onFinish = onTestComplete)
                    }

                    // Kondisi eksplisit untuk menampilkan pertanyaan
                    !uiState.isLoading && uiState.currentQuestionGroup.isNotEmpty() -> {
                        QuestionGroupView(
                            uiState = uiState,
                            onAnswerSelected = viewModel::answerQuestion,
                            onNextClicked = viewModel::nextGroup
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionGroupView(
    uiState: PersonalityTestUiState,
    onAnswerSelected: (questionId: String, optionId: String) -> Unit,
    onNextClicked: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = uiState.progress,
        animationSpec = tween(durationMillis = 500),
        label = "ProgressAnimation"
    )
    val allQuestionsAnswered = uiState.currentQuestionGroup.all { uiState.userAnswers.containsKey(it.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Progress Bar
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = primaryColor,
                trackColor = progressTrackColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = uiState.progressText, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daftar Pertanyaan
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(uiState.currentQuestionGroup, key = { it.id }) { question ->
                QuestionCard(
                    question = question,
                    selectedOptionId = uiState.userAnswers[question.id],
                    onOptionSelected = { optionId ->
                        onAnswerSelected(question.id, optionId)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNextClicked,
            enabled = allQuestionsAnswered,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
            )
        ) {
            Text("Next", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestScreen.kt

// ... (kode lain di atasnya tidak perlu diubah)

// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestScreen.kt

// ... (kode lain di atasnya tidak perlu diubah)

// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestScreen.kt

// ... (kode lain di atasnya tidak perlu diubah)

@Composable
fun QuestionCard(
    question: Question,
    selectedOptionId: String?,
    onOptionSelected: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // --- [PERUBAIKAN UTAMA DI SINI] ---
            // 1. Bungkus Soal dan Ikon dalam satu Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically // Pastikan vertikal sejajar
            ) {
                // Teks Pertanyaan Utama
                Text(
                    text = question.question_text,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f) // Isi ruang yang tersedia
                )

                // Tombol Bantuan '?' di sebelah kanan
                if (!question.explanation.isNullOrBlank()) {
                    Spacer(modifier = Modifier.width(8.dp)) // Beri sedikit jarak
                    IconButton(onClick = { isExpanded = !isExpanded }, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Show explanation",
                            tint = Color.Gray
                        )
                    }
                }
            }
            // --- AKHIR PERUBAIKAN ---

            // 2. Dropdown Penjelasan tetap di bawah Row
            if (!question.explanation.isNullOrBlank()) {
                AnimatedVisibility(visible = isExpanded) {
                    Text(
                        text = question.explanation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp) // Jarak dari soal
                            .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Opsi Jawaban (tidak berubah)
            question.options.forEach { option ->
                val isSelected = selectedOptionId == option.option_id
                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) selectedOptionColor else unselectedOptionColor,
                    label = "OptionBackground"
                )
                val borderColor by animateColorAsState(
                    targetValue = if (isSelected) selectedBorderColor else unselectedBorderColor,
                    label = "OptionBorder"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .background(backgroundColor, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onOptionSelected(option.option_id) }
                        .padding(16.dp)
                ) {
                    Text(
                        text = option.option_text,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

// ... (Sisa kode di file ini seperti TestResultView, QuestionGroupView, dll. tidak perlu diubah)


// ... (Sisa kode di file ini seperti TestResultView, QuestionGroupView, dll. tidak perlu diubah)


// ... (Sisa kode di file ini tidak perlu diubah)


// TestResultView tidak berubah
@Composable
fun TestResultView(result: String, onFinish: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.CheckCircle, "Test Completed", tint = Color(0xFF3B7A57), modifier = Modifier.size(80.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text("Congratulations!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = primaryColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Your personality type is:", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(result, style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.ExtraBold, color = primaryColor)
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onFinish,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View My Profile", modifier = Modifier.padding(vertical = 8.dp), fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
