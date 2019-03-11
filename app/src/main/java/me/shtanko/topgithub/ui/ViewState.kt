package me.shtanko.topgithub.ui

data class ViewState(
    val loading: Boolean = true,
    val error: Pair<Boolean, String> = Pair(false, "")
)