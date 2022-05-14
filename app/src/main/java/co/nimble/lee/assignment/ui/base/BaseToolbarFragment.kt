package co.nimble.lee.assignment.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import co.nimble.lee.assignment.R

abstract class BaseToolbarFragment<VB : ViewBinding> : BaseFragment<VB>() {

    private val toolbar: Toolbar
        get() = binding.root.findViewById(R.id.toolbar)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
