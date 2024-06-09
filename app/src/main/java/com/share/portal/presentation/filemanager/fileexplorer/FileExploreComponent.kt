package com.share.portal.presentation.filemanager.fileexplorer

import android.graphics.Color
import com.share.portal.R
import com.share.portal.domain.models.ParentFile
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData
import org.w3c.dom.Text

@Composable
fun ParentFile(root: ParentFile) {
  Row(
    modifier = Modifier.background(Color.White),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null
    )
    Text(text = "dataaaaaaaaaaaaa")
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