package me.shtanko.topgithub.ui.main

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_fragment.*
import me.shtanko.topgithub.R
import me.shtanko.topgithub.di.provideInjection
import me.shtanko.topgithub.extensions.isOnline
import me.shtanko.topgithub.extensions.shortToast
import me.shtanko.topgithub.platform.BaseFragment
import me.shtanko.topgithub.ui.details.DetailsActivity


class MainFragment : BaseFragment(), OnItemUserClickListener {

    private val viewModel: MainViewModel by bindViewModel()

    private lateinit var mainAdapter: MainAdapter

    private var loading = false
    private var pageNumber = 1
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    override fun onUserItemClick(userId: Int) {
        val message = String.format(resources.getString(R.string.user_id_message), userId)
        //shortToast(message)
        activity?.let {
            navigator.openDetailsActivity(it, userId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        provideInjection()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.loadData()
        mainAdapter = MainAdapter(this)

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
                    viewModel.onLoadNextPage(pageNumber, mainAdapter.getLastUserId())
                    loading = true
                }
            }
        })


        viewModel.data.observe(this, Observer {
            it?.let { list ->
                mainAdapter.addItems(list)
            }
        })

        viewModel.viewState.observe(this, Observer<MainViewModel.ViewState> { viewState ->
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
