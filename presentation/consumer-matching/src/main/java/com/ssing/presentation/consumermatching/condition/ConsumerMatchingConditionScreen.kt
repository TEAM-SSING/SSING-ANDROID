package com.ssing.presentation.consumermatching.condition

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.MatchingConditionInformationCard
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingDropdownField
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingPlusButton
import com.ssing.core.ui.common.component.SsingSelectButton
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.consumermatching.component.ConsumerInfoInputCard
import com.ssing.presentation.consumermatching.condition.component.ConditionSection
import com.ssing.presentation.consumermatching.condition.component.ConditionSectionStyle
import com.ssing.presentation.consumermatching.type.ConsumerGender

@Composable
internal fun ConsumerMatchingConditionRoute(
    onPopBackStack: () -> Unit,
    navigateToMatching: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConsumerMatchingConditionViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            ConsumerMatchingConditionContract.Effect.NavigateToMatching -> navigateToMatching()
            is ConsumerMatchingConditionContract.Effect.ShowToast -> context.toast(effect.message)
        }
    }

    ConsumerMatchingConditionScreen(
        state = state,
        onResortSelect = viewModel::onResortSelect,
        onSportSelect = viewModel::onSportSelect,
        onLevelSelect = viewModel::onLevelSelect,
        onDurationSelect = viewModel::onDurationSelect,
        onAddConsumerClick = viewModel::onAddConsumerClick,
        onConsumerDelete = viewModel::onConsumerDelete,
        onConsumerGenderSelect = viewModel::onConsumerGenderSelect,
        onConsumerFocusChange = viewModel::onConsumerFocusChange,
        onConfirm = viewModel::onConfirm,
        onStartMatchingClick = viewModel::onStartMatchingClick,
        onPopBackStack = onPopBackStack,
        modifier = modifier,
    )
}

@Composable
private fun ConsumerMatchingConditionScreen(
    state: ConsumerMatchingConditionContract.State,
    onResortSelect: (Resort) -> Unit,
    onSportSelect: (Sport) -> Unit,
    onLevelSelect: (LessonLevel) -> Unit,
    onDurationSelect: (LessonDuration) -> Unit,
    onAddConsumerClick: () -> Unit,
    onConsumerDelete: (Int) -> Unit,
    onConsumerGenderSelect: (Int, ConsumerGender) -> Unit,
    onConsumerFocusChange: (Int, Boolean) -> Unit,
    onConfirm: (Boolean) -> Unit,
    onStartMatchingClick: () -> Unit,
    onPopBackStack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SsingTopBar(
                title = "씽 매칭 조건",
                onBack = onPopBackStack,
            )
        },
        bottomBar = {
            SsingButton(
                text = "씽 매칭 시작",
                onClick = onStartMatchingClick,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier
                    .background(SSINGTheme.colors.backgroundNormal)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                enabled = state.isStartMatchingEnabled,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
        ) {
            item {
                SsingHeader(
                    title = "어떤 조건으로 찾아볼까요?",
                    modifier = Modifier.padding(vertical = 16.dp),
                    subText = "부츠와 장비를 착용한 뒤에 매칭을 추천드려요"
                )
            }

            item {
                ConditionSection(
                    label = "강습 장소",
                ) {
                    SsingDropdownField(
                        selectedItem = state.selectedResort,
                        placeholder = "하이원 리조트",
                        items = Resort.persistentEntries,
                        onItemClick = onResortSelect,
                        itemToString = { it.displayName },
                        itemToKey = { it.displayName + it.api },
                    )
                }

                Spacer(Modifier.height(24.dp))
            }

            item {
                ConditionSection(
                    label = "강습 종목",
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Sport.entries.forEach { sport ->
                            SsingSelectButton(
                                text = sport.displayName,
                                isSelected = sport == state.selectedSport,
                                onClick = { onSportSelect(sport) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            item {
                ConditionSection(
                    label = "강습 수준",
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        maxItemsInEachRow = 2,
                    ) {
                        LessonLevel.entries.forEach { level ->
                            SsingSelectButton(
                                text = level.displayName,
                                isSelected = level == state.selectedLevel,
                                onClick = { onLevelSelect(level) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            item {
                ConditionSection(
                    label = "강습 시간",
                    style = ConditionSectionStyle.MultipleSelect,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        LessonDuration.entries.forEach { duration ->
                            SsingSelectButton(
                                text = duration.displayName,
                                isSelected = duration in state.selectedDurations,
                                onClick = { onDurationSelect(duration) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            item {
                ConditionSection(
                    label = "강습생 정보",
                    style = ConditionSectionStyle.ParticipantCount(
                        currentCount = state.consumers.size,
                        totalCount = MAX_CONSUMER_COUNT,
                    ),
                    modifier = Modifier.animateContentSize(),
                ) {
                    state.consumers.forEachIndexed { index, consumer ->
                        ConsumerInfoInputCard(
                            heading = "강습생 ${index + 1}",
                            ageState = consumer.ageState,
                            selectedConsumerGender = consumer.gender,
                            isFocused = consumer.isFocused,
                            onGenderClick = { onConsumerGenderSelect(consumer.id, it) },
                            onFocus = { onConsumerFocusChange(consumer.id, it) },
                            onDelete = if (state.consumers.size > 1) {
                                { onConsumerDelete(consumer.id) }
                            } else {
                                null
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                        )
                    }

                    if (state.showAddConsumer) {
                        SsingPlusButton(
                            text = "강습생 추가",
                            onClick = onAddConsumerClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp),
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            item {
                MatchingConditionInformationCard(
                    isChecked = state.isConfirmed,
                    onCheckedChange = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun ConsumerMatchingConditionScreenPreview() {
    SSINGTheme {
        ConsumerMatchingConditionScreen(
            state = ConsumerMatchingConditionContract.State(),
            onResortSelect = {},
            onSportSelect = {},
            onLevelSelect = {},
            onDurationSelect = {},
            onAddConsumerClick = {},
            onConsumerDelete = {},
            onConsumerGenderSelect = { _, _ -> },
            onConsumerFocusChange = { _, _ -> },
            onConfirm = {},
            onStartMatchingClick = {},
            onPopBackStack = {},
        )
    }
}
