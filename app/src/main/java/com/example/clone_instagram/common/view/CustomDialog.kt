package com.example.clone_instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.clone_instagram.R
import com.example.clone_instagram.databinding.DialogCustomerBinding

class CustomDialog(context: Context) : Dialog(context) {

    private lateinit var binding : DialogCustomerBinding
    private lateinit var txtButtons : Array<TextView>
    private var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun addButton(vararg texts: Int,listener: View.OnClickListener) {
        txtButtons = Array(texts.size){
            TextView(context)
        }

        texts.forEachIndexed{index, txtId ->
            txtButtons[index].id = txtId
            txtButtons[index].setText(txtId)
            txtButtons[index].setOnClickListener {
                listener.onClick(it)
                dismiss()
            }
        }
    }

    override fun setTitle(titleId: Int) {
        this.titleId = titleId
    }

    override fun show() {
        super.show()

        with(binding){
            titleId?.let {
                dialogTitle.setText(it)
            }

            for (textView in txtButtons){
                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                layoutParams.setMargins(30,50,30,50)

                dialogContainer.addView(textView,layoutParams)
            }
        }


    }

}