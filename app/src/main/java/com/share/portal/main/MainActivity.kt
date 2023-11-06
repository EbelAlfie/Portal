package com.share.portal.main

import android.view.LayoutInflater
import com.share.portal.base.ProgenitorActivity
import com.share.portal.databinding.ActivityMainBinding
import com.share.portal.main.adapter.FileAdapter
import javax.inject.Inject

class MainActivity : ProgenitorActivity<ActivityMainBinding>() {

    @Inject
    lateinit var viewModel: MainViewModel

    private val adapter: FileAdapter by lazy {
        FileAdapter(this)
    }

    override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
    override fun onCreated() {
        applicationComponent.inject(this)
        setupViews()
    }

    private fun setupViews() {

    }

}