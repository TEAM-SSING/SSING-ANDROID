package com.ssing.presentation.consumermatching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingChip
import com.ssing.core.ui.common.component.SsingChipStyle
import com.ssing.core.ui.designsystem.component.UrlImage
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.designsystem.theme.Gray75
import com.ssing.core.ui.extension.noRippleClickable
import com.ssing.presentation.consumermatching.model.InstructorReview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun InstructorProfileContent(
    profileImageUrl: String,
    name: String,
    age: Int,
    gender: String,
    level: String,
    career: String,
    lessonCount: String,
    rating: String,
    keywords: ImmutableList<String>,
    introduction: String,
    certifications: ImmutableList<String>,
    totalReviewCount: Int,
    onReviewClick: () -> Unit,
    reviews: ImmutableList<InstructorReview>,
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        if (header != null) header()

        IdentitySection(
            profileImageUrl = profileImageUrl,
            name = name,
            age = age,
            gender = gender,
            level = level,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(16.dp))

        StatsSection(
            career = career,
            lessonCount = lessonCount,
            rating = rating,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(12.dp))

        KeywordSection(
            keywords = keywords,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(12.dp))

        IntroductionSection(
            introduction = introduction,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(12.dp))

        CertificationSection(
            certifications = certifications,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(16.dp))

        ReviewSection(
            totalReviewCount = totalReviewCount,
            onReviewClick = onReviewClick,
            reviews = reviews,
        )
    }
}

@Composable
private fun IdentitySection(
    profileImageUrl: String,
    name: String,
    age: Int,
    gender: String,
    level: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UrlImage(
            url = profileImageUrl,
            modifier = Modifier
                .clip(CircleShape)
                .height(80.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Column {
            Text(
                text = name,
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.title.b22,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${age}세",
                    color = SSINGTheme.colors.textAlternative,
                    style = SSINGTheme.typography.body.md16,
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = gender,
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.body.md16,
                )

                Spacer(Modifier.width(10.dp))

                SsingChip(
                    text = level,
                    style = SsingChipStyle.BLUE,
                )
            }
        }
    }
}

@Composable
private fun StatsSection(
    career: String,
    lessonCount: String,
    rating: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        StatCard(
            label = "경력",
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = career,
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb16,
            )
        }

        StatCard(
            label = "SSING 강습",
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = lessonCount,
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb16,
            )
        }

        StatCard(
            label = "별점",
            modifier = Modifier.weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_star_filled_padding),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )

                Text(
                    text = rating,
                    color = SSINGTheme.colors.textNormal,
                    style = SSINGTheme.typography.body.sb16,
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier
            .background(
                color = Gray75,
                shape = RoundedCornerShape(12.dp),
            )
            .heightIn(min = 72.dp)
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(all = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.md12,
        )

        content()
    }
}

@Composable
private fun LabelSection(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.md12,
        )

        content()
    }
}

@Composable
private fun KeywordSection(
    keywords: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    LabelSection(
        label = "SSING 검증",
        modifier = modifier,
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            keywords.forEach { keyword ->
                SsingChip(
                    text = keyword,
                    style = SsingChipStyle.GRAY,
                )
            }
        }
    }
}

@Composable
private fun IntroductionSection(
    introduction: String,
    modifier: Modifier = Modifier,
) {
    LabelSection(
        label = "소개",
        modifier = modifier,
    ) {
        Text(
            text = introduction,
            color = SSINGTheme.colors.textNormal,
            style = SSINGTheme.typography.caption.md12,
            modifier = Modifier
                .grayRoundedBorder()
                .fillMaxWidth()
                .heightIn(min = 112.dp)
                .padding(all = 16.dp),
        )
    }
}

@Composable
private fun CertificationSection(
    certifications: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    LabelSection(
        label = "자격증",
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .grayRoundedBorder()
                .fillMaxWidth()
                .heightIn(min = 112.dp)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            certifications.forEach { certificate ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_certificate),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )

                    Text(
                        text = certificate,
                        color = SSINGTheme.colors.textNormal,
                        style = SSINGTheme.typography.caption.md12,
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewSection(
    totalReviewCount: Int,
    onReviewClick: () -> Unit,
    reviews: ImmutableList<InstructorReview>,
    modifier: Modifier = Modifier,
) {
    val isEmpty = reviews.isEmpty() || totalReviewCount <= 0

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "강습 후기",
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.caption.sb14,
            )

            if (isEmpty) {
                Row(
                    modifier = Modifier.noRippleClickable(onClick = onReviewClick),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${totalReviewCount}개 전체 보기",
                        color = SSINGTheme.colors.textAlternative,
                        style = SSINGTheme.typography.caption.md12,
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = SSINGTheme.colors.textAlternative,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }

        if (isEmpty) {
            Box(modifier = Modifier.fillMaxWidth()) {
                var reviewCardHeight by remember { mutableStateOf(0.dp) }

                SubcomposeLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) { constraints ->
                    val cardConstraints = constraints.copy(
                        minHeight = 0,
                        maxHeight = Constraints.Infinity,
                    )

                    val maxHeightPx = reviews.indices.maxOfOrNull { index ->
                        subcompose(index) {
                            ReviewCard(review = reviews[index], modifier = Modifier.fillMaxWidth())
                        }.first().measure(cardConstraints).height
                    } ?: 0

                    reviewCardHeight = maxHeightPx.toDp()

                    layout(0, 0) {}
                }

                HorizontalPager(
                    state = rememberPagerState(pageCount = { reviews.size }),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    pageSpacing = 2.dp,
                ) { page ->
                    ReviewCard(
                        review = reviews[page],
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(reviewCardHeight),
                    )
                }
            }
        } else {
            Text(
                text = "아직 강습 후기가 없어요",
                color = SSINGTheme.colors.textDisabled,
                style = SSINGTheme.typography.body.sb16,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .grayRoundedBorder()
                    .heightIn(min = 177.dp)
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}

@Composable
private fun ReviewCard(
    review: InstructorReview,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .grayRoundedBorder()
            .padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UrlImage(
                    url = review.profileImageUrl,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(32.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                )

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text = review.nickname,
                        color = SSINGTheme.colors.textNormal,
                        style = SSINGTheme.typography.caption.sb14,
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = review.gender,
                            color = SSINGTheme.colors.textAlternative,
                            style = SSINGTheme.typography.caption.sb14,
                        )

                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_ellipse),
                            contentDescription = null,
                        )

                        Text(
                            text = "${review.age}세",
                            color = SSINGTheme.colors.textAlternative,
                            style = SSINGTheme.typography.caption.sb14,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.align(Alignment.Top),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                repeat(5) { index ->
                    val (iconRes, iconColor) = if (index < review.rating) {
                        R.drawable.ic_star_filled to Color.Unspecified
                    } else {
                        R.drawable.ic_star_empty to SSINGTheme.colors.borderDisabled
                    }

                    Icon(
                        imageVector = ImageVector.vectorResource(iconRes),
                        contentDescription = null,
                        tint = iconColor,
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            review.tags.forEach { tag ->
                SsingChip(
                    text = tag,
                    style = SsingChipStyle.BLUE,
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = review.content,
            color = SSINGTheme.colors.textNormal,
            style = SSINGTheme.typography.caption.md12,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(
            Modifier
                .weight(1f)
                .heightIn(min = 11.dp),
        )

        Text(
            text = review.date,
            color = SSINGTheme.colors.textAlternative,
            style = SSINGTheme.typography.caption.md11,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun Modifier.grayRoundedBorder(): Modifier =
    this.border(
        width = 1.dp,
        color = SSINGTheme.colors.borderAlternative,
        shape = RoundedCornerShape(12.dp)
    )

private class InstructorProfileContentPreviewProvider :
    PreviewParameterProvider<Pair<@Composable (ColumnScope.() -> Unit)?, ImmutableList<InstructorReview>>> {
    override val values: Sequence<Pair<@Composable (ColumnScope.() -> Unit)?, ImmutableList<InstructorReview>>>
        get() {
            val headerContent: @Composable (ColumnScope.() -> Unit) = {
                SsingHeader(
                    title = "이 강사님이 매칭되었어요",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp),
                    subText = "3분 이내에 수락하지 않으면 강습연결이 취소돼요"
                )
            }

            return sequenceOf(
                null to persistentListOf(),
                headerContent to persistentListOf(
                    InstructorReview(
                        profileImageUrl = "",
                        nickname = "text",
                        gender = "gender",
                        age = 5,
                        rating = 5,
                        tags = persistentListOf("스노보드", "처음타요"),
                        content = "최근 후기 내용입니다. 강사님이 잘생겼고 스키가 정말 재밌고 다음에도 또 씽을 이용하고 싶고 앞으로 스키와 사랑에 빠질 것 같고 스노보드도 언젠가 도전해보고싶으네요 하하",
                        date = "OOOO.OO.OO",
                    ),
                    InstructorReview(
                        profileImageUrl = "",
                        nickname = "text",
                        gender = "gender",
                        age = 5,
                        rating = 4,
                        tags = persistentListOf("스노보드", "처음타요"),
                        content = "최근 후기 내용입니다.",
                        date = "OOOO.OO.OO",
                    ),
                    InstructorReview(
                        profileImageUrl = "",
                        nickname = "text",
                        gender = "gender",
                        age = 5,
                        rating = 1,
                        tags = persistentListOf("스노보드", "처음타요"),
                        content = "최근 후기 내용입니다. 강사님이 잘생겼고 스키가 정말 재밌고 다음에도 또 씽을 이용하고 싶고 앞으로 스키와 사랑에 빠질 것 같고 스노보드도 언젠가 도전해보고싶으네요 하하",
                        date = "OOOO.OO.OO",
                    ),
                ),
            )
        }
}

@Preview(showBackground = true)
@Composable
private fun InstructorProfileContentPreview(
    @PreviewParameter(InstructorProfileContentPreviewProvider::class) pair: Pair<@Composable (ColumnScope.() -> Unit)?, ImmutableList<InstructorReview>>,
) {
    SSINGTheme {
        InstructorProfileContent(
            profileImageUrl = "",
            name = "김씽씽",
            age = 27,
            gender = "남",
            level = "grade1",
            career = "5년",
            lessonCount = "128회",
            rating = "4.8",
            keywords = persistentListOf("#키즈전문", "#수락률 100%", "#수락률 100%"),
            introduction = "안녕하세요! 처음 타시는 분들도 쉽고 안전하게 타실 수 있도록 돕습니다!",
            certifications = persistentListOf(
                "대한스키·스노보드협회 지도자 자격 Level 1",
                "대한스키·스노보드협회 지도자 자격 Level  2"
            ),
            totalReviewCount = 128,
            onReviewClick = {},
            reviews = pair.second,
            header = pair.first,
        )
    }
}
