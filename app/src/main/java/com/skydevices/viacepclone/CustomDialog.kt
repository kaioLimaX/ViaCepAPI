package com.skydevices.viacepclone

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog

import com.skydevices.viacepclone.databinding.CustomDialogBinding

class CustomDialog(context: Context) : AppCompatDialog(context) {



    val binding by lazy {
        CustomDialogBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSair.setOnClickListener {
            dismiss()
        }
    }

    fun setLocalidade(title: String) {
        binding.txtLocalidade.text = title
    }

    fun setRua(message: String) {
        binding.txtRua.text = message
    }
    fun setCep(message: String) {
        binding.txtCep.text = message
    }
    fun setIbge(message: String) {
        binding.tvtIbge.text = message
    }
}