package com.example.beautyshop.presentation.master.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.conventions.SharedKeys
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.databinding.FragmentAdminProfileBinding
import com.example.beautyshop.databinding.FragmentMasterProfileBinding
import com.example.beautyshop.helper.copyInputStreamToFile
import com.example.beautyshop.helper.removeShared
import com.example.beautyshop.helper.toIso
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.auth.LoginActivity
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.InputStream
import java.util.*

class MasterProfileFragment : Fragment() {

    private var _binding: FragmentMasterProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[MasterProfileViewModel::class.java]
    }
    private var galleryPermissionResultLauncher: ActivityResultLauncher<String>? = null
    private var openPictureResultLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryPermissionResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            galleryPermissionResultCallback
        )
        openPictureResultLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            openPictureResultCallback
        )
    }
    private val adapter: RenderAdapter<AppointmentModel> by lazy {
        RenderAdapter(
            RenderViewType.MasterAppointmentsViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {

                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMasterProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAppointments.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        binding.logOut.setOnClickListener {
            requireContext().removeShared(SharedKeys.AccessToken)

            requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.imageProfile.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                galleryPermissionResultLauncher?.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        viewModel.onGetData().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.nameProfile.text =
                    it.firstName + " " + it.name
                Glide
                    .with(requireContext())
                    .load(
                        GlideUrl(
                            "http://a91745zj.beget.tech/api/v1/img/" + it.image,
                            LazyHeaders.Builder()
                                .addHeader("User-Agent", "Mozilla/5.0")
                                .build()
                        )
                    )
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .error(R.drawable.sample_avatar)
                    .into(binding.imageProfile)
            }
        }
        viewModel.onGetAppointments().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
        viewModel.onGetIsError().observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.onLoadData()
    }

    private val galleryPermissionResultCallback = ActivityResultCallback<Boolean> {
        if (it) {
            openPictureResultLauncher?.launch("*/*")
        }
    }

    private val openPictureResultCallback = ActivityResultCallback<Uri?> {
        if (it != null) {
            val openIS = requireContext().contentResolver.openInputStream(it)
                ?: return@ActivityResultCallback
            val realPath = writeStreamToDisk(
                context = requireContext(),
                inputStream = openIS,
                mimeType = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(requireContext().contentResolver.getType(it)) ?: ""
            )
            viewModel.changeImageProfile(realPath.absolutePath)
        }
    }

    private fun writeStreamToDisk(
        context: Context,
        inputStream: InputStream,
        mimeType: String
    ): File {
        val name = "${Date().toIso().replace(":", "-")}-${System.currentTimeMillis()}.$mimeType"
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val pathBuilder = StringBuilder()
        pathBuilder.append("$dir/$name")
        val futureStudioIconFile = File(pathBuilder.toString())
        if (!futureStudioIconFile.exists()) {
            futureStudioIconFile.createNewFile()
        }
        futureStudioIconFile.copyInputStreamToFile(inputStream)
        return futureStudioIconFile
    }
}