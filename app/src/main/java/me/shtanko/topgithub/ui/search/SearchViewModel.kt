package me.shtanko.topgithub.ui.search

import me.shtanko.topgithub.viewmodel.BaseViewModel
import java.lang.RuntimeException
import javax.inject.Inject

class SearchViewModel @Inject constructor(

) : BaseViewModel() {

    fun searchDataChanged(query: String) {

    }

    sealed class SearchUiState {

    }
}