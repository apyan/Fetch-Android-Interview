package com.apyan.fetchinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apyan.fetchinterview.dagger.MainApplication
import com.apyan.fetchinterview.ui.screen.ErrorScreen
import com.apyan.fetchinterview.ui.screen.ItemListScreen
import com.apyan.fetchinterview.ui.screen.LoadingScreen
import com.apyan.fetchinterview.ui.theme.FetchInterviewTheme
import com.apyan.fetchinterview.viewmodel.ItemListViewModel
import com.apyan.fetchinterview.viewmodel.LoadingStatus
import com.apyan.fetchinterview.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ItemListViewModel>
    private val itemListViewModel: ItemListViewModel by lazy {
        viewModelFactory.get<ItemListViewModel>(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        itemListViewModel.launchItemListSearch(this)

        setContent {
            val loadingStatus = itemListViewModel.loadingStatus.collectAsStateWithLifecycle()
            val hashedItemMap = itemListViewModel.hashedItemMap.collectAsStateWithLifecycle()

            FetchInterviewTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    when (loadingStatus.value) {
                        LoadingStatus.LOADING -> LoadingScreen()
                        LoadingStatus.SUCCESS -> {
                            ItemListScreen(
                                itemMapListing = hashedItemMap.value
                            )
                        }
                        LoadingStatus.ERROR -> {
                            ErrorScreen(
                                retrySearch = {
                                    itemListViewModel.launchItemListSearch(this@MainActivity)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
