package com.stibbons.qqc_compose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stibbons.qqc_compose.R
import com.stibbons.qqc_compose.domain.exhaustive
import com.stibbons.qqc_compose.ui.theme.QQCComposeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
           MainScreen(vm = mainViewModel)
        }
    }
}

@Composable
internal fun MainScreen(vm: MainViewModel) {

    val viewState = vm.screenState.observeAsState().value

    var msgList = remember {
        mutableStateListOf<MsgItemPresentation>()
    }

    QQCComposeTheme {

        Column(
            modifier = Modifier.apply {
                fillMaxSize()
                padding(10.dp)
                background(color = MaterialTheme.colors.background)
            },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
               items(items = msgList) { msg ->
                   MsgView(msg = msg)
               }
            }

            when (viewState) {
                is MainViewModel.ViewState.Item -> {
                    msgList.add(viewState.data)
                }
                null -> {}
            }.exhaustive

        }
    }
}

@Composable
fun MsgView(msg: MsgItemPresentation) {
    val cardColor = if (msg.isReceived) R.color.teal_200 else R.color.white
    val textColor = if (msg.isReceived) R.color.white else R.color.black

    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 10.dp,
            modifier = Modifier.apply {
                padding(10.dp)
            },
            backgroundColor = colorResource(id = cardColor)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                color = colorResource(id = textColor),
                text = stringResource(id = msg.text)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QQCComposeTheme {
       MsgView(msg = MsgItemPresentation(
           true,
           R.string.one,
           false
       ))

    }
}