package com.presentation.consumermatching.condition.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme

internal sealed class ConditionSectionStyle(
    val arrangement: Arrangement.Horizontal,
) {
    data object Default : ConditionSectionStyle(arrangement = Arrangement.Start)
    data object MultipleSelect : ConditionSectionStyle(arrangement = Arrangement.spacedBy(8.dp))
    data class PartipicantCount(
        val currentCount: Int,
        val totalCount: Int,
    ) : ConditionSectionStyle(arrangement = Arrangement.SpaceBetween)
}

@Composable
internal fun ConditionSection(
    label: String,
    modifier: Modifier = Modifier,
    style: ConditionSectionStyle = ConditionSectionStyle.Default,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = style.arrangement,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.sb12,
            )

            if (style == ConditionSectionStyle.MultipleSelect) {
                Text(
                    text = "*복수 선택",
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.caption.sb12,
                )
            }

            if (style is ConditionSectionStyle.PartipicantCount) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = style.currentCount.toString(),
                        color = SSINGTheme.colors.primaryNormal,
                        style = SSINGTheme.typography.caption.sb12,
                    )

                    Text(
                        text = "/",
                        color = SSINGTheme.colors.primaryNormal,
                        style = SSINGTheme.typography.caption.sb12,
                    )

                    Text(
                        text = style.totalCount.toString(),
                        color = SSINGTheme.colors.primaryNormal,
                        style = SSINGTheme.typography.caption.sb12,
                    )
                }
            }
        }

        content()
    }
}
