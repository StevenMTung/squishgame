package br.com.steventung.squishgame.ui.screens.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.steventung.squishgame.R
import br.com.steventung.squishgame.ui.theme.LightYellow
import br.com.steventung.squishgame.ui.theme.PinkSquish
import br.com.steventung.squishgame.ui.theme.PurpleSquish

@Composable
fun HighScoreDialog(
    highScorePosition: Int?,
    userName: String,
    onUserNameChanger: (String) -> Unit = {},
    onRegisterGameRecord: () -> Unit = {},
    isUserNameEmptyError: Boolean
) {
    Dialog(onDismissRequest = {}) {

        Box(modifier = Modifier.heightIn(max = 480.dp)) {
            Image(
                painter = painterResource(R.drawable.money_background2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = PurpleSquish),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = "Super Fast!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightYellow,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = LightYellow,
                                    fontSize = 26.sp
                                )
                            ) { highScorePosition?.let { append("#$it") } ?: "" }
                            append(" Fastest Player!")
                        },
                        fontSize = 20.sp,
                        color = LightYellow,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Image(
                        painter = painterResource(R.drawable.prize_image),
                        contentDescription = "prize image"
                    )
                    OutlinedTextField(
                        value = userName,
                        label = { Text(text = if (isUserNameEmptyError) "Insert User Name!" else "User Name") },
                        onValueChange = { onUserNameChanger(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        maxLines = 1,
                        isError = isUserNameEmptyError
                    )
                    Button(
                        onClick = onRegisterGameRecord,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = PinkSquish)
                    ) {
                        Text(text = "Register")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HighScoreDialogPreview() {
    HighScoreDialog(
        highScorePosition = 1,
        userName = "James",
        isUserNameEmptyError = true
    )
}