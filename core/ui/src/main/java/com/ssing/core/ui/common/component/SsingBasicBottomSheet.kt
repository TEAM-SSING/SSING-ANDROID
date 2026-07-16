package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 기본 바텀시트 컴포넌트입니다.
 *
 * @param onDismissRequest 바텀시트를 닫을 때 호출하는 콜백.
 * @param modifier Composable에 적용할 Modifier.
 * @param bottomSheetState 바텀시트의 열림 상태를 제어하는 [SheetState].
 * @param showScrim 바텀시트 뒤 dimmed 처리 여부.
 * @param content 바텀시트 내부 콘텐츠.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SsingBasicBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    showScrim: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = bottomSheetState,
        sheetMaxWidth = Dp.Unspecified,
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
        ),
        containerColor = SSINGTheme.colors.backgroundNormal,
        scrimColor = if (showScrim) Color(0x73000000) else Color.Transparent,
        dragHandle = { CustomDragHandle() },
        content = content,
    )
}

@Composable
private fun CustomDragHandle(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp)
            .size(
                width = 70.dp,
                height = 3.dp
            )
            .background(
                color = SSINGTheme.colors.borderAlternative,
                shape = RoundedCornerShape(100.dp)
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SsingBasicBottomSheetPreview() {
    SSINGTheme {
        var showSheet by remember { mutableStateOf(true) }

        if (showSheet) {
            SsingBasicBottomSheet(
                onDismissRequest = { showSheet = false }
            ) {
                Text (
                    text = "콘텐츠를 표시합니다.",
                    color = SSINGTheme.colors.textNormal,
                    modifier = Modifier
                        .height(500.dp)
                        .padding(20.dp),
                )
            }
        }
    }
}