package me.shtanko.topgithub.ui.search

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import me.shtanko.common.android.extensions.clear
import me.shtanko.common.android.extensions.visibilityOnEdit
import me.shtanko.topgithub.R
import me.shtanko.topgithub.databinding.SearchDataBinding
import me.shtanko.topgithub.extensions.addTextChangedListener
import me.shtanko.topgithub.platform.BaseActivity

class SearchActivity : BaseActivity() {

    private lateinit var binding: SearchDataBinding

    private val viewModel: SearchViewModel by bindViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.content_search)

        binding.toolbar.query.addTextChangedListener {
            binding.toolbar.clearQuery.visibilityOnEdit(it?.length ?: 0)
            viewModel.searchDataChanged(it.toString())
        }



        binding.toolbar.clearQuery.setOnClickListener {
            binding.toolbar.query.clear()
        }

        binding.toolbar.back.setOnClickListener {
            finishAfterTransition()
        }

    }
}