package me.shtanko.topgithub.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import me.shtanko.common.android.extensions.isOnline
import me.shtanko.topgithub.R
import me.shtanko.topgithub.extensions.shortToast
import me.shtanko.topgithub.platform.BaseFragment
import me.shtanko.topgithub.ui.ViewState
import me.shtanko.topgithub.ui.users.UsersViewModel


class MainFragment : BaseFragment(), OnItemUserClickListener {

    private val viewModel: UsersViewModel by bindViewModel()

    private lateinit var mainAdapter: MainAdapter

    private lateinit var profileButton: AppCompatImageView
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var tryAgainButton: AppCompatButton
    private lateinit var footerProgressBar: View

    private var loading = false
    private var pageNumber = 1
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    override fun onUserItemClick(username: String) {
        activity?.let {
            navigator.openDetailsActivity(it, username)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileButton = view.findViewById(R.id.profileButton)
        usersRecyclerView = view.findViewById(R.id.usersRecyclerView)
        tryAgainButton = view.findViewById(R.id.tryAgainButton)
        footerProgressBar = view.findViewById(R.id.footerProgressBar)

        profileButton.setOnClickListener {

        }

        viewModel.loadData()

        mainAdapter = MainAdapter(this, imageLoader)

        usersRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
            adapter = mainAdapter
        }

        usersRecyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
                totalItemCount = layoutManager.itemCount
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= (lastVisibleItem + 1) && recyclerView.context.isOnline()) {
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
                        tryAgainButton.visibility = View.VISIBLE
                    shortToast(String.format(getString(R.string.error_message), errorMessage))
                } else {
                    tryAgainButton.visibility = View.GONE
                }

                this.loading = it.loading
                if (it.loading) {
                    footerProgressBar.visibility = View.VISIBLE
                } else {
                    footerProgressBar.visibility = View.GONE
                }
            }
        })

        tryAgainButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
