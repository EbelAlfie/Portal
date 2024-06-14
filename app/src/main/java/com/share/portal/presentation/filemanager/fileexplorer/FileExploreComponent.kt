package com.share.portal.presentation.filemanager.fileexplorer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.share.portal.R
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData
import com.share.portal.presentation.filemanager.fileexplorer.model.ParentData

@Composable
fun ParentFile(root: ParentData) {
  Row(
    modifier = Modifier.background(Color.White),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      modifier = Modifier.size(15.dp), 
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null
    )
    Text(text = root.name)
  }
}

@Composable
fun FileItem(file: FileData) {
  Row {
    Icon(
      painter = painterResource(id = file.extension.icon),
      contentDescription = null
    )
    Text(text = file.file.name)
    Icon(
      modifier = Modifier.border(1.dp, Color.Black, RectangleShape),
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null
    )
  }
}
