package com.share.portal.main

import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import com.share.portal.base.ProgenitorActivity
import com.share.portal.databinding.ActivityMainBinding

class MainActivity : ProgenitorActivity<ActivityMainBinding>() {
    override fun setViewActions() {}

    override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun manageResult(result: ActivityResult) {}

}