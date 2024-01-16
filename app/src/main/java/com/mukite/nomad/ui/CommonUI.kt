package com.mukite.nomad.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import com.mukite.nomad.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun HeaderImage(modifier: Modifier = Modifier) {
    Box(modifier = modifier.height(280.dp), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.nomad_main),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedCarousel(images: List<Int>, modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        // provide pageCount
        images.size
    }

    Column(modifier = modifier.height(280.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
        ) { page ->
            Card(
                Modifier.fillMaxSize()
            ) {
                // Card content
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
//                        .clip(MaterialTheme.shapes.large)
                )
            }
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
//            .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }

    }
}
