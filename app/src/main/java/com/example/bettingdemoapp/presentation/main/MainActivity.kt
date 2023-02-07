package com.example.bettingdemoapp.presentation.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bettingdemoapp.databinding.ActivityMainBinding
import com.example.bettingdemoapp.delegates.viewBinding
import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportCategory
import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEventCategory
import com.example.bettingdemoapp.presentation.main.adapters.sportCategoriesAdapter.SportCategoriesRvAdapter
import com.example.bettingdemoapp.presentation.splash.SplashActivity.Companion.SPORTS_CATEGORIES_BUNDLE
import com.example.bettingdemoapp.ui.mvp.activity.base.MvpActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : MvpActivity<MainView, MainPresenter, ActivityMainBinding>(),
    MainView {

    private lateinit var sportCategoriesList: MutableList<SportsEventCategory>
    private var sportCategoriesAdapter: SportCategoriesRvAdapter? = null

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    @Inject
    lateinit var presenter: MainPresenter

    override fun initPresenter() {
        attachPresenter(presenter, this)
        presenter.init(sportCategoriesList)
    }


    override fun setSportsEventsList(sportCategories: MutableList<CustomSportCategory>) {
        if (sportCategoriesAdapter == null) {
            binding.betViewRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            sportCategoriesAdapter = SportCategoriesRvAdapter(
                sportCategories,
                this
            )
            binding.betViewRecyclerView.adapter = sportCategoriesAdapter
            binding.betViewRecyclerView.setHasFixedSize(false)
        }
    }

    override fun getPassData() {
        sportCategoriesList =
            intent.extras?.getParcelableArrayList(SPORTS_CATEGORIES_BUNDLE) ?: mutableListOf()
    }

}