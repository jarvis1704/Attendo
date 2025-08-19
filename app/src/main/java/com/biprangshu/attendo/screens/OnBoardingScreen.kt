package com.biprangshu.attendo.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.attendo.ui.theme.Appfonts.robotoFlexTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector,
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit
) {

    val hapticFeedback = LocalHapticFeedback.current
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

    val pagerState = rememberPagerState(pageCount = { pages.size })


    val primaryColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f + (pagerState.currentPage * 0.1f)),
        animationSpec = tween(durationMillis = 1000), label = "primaryColor"
    )
    val secondaryColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f + (pagerState.currentPage * 0.1f)),
        animationSpec = tween(durationMillis = 1000), label = "secondaryColor"
    )
    val tertiaryColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f + (pagerState.currentPage * 0.1f)),
        animationSpec = tween(durationMillis = 1000), label = "tertiaryColor"
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(primaryColor, secondaryColor, tertiaryColor)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                ) {
                    if (pagerState.currentPage < pages.size - 1) {
                        TextButton(
                            onClick = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                onGetStarted()
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text("Skip", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }


                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 24.dp)
                ) { page ->
                    OnboardingPageContent(
                        page = pages[page],
                        isCurrentPage = page == pagerState.currentPage
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PageIndicator(
                        pageCount = pages.size,
                        currentPage = pagerState.currentPage,
                    )


                    ExtendedFloatingActionButton(
                        onClick = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                            if (pagerState.currentPage == pages.size - 1) {
                                onGetStarted()
                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        AnimatedContent(
                            targetState = pagerState.currentPage == pages.size - 1,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                                        scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) togetherWith
                                        fadeOut(animationSpec = tween(90)) + scaleOut(targetScale = 0.92f, animationSpec = tween(90))
                            }, label = "fabContent"
                        ) { isLastPage ->
                            if (isLastPage) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Get Started")
                                    Spacer(Modifier.width(8.dp))
                                    Icon(Icons.Default.Check, contentDescription = "Get Started")
                                }
                            } else {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Next")
                                    Spacer(Modifier.width(8.dp))
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    isCurrentPage: Boolean,
    modifier: Modifier = Modifier
) {

    val contentAlpha by animateFloatAsState(
        targetValue = if (isCurrentPage) 1f else 0f,
        animationSpec = tween(durationMillis = 500, delayMillis = 300),
        label = "contentAlpha"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isCurrentPage) 1f else 0.8f,
        animationSpec = tween(durationMillis = 800, easing = EaseInOutCubic),
        label = "iconScale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer { alpha = contentAlpha },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(180.dp)
                .graphicsLayer { scaleX = iconScale; scaleY = iconScale }
                .drawBehind {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.1f),
                        radius = size.width / 2 * 0.9f
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.2f),
                        radius = size.width / 2 * 0.7f
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = page.icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(56.dp))


        AnimatedContent(
            targetState = page,
            transitionSpec = {
                (slideInHorizontally { width -> width } + fadeIn()).togetherWith(slideOutHorizontally { width -> -width } + fadeOut())
            }, label = "textContent"
        ) { targetPage ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = targetPage.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = robotoFlexTopBar,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = targetPage.description,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        AnimatedVisibility(
            visible = isCurrentPage,
            enter = fadeIn(animationSpec = tween(delayMillis = 600)),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.height(120.dp), contentAlignment = Alignment.Center) {
                when (page.icon) {
                    Icons.Default.Swipe -> SwipeAttendanceDemoCard()
                    Icons.Default.TrendingUp -> AttendanceProgressDemo()
                }
            }
        }
    }
}


@Composable
private fun SwipeAttendanceDemoCard(modifier: Modifier = Modifier) {
    var swipeState by remember { mutableIntStateOf(0) } // 0: neutral, 1: right (absent), -1: left (present)


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


@Composable
private fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { page ->
            val isSelected = page == currentPage
            val width by animateFloatAsState(
                targetValue = if (isSelected) 32f else 8f,
                animationSpec = tween(durationMillis = 400, easing = EaseInOutCubic),
                label = "indicatorWidth"
            )

            Box(
                modifier = Modifier
                    .size(width = width.dp, height = 8.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
                    )
            )
        }
    }
}
