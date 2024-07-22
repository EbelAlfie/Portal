package com.share.portal.presentation.filemanager.fileexplorer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.share.portal.R
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData
import com.share.portal.presentation.filemanager.fileexplorer.model.FileExtension.AUDIO
import com.share.portal.presentation.filemanager.fileexplorer.model.ParentData
import com.share.portal.presentation.ui.theme.BlueDefault
import com.share.portal.presentation.ui.theme.Grey
import java.io.File

@Composable
fun ParentFile(root: ParentData) {
  Row(
    modifier = Modifier
      .background(Color.White)
      .padding(10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      modifier = Modifier.size(15.dp).padding(end = 3.dp),
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null
    )
    Text(text = root.name)
  }
}

@Composable
fun FileItem(
  modifier: Modifier = Modifier,
  file: FileData,
  isSelected: Boolean = false
) {
  Row(
    modifier = modifier
      .padding(3.dp)
      .fillMaxWidth()
      .background(if (isSelected) BlueDefault else Companion.White)
      .border(1.dp, Color.Black, RectangleShape)
  ) {
    Icon(
      modifier = Modifier.padding(10.dp),
      painter = painterResource(id = file.extension.icon),
      tint = Grey,
      contentDescription = null
    )
    Text(
      modifier = Modifier
        .padding(10.dp)
        .weight(1f),
      text = file.file.name
    )
    Icon(
      modifier = Modifier.padding(10.dp),
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null
    )
  }
}

@Preview
@Composable
fun FileParentPreview() {
  ParentFile(
    ParentData(
      name = "rut",
      path = "/rut"
    )
  )
}

@Preview
@Composable
fun FileItemPreview() {
  FileItem(
    file = FileData(
      file = File.createTempFile("oi", ".mp3"),
      isSelected = false,
      extension = AUDIO
    )
  )
}