package com.share.portal.presentation.utils

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.share.portal.R
import com.share.portal.presentation.ui.theme.BlueDefault

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomSheetContainer(
  data: BottomSheetData?,
  content: @Composable (BottomSheetData) -> Unit
) {
  val sheetState = rememberSaveable(data) { data != null }
  if (data == null) return
  AnimatedVisibility(
    visible = sheetState,
    enter = fadeIn(animationSpec = tween(delayMillis = AnimationConstants.DefaultDurationMillis / 3)),
    exit = fadeOut(animationSpec = tween(delayMillis = AnimationConstants.DefaultDurationMillis / 3))
  ) {
    BackHandler(sheetState) {
      data.onDismissRequest?.invoke()
    }
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(BlueDefault, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        .clickable(
          onClick = {
            data.onDismissRequest?.invoke()
          },
          indication = null,
          interactionSource = remember { MutableInteractionSource() },
        ),
    ) {
      val modifier = Modifier.align(Alignment.BottomCenter)
      Card(
        modifier = modifier
          .fillMaxWidth()
          .background(Color.White, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
          .clickable(
            indication = null,
            onClick = {},
            interactionSource = remember { MutableInteractionSource() },
          )
          .animateEnterExit(
            enter = slideInVertically(
              initialOffsetY = { height -> height },
              animationSpec = tween(),
            ),
            exit = slideOutVertically(
              targetOffsetY = { height -> height },
              animationSpec = tween(delayMillis = AnimationConstants.DefaultDurationMillis / 2),
            ),
          ),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
      ) {
        content.invoke(data)
      }
    }
  }
}

data class BottomSheetData(
  @DrawableRes val image: Int,
  @StringRes val title: Int,
  @StringRes val content: Int,
  @StringRes val positiveLabel: Int? = null,
  @StringRes val negativeLabel: Int? = null,
  val onDismissRequest: (() -> Unit)? = null,
  val onPositiveClicked: (() -> Unit)? = null,
  val onNegativeClicked: (() -> Unit)? = null,
)

@Composable
fun BottomSheetErrorContent(
  sheetData: BottomSheetData,
  dismissRequest: (() -> Unit)?
) {
  Column(
    modifier = Modifier
      .background(Color.White)
      .fillMaxWidth()
      .padding(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Bottom
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
      IconButton(onClick = { dismissRequest?.invoke() }) {
        Icon(
          painter = painterResource(id = R.drawable.ic_arrow_back),
          tint = Red,
          contentDescription = null
        )
      }
    }
    Image(
      modifier = Modifier
        .height(200.dp)
        .width(200.dp),
      contentScale = ContentScale.Fit,
      painter = painterResource(id = R.drawable.general_error),
      contentDescription = null
    )
    Text(
      modifier = Modifier.padding(12.dp),
      text = stringResource(id = sheetData.title),
      fontWeight = FontWeight.Bold
    )
    Text(
      modifier = Modifier.padding(12.dp),
      text = stringResource(id = sheetData.content),
      textAlign = TextAlign.Center
    )
  }
}