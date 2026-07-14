package com.ssing.core.ui.state

import com.ssing.core.ui.R
import java.time.LocalDateTime

sealed interface HomeLessonCardState {
    data class Reservation(
        val lessonId: Long,
        val chip: String,
        val displayText: String,
        val location: String,
        val date: LocalDateTime,
        val sport: HomeSport,
        val status: Status,
    ) : HomeLessonCardState {
        sealed interface Status {
            data object Default : Status
            data object Matched : Status
            data object Matching : Status
        }
    }

    data object Empty : HomeLessonCardState
}

enum class HomeSport(val imageRes: Int) {
    SKI(R.drawable.img_ski_86),
    SNOWBOARD(R.drawable.img_snowboard_86),
}

