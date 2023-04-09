package com.crecardbissnes.ebissnescard.presenter.ui.fragment


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crecardbissnes.ebissnescard.BuildConfig.APPLICATION_ID
import com.crecardbissnes.ebissnescard.R
import com.crecardbissnes.ebissnescard.databinding.FragmentCreateBinding
import com.crecardbissnes.ebissnescard.domain.MainNavigateTransition
import com.crecardbissnes.ebissnescard.domain.models.BusinessCardModel
import com.crecardbissnes.ebissnescard.presenter.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class CreateFragment : Fragment(), TextWatcher {

    private lateinit var binding: FragmentCreateBinding
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nav = R.id.action_createFragment_to_resultFragment
        binding.bundle = null
        binding.mainTransition = requireActivity() as MainNavigateTransition


        mainViewModel.pdfInfo.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_createFragment_to_resultFragment,
                bundleOf(
                    "namePDF" to it
                )
            )
        }

        mainViewModel.cardData.observe(viewLifecycleOwner) {
            val name = "business_card_${UUID.randomUUID()}.pdf"
            mainViewModel.createPDFDocument(name, it)
        }


        binding.createButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) createButtonAction()
                else requestPermissionLauncher.launch(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        binding.textFieldName.editText?.addTextChangedListener(this)
        binding.textFieldLastName.editText?.addTextChangedListener(this)
        binding.textFieldPhone.editText?.addTextChangedListener(this)
        binding.textFieldWorkPosition.editText?.addTextChangedListener(this)

    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        binding.createButton.isEnabled = checkValidation(binding.textFieldName) &&
                checkValidation(binding.textFieldLastName) &&
                checkValidation(binding.textFieldPhone) &&
                checkValidation(binding.textFieldWorkPosition)
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    private fun checkValidation(inputLayout: TextInputLayout): Boolean {
        return if (!inputLayout.editText!!.text.isNullOrEmpty() && inputLayout.editText!!.text.length <= 15) {
            inputLayout.helperText = null
            true
        } else {
            inputLayout.helperText = "must not be empty and not over " +
                    "${if (inputLayout == binding.textFieldWorkPosition) "26" else "16"} words"
            false
        }
    }

    private val dialogPermissions by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Permission Dialog")
            .setMessage("To continue, you need to grant permission (read smartphone memory) in the app settings")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
                requestPermissions()
            }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                createButtonAction()
            } else {
                dialogPermissions.show()
            }
        }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) createButtonAction()
            else {
                val intent = Intent(
                    ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:$APPLICATION_ID")
                )
                startActivity(intent)
            }

        } else when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) -> createButtonAction()

        }
    }

    private fun createButtonAction() {
        mainViewModel.setNewCardModel(
            BusinessCardModel(
                binding.textFieldName.editText!!.text.toString(),
                binding.textFieldLastName.editText!!.text.toString(),
                binding.textFieldPhone.editText!!.text.toString(),
                binding.textFieldWorkPosition.editText!!.text.toString()
            )
        )
    }


}