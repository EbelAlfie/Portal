package com.share.portal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.share.portal.App
import com.share.portal.inject.ApplicationComponent

abstract class ProgenitorActivity<V: ViewBinding>: AppCompatActivity() {
    private lateinit var binding: V
    private lateinit var toast: Toast

    protected lateinit var applicationComponent: ApplicationComponent

    protected var activityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent = (application as App).getAppComponent()
        initComponent()
    }

    protected fun initComponent() {
        initViews()
        toast = Toast(this)
        onCreated()
    }

    private fun initViews() {
        binding = initBinding(layoutInflater)
        setContentView(binding.root)
    }

    protected open fun showToast(msg: String) {
        toast.setText(msg)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    abstract fun onCreated()

    abstract fun initBinding(layoutInflater: LayoutInflater): V

}