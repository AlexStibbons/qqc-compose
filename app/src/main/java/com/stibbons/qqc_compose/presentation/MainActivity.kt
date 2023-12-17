package com.stibbons.qqc_compose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stibbons.qqc_compose.R
import com.stibbons.qqc_compose.domain.exhaustive
import com.stibbons.qqc_compose.ui.theme.QQCComposeTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    /***
     * unsure where VM and state observables should be?
     */

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

    //TODO should survive orientation change
    val msgList = remember {
        mutableStateListOf<MsgItemPresentation>()
    }

    //TODO why is flag here? can it be better?
    val btnIsEnabled = rememberSaveable {
        mutableStateOf(true)
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

            BtnStart(btnIsEnabled) {
                btnIsEnabled.value = false
                vm.fetchData()
                msgList.clear()
            }


            CreateList(items = msgList, btnState = btnIsEnabled)

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
fun CreateList(
    items: List<MsgItemPresentation>,
    btnState: MutableState<Boolean>
) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState
    ) {
        items(
            items = items,
        ) { msg ->
            if (msg.isDone) {
                MsgComplete(msg = msg)
                btnState.value = true
            } else MsgView(msg = msg)

            /*LaunchedEffect(listState) {
                listState.scrollToItem(items.lastIndex)
            }*/ //TODO this glitches
        }
    }

}

@Composable
fun MsgView(msg: MsgItemPresentation) {
    val cardColor = if (msg.isReceived) R.color.teal_200 else R.color.white
    val textColor = if (msg.isReceived) R.color.white else R.color.black

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        contentAlignment = if (msg.isReceived) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 10.dp,
            modifier = Modifier
                .width(200.dp)
                .wrapContentHeight()
                .padding(10.dp),
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

@Composable
fun MsgComplete(msg: MsgItemPresentation) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 10.dp,
            modifier = Modifier.apply {
                padding(10.dp)
            },
            backgroundColor = colorResource(id = R.color.purple_200)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                color = colorResource(id = R.color.white),
                text = stringResource(id = msg.text)
            )
        }
    }
}

@Composable
fun BtnStart(isEnabled: MutableState<Boolean>, action: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            enabled = isEnabled.value,
            onClick = action,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple_200))
        ) {
            Text(
                text = "Start",
                color = Color.White
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QQCComposeTheme {


    }
}