package com.norm.myswiperefreshcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.norm.myswiperefreshcompose.ui.theme.MySwipeRefreshComposeTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySwipeRefreshComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var itemCount by remember {
                        mutableStateOf(5)
                    }
                    val state = rememberPullToRefreshState()
                    if (state.isRefreshing) {
                        LaunchedEffect(true) {
                            delay(1500)
                            itemCount += 2
                            state.endRefresh()
                        }
                    }
                    Box(
                        modifier = Modifier
                            .nestedScroll(state.nestedScrollConnection)
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            if (!state.isRefreshing) {
                                items(itemCount) {
                                    ListItem(
                                        {
                                            Text(text = "Item ${itemCount - it}")
                                        }
                                    )
                                }
                            }
                        }
                        PullToRefreshContainer(
                            modifier = Modifier
//                                .offset(y = -16.dp)
                                .align(Alignment.TopCenter),
                            state = state,
                        )
                    }
                }
            }
        }
    }
}