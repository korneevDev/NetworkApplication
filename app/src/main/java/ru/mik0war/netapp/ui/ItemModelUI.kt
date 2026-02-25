package ru.mik0war.netapp.ui

import android.view.View
import ru.mik0war.netapp.databinding.FragmentItemListBinding

sealed interface ItemUIState {

    fun show(binding: FragmentItemListBinding)

    data class Success(
        private val id: Int,
        private val name: String
    ): ItemUIState {
        override fun show(binding: FragmentItemListBinding) {
            binding.errorLayout.visibility = View.GONE

            binding.textId.text = "ID: $id"
            binding.textName.text = name
            binding.textName.visibility = View.VISIBLE
            binding.textId.visibility = View.VISIBLE
            binding.icon.visibility = View.VISIBLE
        }
    }

    data class Error(
        private val text: String
    ): ItemUIState {
        override fun show(binding: FragmentItemListBinding) {
            binding.textName.visibility = View.GONE
            binding.textId.visibility = View.GONE
            binding.icon.visibility = View.GONE

            binding.textError.text = text
            binding.errorLayout.visibility = View.VISIBLE
        }
    }
}