package com.thefork.challenge.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.thefork.challenge.api.UserFull
import com.thefork.challenge.user.ui.FadingTopBar
import com.thefork.challenge.user.ui.LoadingView
import com.thefork.challenge.user.ui.NoUserView
import com.thefork.challenge.user.ui.TitleAndText
import retrofit2.Response

@Composable
fun UserScreen(
    userId: String?,
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

        if (!userId.isNullOrBlank()) {
            val userViewModel: UserViewModel = hiltViewModel()
            val userResponse: Response<UserFull>? by userViewModel.response.observeAsState(null)

            LaunchedEffect(Unit) {
                userViewModel.getFullUser(userId)
            }

            if (userResponse == null) {
                LoadingView(modifier = Modifier.fillMaxSize())
            } else {
                if (userResponse!!.isSuccessful) {
                    val response = userResponse!!.body()

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
                            response?.picture?.let {
                                if (response.lastName.isNotBlank()) {
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
                                } else {
                                    Text(text = stringResource(id = R.string.no_image))
                                }
                            } ?: Text(text = stringResource(id = R.string.no_image))
                        }
                    }

                    Text(
                        text = "${response?.title} ${response?.firstName} ${response?.lastName}",
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
                        TitleAndText(title = "Email", text = response?.email ?: notAvailable)
                        TitleAndText(title = "Phone", text = response?.phone ?: notAvailable)
                        TitleAndText(
                            title = "Birthday",
                            text = response?.dateOfBirth ?: notAvailable
                        )
                        TitleAndText(
                            title = "Registered on",
                            text = response?.registerDate ?: notAvailable
                        )
                        TitleAndText(
                            title = "Country",
                            text = response?.location?.country ?: notAvailable
                        )
                        TitleAndText(
                            title = "City",
                            text = response?.location?.city ?: notAvailable
                        )
                        TitleAndText(
                            title = "Timezone",
                            text = response?.location?.timezone ?: notAvailable
                        )
                    }
                } else {
                    NoUserView()
                }
            }
        } else {
            NoUserView()
        }
    }

    FadingTopBar(
        title = "Profile",
        modifier = Modifier,
        scrollState = scrollState,
        navigateUp = navigateUp
    )
}