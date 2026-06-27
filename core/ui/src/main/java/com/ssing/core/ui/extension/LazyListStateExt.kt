package com.ssing.core.ui.extension

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

/**
 * LazyList의 스크롤이 하단에 도달했을 때 추가 데이터를 로드합니다.
 *
 * 무한 스크롤(Infinite Scroll) 또는 페이지네이션 구현에 사용됩니다.
 *
 * @param threshold 하단으로부터 몇 개의 아이템 이전에 로드를 시작할지 (기본: 0)
 * @param isLoading 현재 로딩 중인지 여부. 중복 호출을 방지합니다.
 * @param onLoadMore 하단 도달 시 호출될 콜백
 */
@Composable
fun LazyListState.onBottomReached(
    threshold: Int = 0,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
) {
    require(threshold >= 0) { "threshold cannot be negative, but was $threshold" }

    val loadMore by rememberUpdatedState(onLoadMore)
    val loading by rememberUpdatedState(isLoading)

    val shouldLoadMore by remember {
        derivedStateOf {
            val total = layoutInfo.totalItemsCount
            if (total == 0 || loading) return@derivedStateOf false

            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            lastVisible + threshold >= total - 1
        }
    }

    LaunchedEffect(this) {
        snapshotFlow { shouldLoadMore }
            .distinctUntilChanged()
            .filter { it }
            .collect { loadMore() }
    }
}

@Composable
fun LazyGridState.onBottomReached(
    threshold: Int = 0,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
) {
    require(threshold >= 0) { "threshold cannot be negative, but was $threshold" }

    val loadMore by rememberUpdatedState(onLoadMore)
    val loading by rememberUpdatedState(isLoading)

    val shouldLoadMore by remember {
        derivedStateOf {
            val total = layoutInfo.totalItemsCount
            if (total == 0 || loading) return@derivedStateOf false

            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            lastVisible + threshold >= total - 1
        }
    }

    LaunchedEffect(this) {
        snapshotFlow { shouldLoadMore }
            .distinctUntilChanged()
            .filter { it }
            .collect { loadMore() }
    }
}
