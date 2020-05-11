package com.bismillah.quran.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bismillah.quran.R
import com.bismillah.quran.Settings
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.data.model.Sure
import com.bismillah.quran.ui.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private var sureList: List<Sure> = listOf()
    private var ayatList: List<Ayat> = listOf()
    private val settings: Settings by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!settings.isAppFirstLaunch()) {
            openMainActivity.invoke()
        } else {
            viewModel.getAllAyat()
            viewModel.ayatList.observe(this, Observer {
                ayatList = it
                viewModel.getAllSure()
            })
            viewModel.sureList.observe(this, Observer {
                sureList = it
                viewModel.updateAyatList(ayatList, sureList, openMainActivity)
            })
        }
    }

    private val openMainActivity = {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
