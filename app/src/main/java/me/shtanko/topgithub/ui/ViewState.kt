package me.shtanko.topgithub.ui

import me.shtanko.common.Failure

data class ViewState(
        val loading: Boolean = true,
        val error: Pair<Boolean, String> = Pair(false, "")
)

sealed class UiState {
    object ShowProgress : UiState()
    object HideProgress : UiState()
    data class ShowError(val failure: Failure) : UiState()
    object ShowSuccess : UiState()
}

sealed class ErrorUiState {
    object ShowNetworkConnectionError : ErrorUiState()
    object ShowServerError : ErrorUiState()
}

sealed class WarningUiState {

}