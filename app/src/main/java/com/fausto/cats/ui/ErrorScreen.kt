package com.fausto.cats.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fausto.cats.R
import com.fausto.cats.databinding.ScreenErrorBinding

class ErrorScreen(
    private val retryAction: () -> Unit
) : DialogFragment() {

    private var _binding: ScreenErrorBinding? = null
    private val binding: ScreenErrorBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return ScreenErrorBinding.inflate(inflater, container, false).run {
//            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            _binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tryAgainButton.setOnClickListener {
            retryAction.invoke()
            this.dismiss()
        }
    }
}