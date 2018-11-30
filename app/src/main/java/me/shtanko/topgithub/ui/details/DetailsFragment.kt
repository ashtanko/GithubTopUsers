package me.shtanko.topgithub.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.shtanko.topgithub.R
import me.shtanko.topgithub.platform.BaseFragment

class DetailsFragment : BaseFragment() {

    private val viewModel: DetailsViewModel by bindViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        fun newInstance() = DetailsFragment()
    }
}