package com.share.portal.view.general

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.share.portal.App
import com.share.portal.view.dinject.ApplicationComponent
import com.share.portal.view.utils.ToastBuilder

abstract class ProgenitorActivity<V: ViewBinding>: AppCompatActivity() {
    lateinit var binding: V

    private val toast: ToastBuilder by lazy {
        ToastBuilder(this)
    }

    protected val applicationComponent: ApplicationComponent by lazy {
        (application as App).getAppComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    protected open fun showToast(
        msg: String?,
        gravity: Int = Gravity.CENTER
    ) = msg?.let { toast.show(it, gravity) }

    abstract fun onCreated()

    abstract fun initBinding(layoutInflater: LayoutInflater): V

}