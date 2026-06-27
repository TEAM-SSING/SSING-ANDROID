package com.ssing.core.ui.util
object TextInputValidator {

    private val textSpecialRegex = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]*$".toRegex()
    private val hasTextRegex = "[ㄱ-ㅎㅏ-ㅣ]".toRegex()
    /**
     * 한글, 영어, 숫자만 포함되어 있는지 검증하는 함수입니다.
     * @param text 검증할 텍스트
     */

    fun isTextInputSpecialValid(text: String): Boolean {
        return text.matches(textSpecialRegex)
    }

    fun isTextFinished(text: String): Boolean {
        return !hasTextRegex.containsMatchIn(text)
    }
}
