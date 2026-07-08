package com.ssing.presentation.consumermatching.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.consumermatching.ConsumerMatchingContract
import com.ssing.presentation.consumermatching.ConsumerMatchingViewModel
import com.ssing.presentation.consumermatching.component.InstructorProfileContent
import com.ssing.presentation.consumermatching.component.MatchingResultBottomBar
import com.ssing.presentation.consumermatching.model.InstructorReview
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ConsumerMatchingResultRoute(
    popBackStack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToPayment: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConsumerMatchingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        if (effect is ConsumerMatchingContract.Effect.ResultEffect) {
            when (effect) {
                ConsumerMatchingContract.Effect.ResultEffect.NavigateToHome -> navigateToHome()
                ConsumerMatchingContract.Effect.ResultEffect.NavigateToPayment -> navigateToPayment()
                ConsumerMatchingContract.Effect.ResultEffect.PopBackStack -> popBackStack()
                is ConsumerMatchingContract.Effect.ResultEffect.ShowToast -> context.toast(effect.message)
            }
        }
    }

    BackHandler {
        viewModel.showCancelModal()
    }

    if (state.showCancelModal) {
        SsingModal(
            onDismissRequest = viewModel::closeCancelModel,
            title = "매칭을 취소할까요?",
            text = "홈으로 이동하면 현재 매칭된 강사와의 연결이 취소됩니다",
            primaryText = "계속 보기",
            onPrimary = viewModel::abortCancel,
            primaryStyle = SsingButtonStyle.GRAY,
            secondaryText = "취소 후 홈으로",
            onSecondary = viewModel::confirmCancel,
            secondaryStyle = SsingButtonStyle.RED,
        )
    }

    ConsumerMatchingResultScreen(
        state = state,
        onBack = viewModel::showCancelModal,
        onRematchingClick = viewModel::requsetRematching,
        onAcceptClick = viewModel::acceptMatching,
        onReviewClick = viewModel::navigateToReview,
        modifier = modifier,
    )
}

@Composable
internal fun ConsumerMatchingResultScreen(
    state: ConsumerMatchingContract.State,
    onBack: () -> Unit,
    onRematchingClick: () -> Unit,
    onAcceptClick: () -> Unit,
    onReviewClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SsingTopBar(
                title = "강사 프로필",
                onBack = onBack,
            )
        },
        bottomBar = {
            MatchingResultBottomBar(
                duration = state.lessonDuration,
                estimatedFee = state.estimatedFee,
                onRematchingClick = onRematchingClick,
                onAcceptClick = onAcceptClick,
            )
        }
    ) { innerPadding ->
        InstructorProfileContent(
            profileImageUrl = state.profileImageUrl,
            name = state.name,
            age = state.age,
            gender = state.gender,
            level = state.level,
            career = state.career,
            lessonCount = state.lessonCount,
            rating = state.rating,
            keywords = state.keywords,
            introduction = state.introduction,
            certifications = state.certifications,
            totalReviewCount = state.totalReviewCount,
            onReviewClick = onReviewClick,
            reviews = state.reviews,
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 16.dp),
            header = {
                SsingHeader(
                    title = "이 강사님이 매칭되었어요",
                    subText = "3분 이내에 수락하지 않으면 강습연결이 취소돼요",
                    modifier = Modifier.padding(bottom = 24.dp),
                )
            }
        )
    }
}

@Preview
@Composable
private fun ConsumerMatchingResultScreenPreview() {
    SSINGTheme {
        ConsumerMatchingResultScreen(
            state = ConsumerMatchingContract.State(
                profileImageUrl = "",
                name = "김수영",
                age = 32,
                gender = "여성",
                level = "세미프로",
                career = "8년차",
                lessonCount = "312회",
                rating = "4.9",
                keywords = persistentListOf("친절해요", "설명이 쉬워요", "시간을 잘 지켜요"),
                introduction = "초보자도 편하게 배울 수 있도록 눈높이에 맞춰 알려드려요. 안전을 최우선으로 생각하며 즐겁게 강습을 진행합니다.",
                certifications = persistentListOf("생활스포츠지도사 2급", "인명구조요원 자격증"),
                totalReviewCount = 128,
                reviews = persistentListOf(
                    InstructorReview(
                        profileImageUrl = "",
                        nickname = "수강생A",
                        gender = "남성",
                        age = 27,
                        rating = 5,
                        tags = persistentListOf("친절해요", "설명이 쉬워요"),
                        content = "처음이라 걱정했는데 차근차근 알려주셔서 금방 적응했어요!",
                        date = "2026.06.28",
                    ),
                    InstructorReview(
                        profileImageUrl = "",
                        nickname = "수강생B",
                        gender = "여성",
                        age = 24,
                        rating = 4,
                        tags = persistentListOf("시간을 잘 지켜요"),
                        content = "약속 시간 정확하게 지켜주시고 강습도 알찼습니다.",
                        date = "2026.06.15",
                    ),
                ),
                estimatedFee = 80000,
                lessonDuration = "2시간",
            ),
            onBack = {},
            onRematchingClick = {},
            onAcceptClick = {},
            onReviewClick = {},
        )
    }
}
