package com.ssing.presentation.consumermatching.type

enum class ConsumerGender(
    val api: String,
    val displayName: String,
) {
    FEMALE(
        api = "FEMALE",
        displayName = "여성",
    ),
    MALE(
        api = "MALE",
        displayName = "남성",
    );
}