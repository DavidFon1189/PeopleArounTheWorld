package com.example.peoplearoundtheworld

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import coil3.size.Size
import com.example.peoplearoundtheworld.model.User
import com.example.peoplearoundtheworld.ui.theme.SimpleRestTheme
import com.example.peoplearoundtheworld.R
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleRestTheme {
                Surface(color = MaterialTheme.colorScheme.primary){
                    MyApp1()
                }
            }
        }
    }
}

@Composable
fun MyApp1(
    userViewModel: UserViewModel = hiltViewModel(),
    ) {
    val users by userViewModel.users.observeAsState(arrayListOf())
    val isLoading by userViewModel.isLoading.observeAsState(false)
    MyApp(
        onAddClick = {
            userViewModel.addUser()
                     },
        onDleteClick = {
            userViewModel.deleteUser(it)
        },
        users = users,
        isLoading)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp (
    onAddClick: (() ->Unit)? = null,
    onDleteClick: ((toDelete: User) ->Unit)? = null,
    users: List<User>,
    isLoading: Boolean
){
    Scaffold(
    topBar = {
        TopAppBar(
            title = { Text("Simple Rest + Room") },
            actions = {
                IconButton(onClick = {
                    onAddClick?.invoke()
                }) {
                    Icon(Icons.Filled.Add, "Add")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
        )
        )
    }
){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)  // Aplica el padding del scaffold

        ) {
            var itemCount = users.size
            if (isLoading) itemCount++

            items(count = itemCount) { index ->
                var auxIndex = index
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items LoadingCard()
                    auxIndex--
                }
                val user = users[auxIndex]
                Card  (
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .wrapContentSize()
                        .testTag("LoadingCard")
                ) {
                    Row (modifier = Modifier.padding(8.dp)) {
                        Image(
//                            painterResource(R.drawable.ic_launcher_foreground),
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(data = user.thumbnail)
                                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background)
                                    .build()
                                ),
                            contentDescription = stringResource(id = R.string.app_name),
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer()
                        Column (Modifier.weight(1f)) {
                            Text("${user.name} ${user.lastName}")
                            Text(user.city)
                        }
                        Spacer()
                        IconButton(onClick = {
                            onDleteClick?.invoke(user)
                        }) {
                            Icon(Icons.Filled.Delete, "Remove")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .testTag("LoadingCard")
    ){
        //Row agrega elementos horizontal
        Row (modifier = Modifier.padding(8.dp)) {
            ImageLoading()
            Spacer()
            //Column agrega elementos vertical
            Column {
                Spacer()
                Box(modifier = Modifier.shimmer()){
                    Column {
                        Box(modifier = Modifier
                            .height(15.dp)
                            .fillMaxWidth()
                            .background(Color.Gray)
                        )
                        Spacer()
                        Box(modifier = Modifier
                            .height(15.dp)
                            .fillMaxWidth()
                            .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageLoading() {
    Box(modifier = Modifier.shimmer()) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
        )
    }
}

@Composable
fun Spacer(size: Int = 8) = androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(size.dp))

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleRestTheme {
        MyApp(users = listOf(), isLoading = true)
    }
}