package com.thefork.challenge.user.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.thefork.challenge.domain.LoadStatus
import com.thefork.challenge.domain.User
import com.thefork.challenge.user.R

@Composable
fun UserScreen(
    loadStatus: State<LoadStatus<User>>,
    navigateUp: () -> Unit
) {
    val scrollState = rememberScrollState()
    val randomPictureURL = "https://picsum.photos/300/490"
    val notAvailable = "N/A"

    Column(
        modifier = Modifier
            .padding(bottom = 24.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (loadStatus.value) {
            is LoadStatus.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }
            is LoadStatus.Success -> {
                val user = loadStatus.value.data
                Box() {
                    Image(
                        painter = rememberCoilPainter(
                            request = randomPictureURL,
                            fadeIn = true
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(0.dp)),
                        contentScale = ContentScale.FillBounds
                    )

                    Box(
                        Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 80.dp)
                    ) {
                        user?.picture?.let {
                            val imageLoaded = rememberCoilPainter(
                                request = it,
                                fadeIn = true
                            )
                            Image(
                                painter = imageLoaded,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(140.dp)
                                    .clip(shape = CircleShape)
                                    .border(2.dp, Color.White, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Text(text = stringResource(id = R.string.no_image))
                        }
                    }
                }

                Text(
                    text = "${user?.title} ${user?.firstName} ${user?.lastName}",
                    modifier = Modifier.padding(8.dp), fontSize = 28.sp
                )

                Column(
                    modifier = Modifier
                        .padding(top = 34.dp)
                        .fillMaxWidth()
                        .verticalScroll(state = scrollState)
                        .background(Color.White),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Information",
                        modifier = Modifier.padding(8.dp),
                        fontSize = 24.sp
                    )
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                    TitleAndText(title = "Email", text = user?.email ?: notAvailable)
                    TitleAndText(title = "Phone", text = user?.phone ?: notAvailable)
                    TitleAndText(
                        title = "Birthday",
                        text = user?.dateOfBirth ?: notAvailable
                    )
                    TitleAndText(
                        title = "Registered on",
                        text = user?.registerDate ?: notAvailable
                    )
                    TitleAndText(
                        title = "Country",
                        text = user?.location?.country ?: notAvailable
                    )
                    TitleAndText(
                        title = "City",
                        text = user?.location?.city ?: notAvailable
                    )
                    TitleAndText(
                        title = "Timezone",
                        text = user?.location?.timezone ?: notAvailable
                    )
                }
            }
            is LoadStatus.Error -> {
                NoUserView(modifier = Modifier.padding(60.dp))
            }
        }

    }

    FadingTopBar(
        title = "Profile",
        modifier = Modifier,
        scrollState = scrollState,
        navigateUp = navigateUp
    )
}