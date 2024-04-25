package com.fausto.designsystem.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fausto.designsystem.databinding.ScreenErrorBinding

class ErrorScreen(
    private val errorMessage: String, private val retryAction: () -> Unit
) : DialogFragment() {

    private var _binding: ScreenErrorBinding? = null
    private val binding: ScreenErrorBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ScreenErrorBinding.inflate(inflater, container, false).run {
            _binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isCancelable = false
        with(binding) {
            screenErrorText.text = errorMessage
            tryAgainButton.setOnClickListener {
                retryAction.invoke()
                dismiss()
            }
        }
    }
}