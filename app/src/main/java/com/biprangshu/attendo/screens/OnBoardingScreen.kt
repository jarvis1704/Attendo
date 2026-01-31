package com.biprangshu.attendo.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.biprangshu.attendo.ui.theme.Appfonts.robotoFlexTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector,
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val pages = remember {
        listOf(
            OnboardingPage(
                title = "Welcome to Attendo",
                description = "Take control of your academic attendance with intelligent tracking and insights.",
                icon = Icons.Default.School
            ),
            OnboardingPage(
                title = "Add & Manage Subjects",
                description = "Easily add subjects with current attendance data and keep everything organized.",
                icon = Icons.Default.Add
            ),
            OnboardingPage(
                title = "Smart Swipe Actions",
                description = "Swipe to mark present or absent. It's that simple and intuitive!",
                icon = Icons.Default.Swipe
            ),
            OnboardingPage(
                title = "Calendar & Analytics",
                description = "View your attendance history in a beautiful calendar and get insights to stay on track.",
                icon = Icons.Default.CalendarToday
            ),
            OnboardingPage(
                title = "Stay Above 75%",
                description = "Get smart suggestions on how many classes to attend or skip to maintain your goal.",
                icon = Icons.Default.TrendingUp
            )
        )
    }

    val colour1 = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    val colour2 = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {


            Box(modifier = Modifier.weight(0.3f)) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    AnimatedContent(
                        targetState = pages[pagerState.currentPage].title,
                        transitionSpec = {
                            if (targetState != initialState) {
                                slideInVertically { height -> height } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                fadeIn() togetherWith fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        },
                        label = "Title Animation"
                    ) { title ->
                        Text(
                            text = title,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = robotoFlexTopBar
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedContent(
                        targetState = pages[pagerState.currentPage].description,
                        transitionSpec = {
                            if (targetState != initialState) {
                                slideInVertically { height -> height / 2 } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height / 2 } + fadeOut()
                            } else {
                                fadeIn() togetherWith fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        },
                        label = "Desc Animation"
                    ) { desc ->
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }


            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
            ) { pageIndex ->
                val pageOffset = (
                        (pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction
                        ).absoluteValue

                val page = pages[pageIndex]

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            val scale = lerp(1f, 0.85f, pageOffset.coerceIn(0f, 1f))
                            scaleX = scale
                            scaleY = scale
                            alpha = lerp(1f, 0.5f, pageOffset.coerceIn(0f, 1f))
                        },
                    contentAlignment = Alignment.Center
                ) {

                    when (page.icon) {
                        Icons.Default.Swipe -> SwipeAttendanceDemoCard()
                        Icons.Default.TrendingUp -> AttendanceProgressDemo()
                        else -> {
                            Box(
                                modifier = Modifier
                                    .size(180.dp)
                                    .drawBehind {
                                        drawCircle(
                                            color = colour1,
                                            radius = size.width / 2 * 0.9f
                                        )
                                        drawCircle(
                                            color = colour2,
                                            radius = size.width / 2 * 0.7f
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = page.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }


            Row(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(pages.size) { iteration ->
                        val isSelected = pagerState.currentPage == iteration

                        val width by animateDpAsState(
                            targetValue = if (isSelected) 32.dp else 12.dp,
                            animationSpec = spring(stiffness = Spring.StiffnessMedium),
                            label = "Indicator Width"
                        )
                        val color by animateColorAsState(
                            targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                            animationSpec = spring(stiffness = Spring.StiffnessMedium),
                            label = "Indicator Color"
                        )

                        Box(
                            modifier = Modifier
                                .height(12.dp)
                                .width(width)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }

                val isLastPage = pagerState.currentPage == pages.size - 1

                Button(
                    onClick = {
                        if (isLastPage) {
                            onGetStarted()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1,
                                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                                )
                            }
                        }
                    },
                    modifier = Modifier.height(62.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    AnimatedVisibility(visible = !isLastPage) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next"
                        )
                    }
                    AnimatedVisibility(visible = isLastPage) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Get Started",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SwipeAttendanceDemoCard(modifier: Modifier = Modifier) {
    var swipeState by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1500)
            swipeState = 1
            delay(1500)
            swipeState = 0
            delay(1000)
            swipeState = -1
            delay(1500)
            swipeState = 0
        }
    }

    val offset by animateDpAsState(
        targetValue = when (swipeState) {
            1 -> 40.dp
            -1 -> (-40).dp
            else -> 0.dp
        },
        animationSpec = tween(500, easing = EaseInOutCubic), label = "swipeOffset"
    )
    val color by animateColorAsState(
        targetValue = when (swipeState) {
            1 -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
            -1 -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        },
        animationSpec = tween(500), label = "swipeColor"
    )

    Card(
        modifier = modifier
            .offset(x = offset)
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Data Structures",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = when (swipeState) {
                        1 -> "Marked Absent ✗"
                        -1 -> "Marked Present ✓"
                        else -> "Swipe to update"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "85%",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun AttendanceProgressDemo(modifier: Modifier = Modifier) {
    var progress by remember { mutableFloatStateOf(0.75f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            progress = 0.65f
            delay(2000)
            progress = 0.85f
            delay(2000)
            progress = 0.75f
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = EaseInOutCubic),
        label = "progressAnimation"
    )
    val color by animateColorAsState(
        targetValue = when {
            animatedProgress >= 0.75f -> MaterialTheme.colorScheme.primary
            animatedProgress > 0.65f -> MaterialTheme.colorScheme.tertiary
            else -> MaterialTheme.colorScheme.error
        },
        animationSpec = tween(1000), label = "progressColor"
    )

    Card(
        modifier = modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "${(animatedProgress * 100).toInt()}% Attendance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(CircleShape),
                color = color,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Text(
                text = when {
                    animatedProgress >= 0.75f -> "You're on track! ✨"
                    else -> "Needs improvement 📈"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}