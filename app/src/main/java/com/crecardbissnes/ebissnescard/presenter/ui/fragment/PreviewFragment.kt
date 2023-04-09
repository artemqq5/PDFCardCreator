package com.crecardbissnes.ebissnescard.presenter.ui.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crecardbissnes.ebissnescard.R
import com.crecardbissnes.ebissnescard.databinding.FragmentPreviewBinding
import com.crecardbissnes.ebissnescard.domain.MainNavigateTransition

class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentPreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bundle = null
        binding.nav = R.id.action_previewFragment_to_createFragment
        binding.mainTransition = requireActivity() as MainNavigateTransition

    }


}