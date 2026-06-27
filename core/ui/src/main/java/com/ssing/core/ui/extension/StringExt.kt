package com.ssing.core.ui.extension
import java.text.BreakIterator

/**
 * 문자열의 글자수를 반환합니다.
 *
 * 이모지, 결합 문자 등을 하나의 문자로 계산하여
 * 사용자가 인식하는 실제 문자 개수를 반환합니다.
 *
 * @return 글자 카운트
 */
fun String.checkLength(): Int {
    if (isEmpty()) return 0

    val iterator = BreakIterator.getCharacterInstance()
    iterator.setText(this)

    var count = 0
    while (iterator.next() != BreakIterator.DONE) {
        count++
    }

    return count
}
