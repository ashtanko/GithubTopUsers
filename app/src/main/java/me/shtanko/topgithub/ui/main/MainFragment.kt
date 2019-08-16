package me.shtanko.topgithub.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import me.shtanko.common.android.extensions.isOnline
import me.shtanko.topgithub.R
import me.shtanko.topgithub.databinding.MainDataBinding
import me.shtanko.topgithub.extensions.shortToast
import me.shtanko.topgithub.platform.BaseFragment
import me.shtanko.topgithub.ui.ErrorUiState
import me.shtanko.topgithub.ui.UiState
import me.shtanko.topgithub.ui.ViewState
import me.shtanko.topgithub.ui.users.UsersViewModel


class MainFragment : BaseFragment(), OnItemUserClickListener {

    private val viewModel: UsersViewModel by bindViewModel()

    private lateinit var mainAdapter: MainAdapter

    private var loading = false
    private var pageNumber = 1
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    private lateinit var binding: MainDataBinding

    override fun onUserItemClick(username: String) {
        activity?.let {
            navigator.openDetailsActivity(it, username)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = MainDataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBarContent.infoButton.setOnClickListener {
            activity?.let {
                navigator.openSubmitLogActivity(it)
            }
        }

        binding.appBarContent.profileButton.setOnClickListener {
            activity?.let {
                navigator.openLoginActivity(it)
            }
        }

        binding.searchButton.setOnClickListener {
            activity?.let {
                navigator.openSearchActivity(it)
            }
        }

        viewModel.loadData()

        mainAdapter = MainAdapter(this, imageLoader)

        binding.usersRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
            adapter = mainAdapter
        }

        binding.usersRecyclerView.addOnScrollListener(object :
                androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
                totalItemCount = layoutManager.itemCount
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= (lastVisibleItem + 1) && recyclerView.context.isOnline() == true) {
                    pageNumber++
                    viewModel.loadNextPage(pageNumber, mainAdapter.getLastUserId())
                    loading = true
                }
            }
        })


        viewModel.users.observe(this, Observer {
            it?.let { list ->
                mainAdapter.addItems(list)
            }
        })

        viewModel.viewState.observe(this, Observer<ViewState> { viewState ->
            viewState?.let {
                val error = it.error
                val isError = error.first
                val errorMessage = error.second
                if (isError) {
                    if (mainAdapter.itemCount == 0)
                        binding.tryAgainButton.visibility = View.VISIBLE
                    shortToast(String.format(getString(R.string.error_message), errorMessage))
                } else {
                    binding.tryAgainButton.visibility = View.GONE
                }

                this.loading = it.loading
                if (it.loading) {
                    binding.footerProgressBar.visibility = View.VISIBLE
                } else {
                    binding.footerProgressBar.visibility = View.GONE
                }
            }
        })

        viewModel.errorUiState.observe(this, Observer {
            when (it) {
                is ErrorUiState.ShowNetworkConnectionError -> {
                    binding.tryAgainButton.visibility = View.VISIBLE

                }

                is ErrorUiState.ShowServerError -> {

                }

            }
        })

        viewModel.uiState.observe(this, Observer {
            when (it) {
                is UiState.ShowProgress -> {
                    binding.progress.visibility = View.VISIBLE
                }

                is UiState.HideProgress -> {
                    binding.progress.visibility = View.GONE
                }
            }
        })

        binding.tryAgainButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
