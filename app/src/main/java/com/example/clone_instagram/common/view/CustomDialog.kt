package com.example.clone_instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.clone_instagram.R

class CustomDialog(context: Context) : Dialog(context) {


    private lateinit var dialogLinearLayou: LinearLayout
    private lateinit var txtButtons : Array<TextView>
    private lateinit var txtTitle : TextView


    private var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_customer)

        dialogLinearLayou = findViewById(R.id.dialog_container)
        txtTitle = findViewById(R.id.dialog_title)
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

        titleId?.let {
            txtTitle.setText(it)
        }

        for (textView in txtButtons){
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(30,50,30,50)

            dialogLinearLayou.addView(textView,layoutParams)
        }
    }

}