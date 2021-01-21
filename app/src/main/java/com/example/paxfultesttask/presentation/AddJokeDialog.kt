package com.example.paxfultesttask.presentation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.Toast
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.MyJoke
import kotlinx.android.synthetic.main.custom_dialog.*

class AddJokeDialog(context: Context) : Dialog(context) {

    var clickListener: OnDialogButtonClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.custom_dialog)
        cancelButton.setOnClickListener {
            clickListener?.onCancelButtonClick()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSymbols.setText(s?.length.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }

        dialogEditText.addTextChangedListener(textWatcher)

        saveButton.setOnClickListener {
            if (!dialogEditText.text.isNullOrEmpty()) {
                clickListener?.onSaveButtonClick(MyJoke(joke = dialogEditText.text.toString()))
            } else {
                Toast.makeText(context, "Empty edit text!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface OnDialogButtonClickListener {
        fun onCancelButtonClick()
        fun onSaveButtonClick(myJoke: MyJoke)
    }
}