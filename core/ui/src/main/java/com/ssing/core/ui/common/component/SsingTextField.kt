package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 기본 텍스트 입력 컴포넌트입니다.
 *
 * @param state 입력값을 관리하는 [TextFieldState].
 * @param placeholder 입력값이 비어있을 때 표시하는 안내 텍스트.
 * @param modifier Composable에 적용할 Modifier.
 */
@Composable
fun SsingTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)

    BasicTextField(
        state = state,
        textStyle = SSINGTheme.typography.caption.sb14.copy(
            color = SSINGTheme.colors.textNormal,
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        modifier = modifier,
        decorator = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SSINGTheme.colors.backgroundNormal,
                        shape = shape,
                    )
                    .border(
                        width = 1.dp,
                        color = SSINGTheme.colors.borderAlternative,
                        shape = shape,
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (state.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = SSINGTheme.typography.caption.sb14,
                        color = SSINGTheme.colors.textDisabled,
                    )
                }
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SsingTextFieldPreview() {
    SSINGTheme {
        val state = rememberTextFieldState()
        Box(
            modifier = Modifier
                .padding(20.dp)
        ) {
            SsingTextField(
                state = state,
                placeholder = "직접 입력",
            )
        }
    }
}
