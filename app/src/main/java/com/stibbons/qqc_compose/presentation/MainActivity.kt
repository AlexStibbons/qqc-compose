package com.stibbons.qqc_compose.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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

    QQCComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            when (viewState) {
                is MainViewModel.ViewState.Item -> Greeting(viewState.data.text)
                MainViewModel.ViewState.Completed -> Greeting(R.string.complete)
                null -> {} //when is state null?
            }.exhaustive


        }
    }
}

@Composable
fun Greeting(msgText: Int) {
    Text(text = stringResource(id = msgText))
}

@Composable
fun Msg(input: Int) {
    Text(text = stringResource(id = input))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QQCComposeTheme {
        Greeting(R.string.one)
    }
}