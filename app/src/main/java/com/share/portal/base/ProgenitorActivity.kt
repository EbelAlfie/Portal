package com.share.portal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ProgenitorActivity<V: ViewBinding>: AppCompatActivity() {
    private lateinit var binding: V
    private lateinit var toast: Toast

    protected var activityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result -> manageResult(result) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
    }

    private fun initComponent() {
        initViews()
        toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        setViewActions()
    }

    private fun initViews() {
        binding = initBinding(layoutInflater)
        setContentView(binding.root)
    }

    abstract fun setViewActions()

    protected open fun showToast(msg: String) {
        toast.setText(msg)
        toast.show()
    }

    abstract fun initBinding(layoutInflater: LayoutInflater): V

    abstract fun manageResult(result: ActivityResult)
}