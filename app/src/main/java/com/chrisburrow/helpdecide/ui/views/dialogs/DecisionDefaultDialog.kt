package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews

class DecisionDefaultDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "DecisionDefaultView"
        const val OPTION_ROW_TAG = "DecisionRow"
        const val GO_BUTTON_TAG = "DecisionGoButton"
    }
}
@Composable
fun DecisionDefaultDialog(
    options: List<String> = listOf(
        stringResource(R.string.instant_decision),
        stringResource(R.string.spin_the_wheel)
    ),
    previouslySelected: Int,
    selected:(Int) -> Unit
) {

    var selectedPosition by remember { mutableIntStateOf(previouslySelected) }

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = {  },
    ) {

        Surface(
            modifier = Modifier.testTag(DecisionDefaultDialogTags.BASE_VIEW_TAG),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth())

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(R.string.how_do_you_want_to_decide),
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {

                    items(options.size) { position ->

                        FilterChip(
                            modifier = Modifier
                                .testTag(DecisionDefaultDialogTags.OPTION_ROW_TAG + position),
                            label = {
                                Text(
                                    color = Color.DarkGray,
                                    text = options[position]
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                                    contentDescription = ""
                                )
                            },
                            onClick = { selectedPosition = position },
                            selected = selectedPosition == position
                        )
                    }
                }

                TextButton(
                    modifier = Modifier
                        .testTag(DecisionDefaultDialogTags.GO_BUTTON_TAG)
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp),
                    onClick = { selected(selectedPosition) },
                ) {

                    Text(
                        fontWeight = FontWeight.SemiBold,
                        text = stringResource(R.string.go)
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun DecisionDefaultBottomSheetPreview() {

    DecisionDefaultDialog(
        previouslySelected = 0,
        selected = {})
}