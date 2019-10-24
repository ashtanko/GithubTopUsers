package dev.shtanko.topgithub.ui.search

import dev.shtanko.topgithub.viewmodel.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(

) : BaseViewModel() {

    fun searchDataChanged(query: String) {

    }

    sealed class SearchUiState {

    }
}