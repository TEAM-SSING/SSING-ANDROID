package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import kotlinx.coroutines.launch

enum class UserRole {
    INSTRUCTOR,
    CONSUMER,
}

enum class CancelReason(val label: String) {
    SCHEDULE_CHANGE("일정 변경"),
    INSTRUCTOR_NO_SHOW("강사를 못 만났어요"),
    CONSUMER_NO_SHOW("강습생을 못 만났어요"),
    ETC("기타"),
}

/**
 * 강습 취소 확인 바텀시트입니다.
 *
 * @param userRole 현재 사용자의 역할. "상대방을 못 만났어요" 사유 라벨 분기에 사용.
 * @param onConfirmClick 강습 취소하기 버튼 클릭 시 호출하는 콜백.
 * @param onDismissRequest 바텀시트를 닫을 때 호출하는 콜백.
 * @param modifier Composable에 적용할 Modifier.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelBottomSheet(
    userRole: UserRole,
    selectedReason: CancelReason?,
    onReasonClick: (CancelReason) -> Unit,
    etcState: TextFieldState,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { targetValue ->
            targetValue != SheetValue.Hidden
        }
    ),
) {
    SsingBasicBottomSheet(
        onDismissRequest = onDismissRequest,
        bottomSheetState = bottomSheetState,
    ) {
        CancelBottomSheetContent(
            userRole = userRole,
            selectedReason = selectedReason,
            onReasonClick = onReasonClick,
            etcState = etcState,
            onConfirmClick = onConfirmClick,
            onDismissRequest = onDismissRequest,
        )
    }
}

@Composable
private fun CancelBottomSheetContent(
    userRole: UserRole,
    selectedReason: CancelReason?,
    onReasonClick: (CancelReason) -> Unit,
    etcState: TextFieldState,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val noShowReason = if (userRole == UserRole.CONSUMER) {
        CancelReason.INSTRUCTOR_NO_SHOW
    } else {
        CancelReason.CONSUMER_NO_SHOW
    }

    val enabled = selectedReason != null &&
            (selectedReason != CancelReason.ETC || etcState.text.isNotBlank())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_exclamation_mark),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "강습을 취소할까요?",
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb16,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "취소 사유를 남겨주세요.\n환불은 결제수단에 따라 최대 3일 걸릴 수 있어요.",
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.md14,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "취소 사유",
                    color = SSINGTheme.colors.textAlternative,
                    style = SSINGTheme.typography.caption.sb12,
                )

                Text(
                    text = "*취소 사유를 반드시 선택해주세요",
                    color = SSINGTheme.colors.accentRedNormal,
                    style = SSINGTheme.typography.caption.sb12,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    SsingSelectButton(
                        text = CancelReason.SCHEDULE_CHANGE.label,
                        isSelected = selectedReason == CancelReason.SCHEDULE_CHANGE,
                        onClick = { onReasonClick(CancelReason.SCHEDULE_CHANGE) },
                        modifier = Modifier.weight(1f),
                    )
                    SsingSelectButton(
                        text = noShowReason.label,
                        isSelected = selectedReason == noShowReason,
                        onClick = { onReasonClick(noShowReason) },
                        modifier = Modifier.weight(1f),
                    )
                }

                SsingSelectButton(
                    text = CancelReason.ETC.label,
                    isSelected = selectedReason == CancelReason.ETC,
                    onClick = { onReasonClick(CancelReason.ETC) },
                    modifier = Modifier.fillMaxWidth(),
                )

                if (selectedReason == CancelReason.ETC) {
                    Spacer(modifier = Modifier.height(4.dp))

                    SsingTextField(
                        state = etcState,
                        placeholder = "직접 입력",
                    )
                }
            }
        }

        Text(
            text = "반복적인 취소가 확인되면 향후 이용에 제한이 있을 수 있어요",
            color = SSINGTheme.colors.accentRedNormal,
            style = SSINGTheme.typography.caption.sb12,
        )

        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SsingButton(
                text = "강습 취소하기",
                onClick = onConfirmClick,
                style = if (enabled) SsingButtonStyle.RED else SsingButtonStyle.GRAY,
                enabled = enabled,
                modifier = Modifier.weight(1f),
            )

            SsingButton(
                text = "계속 이용하기",
                onClick = onDismissRequest,
                style = SsingButtonStyle.GRAY,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

private class CancelBottomSheetPreviewProvider : PreviewParameterProvider<UserRole> {
    override val values: Sequence<UserRole>
        get() = sequenceOf(UserRole.INSTRUCTOR, UserRole.CONSUMER)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun CancelBottomSheetPreview(
    @PreviewParameter(CancelBottomSheetPreviewProvider::class) userRole: UserRole,
) {
    SSINGTheme {
        var showSheet by remember { mutableStateOf(true) }
        var selectedReason by remember { mutableStateOf<CancelReason?>(null) }
        val etcState = rememberTextFieldState()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { it != SheetValue.Hidden }
        )
        val scope = rememberCoroutineScope()

        fun dismissSheet() {
            scope.launch {
                bottomSheetState.hide()
            }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    showSheet = false
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (showSheet) {
                CancelBottomSheet(
                    userRole = userRole,
                    selectedReason = selectedReason,
                    onReasonClick = { selectedReason = it },
                    etcState = etcState,
                    onConfirmClick = { dismissSheet() },
                    onDismissRequest = { dismissSheet() },
                )
            }
        }
    }
}