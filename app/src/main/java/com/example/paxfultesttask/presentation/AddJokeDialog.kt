package com.example.paxfultesttask.presentation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import com.example.paxfultesttask.R
import kotlinx.android.synthetic.main.custom_dialog.*

class AddJokeDialog(
    context: Context,
    private val clickListener: OnDialogButtonClickListener
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.custom_dialog)
        cancelButton.setOnClickListener {
            clickListener.onCancelButtonClick(this)
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
            clickListener.onSaveButtonClick(this)
        }
    }

    interface OnDialogButtonClickListener {
        fun onCancelButtonClick(dialog: AddJokeDialog)
        fun onSaveButtonClick(dialog: AddJokeDialog)
    }
}