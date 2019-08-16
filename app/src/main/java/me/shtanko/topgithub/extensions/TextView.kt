package me.shtanko.topgithub.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Add a text changed listener to this TextView using the provided actions
 */
fun EditText.addTextChangedListener(
        beforeTextChanged: ((
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
        ) -> Unit)? = null,
        onTextChanged: ((
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
        ) -> Unit)? = null,
        afterTextChanged: ((text: Editable?) -> Unit)? = null
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged?.invoke(s)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke(text, start, count, after)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(text, start, before, count)
        }
    })
}