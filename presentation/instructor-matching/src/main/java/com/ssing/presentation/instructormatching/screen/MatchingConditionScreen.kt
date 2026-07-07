package com.ssing.presentation.instructormatching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingSelectButton
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructormatching.MatchingContract
import com.ssing.presentation.instructormatching.component.MatchingConditionFixedResortField
import com.ssing.presentation.instructormatching.component.MatchingConditionNoticeSection
import com.ssing.presentation.instructormatching.component.MatchingConditionSection
import com.ssing.presentation.instructormatching.component.MatchingStepSlider
import com.ssing.presentation.instructormatching.component.MultiSelectBadge
import com.ssing.presentation.instructormatching.model.ConditionUiState
import com.ssing.presentation.instructormatching.model.DurationOption
import com.ssing.presentation.instructormatching.model.LevelOption
import com.ssing.presentation.instructormatching.model.SportOption

@Composable
internal fun MatchingConditionScreen(
    condition: ConditionUiState,
    onIntent: (MatchingContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SSINGTheme.colors.backgroundNormal),
    ) {
        SsingTopBar(
            title = "씽 매칭 노출 조건",
            onBack = { onIntent(MatchingContract.Intent.OnBackClick) },
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            SsingHeader(
                title = "어떤 조건으로 노출할까요?",
                subText = "부츠와 장비를 착용하고 바로 이동 가능한 상태에서만 빠른 매칭을 시작할 수 있어요",
                modifier = Modifier.padding(top = 24.dp),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                MatchingConditionSection(label = "강습 장소") {
                    MatchingConditionFixedResortField(resortName = condition.resortName)
                }

                MatchingConditionSection(label = "강습 종목") {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        condition.availableSports.sortedBy { it.ordinal }.forEach { sport ->
                            SsingSelectButton(
                                text = sport.label,
                                isSelected = sport in condition.selectedSports,
                                onClick = { onIntent(MatchingContract.Intent.OnSportToggle(sport)) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                        if (condition.availableSports.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                MatchingConditionSection(
                    label = "강습 가능 레벨",
                    labelSuffix = { MultiSelectBadge() },
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        LevelOption.entries.chunked(LEVEL_OPTION_COLUMN_COUNT)
                            .forEach { rowOptions ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    rowOptions.forEach { level ->
                                        SsingSelectButton(
                                            text = level.label,
                                            isSelected = level in condition.selectedLevels,
                                            onClick = {
                                                onIntent(MatchingContract.Intent.OnLevelToggle(level))
                                            },
                                            modifier = Modifier.weight(1f),
                                        )
                                    }
                                }
                            }
                    }
                }

                MatchingConditionSection(
                    label = "강습 시간",
                    labelSuffix = { MultiSelectBadge() },
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DurationOption.entries.forEach { duration ->
                            SsingSelectButton(
                                text = duration.label,
                                isSelected = duration in condition.selectedDurations,
                                onClick = {
                                    onIntent(MatchingContract.Intent.OnDurationToggle(duration))
                                },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }

                MatchingConditionSection(
                    label = "최대 인원",
                    labelSuffix = {
                        Text(
                            text = "최대 ${condition.maxHeadcount}명",
                            style = SSINGTheme.typography.caption.sb12,
                            color = SSINGTheme.colors.primaryNormal,
                        )
                    },
                ) {
                    MatchingStepSlider(
                        value = condition.maxHeadcount,
                        onValueChange = {
                            onIntent(MatchingContract.Intent.OnMaxHeadcountChange(it))
                        },
                        valueRange = condition.maxHeadcountRange,
                    )
                }

                MatchingConditionNoticeSection(
                    isChecked = condition.isNoticeChecked,
                    onCheckedChange = {
                        onIntent(MatchingContract.Intent.OnNoticeCheckedChange(it))
                    },
                )

                SsingButton(
                    text = "씽 매칭 시작",
                    onClick = { onIntent(MatchingContract.Intent.OnStartMatchingClick) },
                    style = SsingButtonStyle.BLUE,
                    enabled = condition.isStartEnabled,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

private const val LEVEL_OPTION_COLUMN_COUNT = 2

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun MatchingConditionScreenPreview() {
    SSINGTheme {
        MatchingConditionScreen(
            condition = ConditionUiState(
                resortName = "하이원 리조트",
                selectedLevels = setOf(LevelOption.BEGINNER, LevelOption.INTERMEDIATE),
                selectedDurations = setOf(DurationOption.HOUR_3),
                maxHeadcount = 3,
                isNoticeChecked = true,
            ).applyProfile(
                availableSports = setOf(SportOption.SKI),
                resortName = "하이원 리조트",
            ),
            onIntent = {},
        )
    }
}
