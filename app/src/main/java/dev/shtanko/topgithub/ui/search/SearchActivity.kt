package dev.shtanko.topgithub.ui.search

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dev.shtanko.common.android.extensions.clear
import dev.shtanko.common.android.extensions.visibilityOnEdit
import dev.shtanko.topgithub.R
import dev.shtanko.topgithub.databinding.SearchDataBinding
import dev.shtanko.topgithub.extensions.addTextChangedListener
import dev.shtanko.topgithub.platform.BaseActivity

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