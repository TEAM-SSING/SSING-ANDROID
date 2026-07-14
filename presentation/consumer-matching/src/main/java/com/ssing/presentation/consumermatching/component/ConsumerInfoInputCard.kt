package com.ssing.presentation.consumermatching.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.SsingSelectButton
import com.ssing.core.ui.common.component.SsingTextField
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.checkDigitsOnly
import com.ssing.core.ui.extension.checkMaxLength
import com.ssing.core.ui.extension.noRippleClickable
import com.ssing.core.ui.extension.roundedBackgroundWithBorder
import com.ssing.presentation.consumermatching.type.ConsumerGender

private val AgeInputTransformation: InputTransformation = InputTransformation.checkDigitsOnly().checkMaxLength(3)

@Composable
fun ConsumerInfoInputCard(
    heading: String,
    ageState: TextFieldState,
    selectedConsumerGender: ConsumerGender?,
    isFocused: Boolean,
    onGenderClick: (ConsumerGender) -> Unit,
    onFocus: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onDelete: (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .roundedBackgroundWithBorder(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Blue50,
                borderColor = if (isFocused) SSINGTheme.colors.primaryNormal else Blue50,
                borderWidth = 1.dp,
            )
            .padding(all = 16.dp)
    ) {
        Row {
            Text(
                text = heading,
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb16,
                modifier = Modifier.weight(1f),
            )

            onDelete?.let { delete ->
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_trash_empty),
                    contentDescription = "delete consumer",
                    tint = SSINGTheme.colors.textAlternative,
                    modifier = Modifier.noRippleClickable(onClick = {
                        focusManager.clearFocus(force = true)
                        delete()
                    }),
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "나이",
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.sb12,
        )

        Spacer(Modifier.height(6.dp))

        SsingTextField(
            state = ageState,
            placeholder = "나이 입력",
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocus(it.isFocused) },
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            inputTransformation = AgeInputTransformation,
            onKeyboardAction = { onFocus(false) },
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "성별",
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.sb12,
        )

        Spacer(Modifier.height(6.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ConsumerGender.entries.forEach {
                SsingSelectButton(
                    text = it.displayName,
                    isSelected = selectedConsumerGender == it,
                    onClick = {
                        focusManager.clearFocus()
                        onFocus(true)
                        onGenderClick(it)
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

private class ConsumerInfoInputCardPreviewParameter :
    PreviewParameterProvider<Pair<Boolean, (() -> Unit)?>> {
    override val values: Sequence<Pair<Boolean, (() -> Unit)?>>
        get() = sequenceOf(
            true to { },
            false to null,
        )
}

@Preview(showBackground = true)
@Composable
private fun ConsumerInfoInputCardPreview(
    @PreviewParameter(ConsumerInfoInputCardPreviewParameter::class) pair: Pair<Boolean, (() -> Unit)?>,
) {
    var selectedGender by remember { mutableStateOf<ConsumerGender?>(null) }

    SSINGTheme {
        ConsumerInfoInputCard(
            heading = "강습생 1",
            ageState = rememberTextFieldState(),
            selectedConsumerGender = selectedGender,
            isFocused = pair.first,
            onGenderClick = { selectedGender = it },
            onFocus = {},
            modifier = Modifier.padding(all = 20.dp),
            onDelete = pair.second,
        )
    }
}
