package com.ssing.core.ui.extension

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.then

/**
 * 입력된 문자열의 최대 글자수를 제한합니다.
 *
 * 이모지, 결합 문자 등을 하나의 문자로 계산하여
 * maxLength를 넘을 경우 `revertAllChanges()`를 호출하여 입력을 취소합니다.
 *
 * @param maxLength 최대 글자수
 */

fun InputTransformation.checkMaxLength(maxLength: Int): InputTransformation = this.then(CheckMaxLength(maxLength))

private data class CheckMaxLength(
    private val maxLength: Int,
) : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        val length = asCharSequence().toString().checkLength()

        if (length > maxLength) {
            revertAllChanges()
        }
    }
}
