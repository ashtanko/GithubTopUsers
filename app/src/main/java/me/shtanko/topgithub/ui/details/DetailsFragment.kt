package me.shtanko.topgithub.ui.details

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import me.shtanko.common.android.processing.image.TransformType
import me.shtanko.topgithub.R
import me.shtanko.topgithub.navigation.Navigator
import me.shtanko.topgithub.platform.BaseFragment
import me.shtanko.topgithub.ui.ViewState
import me.shtanko.topgithub.ui.user.UserViewModel

class DetailsFragment : BaseFragment() {

    private val viewModel: UserViewModel by bindViewModel()

    private lateinit var profileImageView: AppCompatImageView
    private lateinit var reposCountTextView: AppCompatTextView
    private lateinit var starsCountTextView: AppCompatTextView
    private lateinit var followersCountTextView: AppCompatTextView
    private lateinit var followingCountTextView: AppCompatTextView
    private lateinit var nickNameTextView: AppCompatTextView
    private lateinit var profileSpecialityTextView: AppCompatTextView
    private lateinit var profileLocationTextView: AppCompatTextView
    private lateinit var profileCompanyTextView: AppCompatTextView
    private lateinit var profileSiteTextView: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileImageView = view.findViewById(R.id.profileImageView)
        reposCountTextView = view.findViewById(R.id.reposCountTextView)
        starsCountTextView = view.findViewById(R.id.starsCountTextView)
        followersCountTextView = view.findViewById(R.id.followersCountTextView)
        followingCountTextView = view.findViewById(R.id.followingCountTextView)
        nickNameTextView = view.findViewById(R.id.nickNameTextView)
        profileSpecialityTextView = view.findViewById(R.id.profileSpecialityTextView)
        profileLocationTextView = view.findViewById(R.id.profileLocationTextView)
        profileCompanyTextView = view.findViewById(R.id.profileCompanyTextView)
        profileSiteTextView = view.findViewById(R.id.profileCompanyTextView)

        val username = arguments?.getString(Navigator.EXTRA_USERNAME) ?: "JakeWharton"

        viewModel.getUser(username)

        viewModel.viewState.observe(this, Observer<ViewState> { viewState ->
            viewState?.let {

            }
        })

        viewModel.user.observe(this, Observer {
            imageLoader.load(
                it.avatarUrl ?: "", TransformType.CIRCLE_CROP, profileImageView,
                R.drawable.ic_image_placeholder, R.drawable.ic_broken_image_black
            )

            nickNameTextView.text = it.name ?: ""

            reposCountTextView.text = "${it.publicRepos ?: 0}"
            followersCountTextView.text = "${it.followers ?: 0}"
            followingCountTextView.text = "${it.following ?: 0}"
            starsCountTextView.text = "${0}"

            profileSpecialityTextView.text = it.bio ?: ""
            profileLocationTextView.text = it.location ?: ""
            profileCompanyTextView.text = it.company ?: ""

            Linkify.addLinks(profileSiteTextView, Linkify.ALL)
            profileSiteTextView.movementMethod = LinkMovementMethod.getInstance()
            val html = "<a href=\"${it.blog ?: ""}\">${it.blog ?: ""}</a>"
            profileSiteTextView.text = Html.fromHtml(html)
        })

    }

    companion object {
        fun newInstance(username: String): DetailsFragment {
            val args = Bundle()
            args.putString(Navigator.EXTRA_USERNAME, username)
            val instance = DetailsFragment()
            instance.arguments = args
            return instance
        }
    }
}