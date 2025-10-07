// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityTestScreen.kt

package com.dhirekhaf.mytype

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dhirekhaf.mytype.data.UserDataRepository

// --- [PERBAIKAN] Definisikan konstanta di sini agar bisa diakses oleh semua Composable dalam file ---
private const val QUESTIONS_PER_GROUP = 8
// --- AKHIR PERBAIKAN ---

// --- Definisi Gaya ---
private val primaryColor = Color(0xff735283)
private val backgroundColorStart = Color(0xFFF8F5FA)
private val backgroundColorEnd = Color(0xFFEEEAF2)
private val cardBackgroundColor = Color.White
private val progressTrackColor = Color.Black.copy(alpha = 0.1f)
private val titleStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
private val optionStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

private val agreeColor = Color(0xFF3B7A57)
private val disagreeColor = Color(0xFF8B898E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalityTestScreen(navController: NavController) {
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
                when {
                    uiState.isLoading -> CircularProgressIndicator(color = primaryColor)
                    uiState.error != null -> Text(
                        uiState.error!!,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                    uiState.testResult != null -> {
                        LaunchedEffect(uiState.testResult) {
                            val scores = viewModel.getFinalScores()
                            if (scores != null) {
                                meViewModel.saveMbtiResult(uiState.testResult!!, scores)
                            }
                        }
                        TestResultView(result = uiState.testResult!!) {
                            navController.navigate("me") {
                                popUpTo("beranda") { inclusive = true }
                            }
                        }
                    }
                    uiState.currentQuestionGroup.isNotEmpty() -> {
                        QuestionGroupView(
                            questions = uiState.currentQuestionGroup,
                            userAnswers = uiState.userAnswers,
                            progress = uiState.progress,
                            progressText = uiState.progressText,
                            currentGroupIndex = uiState.currentGroupIndex,
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
    questions: List<Question>,
    userAnswers: Map<String, QuestionOption>,
    progress: Float,
    progressText: String,
    currentGroupIndex: Int,
    onAnswerSelected: (questionId: String, selectedOption: QuestionOption) -> Unit,
    onNextClicked: () -> Unit
) {
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(600), label = "ProgressAnimation")
    val answeredIds = userAnswers.keys
    val currentQuestionIds = questions.map { it.id }
    val allAnswered = currentQuestionIds.all { it in answeredIds }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = primaryColor,
                trackColor = progressTrackColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = progressText, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            itemsIndexed(items = questions, key = { _, q -> "${currentGroupIndex}-${q.id}" }) { index, question ->
                // --- [PERBAIKAN] Gunakan konstanta QUESTIONS_PER_GROUP ---
                val questionNumber = (currentGroupIndex * QUESTIONS_PER_GROUP) + index + 1
                // --- AKHIR PERBAIKAN ---
                SingleQuestionCard(
                    questionNumber = questionNumber,
                    question = question,
                    selectedOptionId = userAnswers[question.id]?.option_id,
                    onOptionSelected = { selectedOption ->
                        onAnswerSelected(question.id, selectedOption)
                    }
                )
            }
        }

        Button(
            onClick = onNextClicked,
            enabled = allAnswered,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                disabledContainerColor = primaryColor.copy(alpha = 0.5f)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 6.dp)
        ) {
            Text("Lanjut", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun SingleQuestionCard(
    questionNumber: Int,
    question: Question,
    selectedOptionId: String?,
    onOptionSelected: (QuestionOption) -> Unit
) {
    var explanationVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$questionNumber. ${question.question_text}",
                    style = titleStyle,
                    lineHeight = 28.sp,
                    modifier = Modifier.weight(1f)
                )
                if (!question.explanation.isNullOrBlank()) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = "Lihat Penjelasan",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { explanationVisible = !explanationVisible }
                    )
                }
            }

            AnimatedVisibility(
                visible = explanationVisible,
                enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
            ) {
                Text(
                    text = question.explanation ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 8.dp)
                        .background(Color.Black.copy(alpha = 0.04f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                question.options.forEach { option ->
                    AnswerOptionRow(
                        option = option,
                        isSelected = (option.option_id == selectedOptionId),
                        onClick = { onOptionSelected(option) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AnswerOptionRow(
    option: QuestionOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val targetBackgroundColor = when {
        isSelected && option.option_text.equals("Setuju", ignoreCase = true) -> agreeColor
        isSelected && option.option_text.equals("Tidak Setuju", ignoreCase = true) -> disagreeColor
        else -> Color.White
    }

    val targetBorderColor = when {
        isSelected && option.option_text.equals("Setuju", ignoreCase = true) -> agreeColor
        isSelected && option.option_text.equals("Tidak Setuju", ignoreCase = true) -> disagreeColor
        else -> Color.LightGray
    }

    val targetTextColor = if (isSelected) Color.White else Color.Black

    val backgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        animationSpec = tween(durationMillis = 250),
        label = "answer_bg_color"
    )
    val borderColor by animateColorAsState(
        targetValue = targetBorderColor,
        animationSpec = tween(durationMillis = 250),
        label = "answer_border_color"
    )
    val textColor by animateColorAsState(
        targetValue = targetTextColor,
        animationSpec = tween(durationMillis = 250),
        label = "answer_text_color"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = option.option_text ?: "",
            style = optionStyle,
            color = textColor
        )
        Icon(
            imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = if (isSelected) "Terpilih" else "Tidak terpilih",
            tint = if (isSelected) Color.White else Color.Gray
        )
    }
}


@Composable
fun TestResultView(result: String, onFinish: () -> Unit) {
    val (groupName, groupColor) = when {
        result.contains('N') && result.contains('F') -> "The Diplomats" to agreeColor
        result.contains('N') && result.contains('T') -> "The Analysts" to primaryColor
        result.contains('S') && result.contains('J') -> "The Sentinels" to Color(0xFF4682B4)
        result.contains('S') && result.contains('P') -> "The Explorers" to Color(0xFFFFA500)
        else -> "Unknown Group" to Color.Gray
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Test Complete!", style = MaterialTheme.typography.headlineMedium, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your personality type is:", style = MaterialTheme.typography.titleMedium, color = Color.DarkGray)
        Text(text = result, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = groupColor)
        Spacer(modifier = Modifier.height(24.dp))
        Text("You belong to the group:", style = MaterialTheme.typography.titleMedium, color = Color.DarkGray)
        Text(text = groupName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = groupColor)
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 6.dp)
        ) {
            Text("See My Profile", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
