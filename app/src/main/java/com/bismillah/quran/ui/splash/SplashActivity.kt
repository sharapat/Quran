package com.bismillah.quran.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bismillah.quran.R
import com.bismillah.quran.data.model.Ayat
import com.bismillah.quran.data.model.Sure
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private var sureList: List<Sure> = listOf()
    private var ayatList: List<Ayat> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.getAllAyat()
        viewModel.ayatList.observe(this, Observer {
            ayatList = it
            viewModel.getAllSure()
        })
        viewModel.sureList.observe(this, Observer {
            sureList = it
        })
    }
}
