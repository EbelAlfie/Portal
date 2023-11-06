package com.share.portal.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.share.portal.App
import com.share.portal.inject.ApplicationComponent
import com.share.portal.utils.ToastBuilder

abstract class ProgenitorActivity<V: ViewBinding>: AppCompatActivity() {
    lateinit var binding: V

    private val toast: ToastBuilder by lazy {
        ToastBuilder(this)
    }

    protected lateinit var applicationComponent: ApplicationComponent

    protected var activityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent = (application as App).getAppComponent()
        initComponent()
    }

    private fun initComponent() {
        initViews()
        onCreated()
    }

    private fun initViews() {
        binding = initBinding(layoutInflater)
        setContentView(binding.root)
    }

    protected open fun showToast(msg: String) = toast.show(msg)

    abstract fun onCreated()

    abstract fun initBinding(layoutInflater: LayoutInflater): V

}