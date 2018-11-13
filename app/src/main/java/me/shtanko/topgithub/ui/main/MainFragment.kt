package me.shtanko.topgithub.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_fragment.*
import me.shtanko.topgithub.R
import me.shtanko.topgithub.ui.main.di.DaggerMainComponent
import me.shtanko.topgithub.ui.main.extensions.isOnline
import me.shtanko.topgithub.ui.main.extensions.shortToast
import me.shtanko.topgithub.ui.main.viewmodel.MainViewModel
import me.shtanko.topgithub.ui.main.viewmodel.ViewModelFactory
import javax.inject.Inject

fun MainFragment.provideInjection() {
    DaggerMainComponent.builder().build().inject(this)
}

class MainFragment : Fragment(), OnItemUserClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    private lateinit var mainAdapter: MainAdapter

    private var loading = false
    private var pageNumber = 1
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    override fun onUserItemClick(userId: Int) {
        val message = String.format(resources.getString(R.string.user_id_message), userId)
        shortToast(message)
    }

    companion object {
        fun newInstance() = MainFragment()
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.loadData()
        mainAdapter = MainAdapter(this)

        usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mainAdapter
        }

        usersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
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
}
