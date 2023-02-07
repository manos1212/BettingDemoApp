package com.example.bettingdemoapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bettingdemoapp.databinding.ActivitySplashBinding
import com.example.bettingdemoapp.delegates.viewBinding
import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEventCategory
import com.example.bettingdemoapp.presentation.main.MainActivity
import com.example.bettingdemoapp.ui.mvp.activity.base.MvpActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : MvpActivity<SplashView, SplashPresenter, ActivitySplashBinding>(),
    SplashView {
    companion object {
        const val SPORTS_CATEGORIES_BUNDLE = "sportsCategories"
    }

    private lateinit var splashScreen: SplashScreen

    override val binding: ActivitySplashBinding by viewBinding(ActivitySplashBinding::inflate)

    @Inject
    lateinit var presenter: SplashPresenter


    override fun initPresenter() {
        attachPresenter(presenter, this)
        presenter.init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        super.onCreate(savedInstanceState)
    }

    override fun proceedToMainActivity(sportsCategories: MutableList<SportsEventCategory>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putParcelableArrayListExtra(
            SPORTS_CATEGORIES_BUNDLE,
            sportsCategories as ArrayList<out Parcelable>
        )
        startActivity(intent)
        finish()
    }


}