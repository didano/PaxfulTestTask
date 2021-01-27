package com.example.paxfultesttask.presentation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.core.widget.doAfterTextChanged
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

        dialogEditText.doAfterTextChanged {
            currentSymbols.text = it?.length.toString()
        }

        saveButton.setOnClickListener {
            clickListener.onSaveButtonClick(this)
        }
    }

    interface OnDialogButtonClickListener {
        fun onCancelButtonClick(dialog: AddJokeDialog)
        fun onSaveButtonClick(dialog: AddJokeDialog)
    }
}