package com.ssing.presentation.instructorlessondetail.component

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.MatchingCancelBottomSheet
import com.ssing.core.ui.common.component.UserRole
import com.ssing.presentation.instructorlessondetail.screen.CancelReasonState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelReasonBottomSheet(
    cancelReasonState: CancelReasonState,
    onReasonClick: (CancelReason) -> Unit,
    onConfirmClick: (etcReasonText: String?) -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (!cancelReasonState.visible) return

    val etcState = rememberTextFieldState()
    LaunchedEffect(cancelReasonState.visible) {
        etcState.edit { replace(0, length, "") }
    }

    MatchingCancelBottomSheet(
        userRole = UserRole.INSTRUCTOR,
        selectedReason = cancelReasonState.selectedReason,
        onReasonClick = onReasonClick,
        etcState = etcState,
        onConfirmClick = { onConfirmClick(etcState.text.toString().ifBlank { null }) },
        onDismissRequest = onDismissRequest,
    )
}