package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.roundedBackgroundWithBorder

/**
 * 기본 텍스트 입력 컴포넌트입니다.
 *
 * @param state 입력값을 관리하는 [TextFieldState].
 * @param placeholder 입력값이 비어있을 때 표시하는 안내 텍스트.
 * @param modifier Composable에 적용할 Modifier.
 * @param lineLimits 텍스트필드 줄 수 제한. 기본값은 한 줄.
 * @param keyboardOptions 소프트 키보드의 타입, IME 액션 등 설정.
 * @param onKeyboardAction 키보드 액션 버튼 클릭 시 호출되는 콜백. [keyboardOptions]에 설정된 [ImeAction]을 인자로 전달.
 * @param inputTransformation 사용자 입력을 제한하거나 입력값을 변형할 때 사용.
 * @param outputTransformation 실제 저장되는 값은 변경하지 않고 화면에 표시되는 텍스트만 변형할 때 사용.
 */
@Composable
fun SsingTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: ((ImeAction) -> Unit)? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
) {
    BasicTextField(
        state = state,
        textStyle = SSINGTheme.typography.caption.sb14.copy(
            color = SSINGTheme.colors.textNormal,
        ),
        lineLimits = lineLimits,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction?.let { handler ->
            KeyboardActionHandler { performDefaultAction ->
                handler(keyboardOptions.imeAction)
                performDefaultAction()
            }
        },
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        modifier = modifier,
        decorator = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .roundedBackgroundWithBorder(
                        shape = RoundedCornerShape(12),
                        backgroundColor = SSINGTheme.colors.backgroundNormal,
                        borderColor = SSINGTheme.colors.borderAlternative,
                        borderWidth = 1.dp,
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (state.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = SSINGTheme.typography.caption.sb14,
                        color = SSINGTheme.colors.textDisabled,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
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
