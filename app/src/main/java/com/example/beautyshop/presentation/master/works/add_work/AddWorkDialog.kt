package com.example.beautyshop.presentation.master.works.add_work

import android.Manifest
import android.content.Context
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
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.databinding.DialogAddWorkBinding
import com.example.beautyshop.databinding.DialogAddWorkerBinding
import com.example.beautyshop.helper.copyInputStreamToFile
import com.example.beautyshop.helper.toIso
import java.io.File
import java.io.InputStream
import java.util.*


class AddWorkDialog(
    private val delegate: IAddWorkDialog
) : DialogFragment() {

    interface IAddWorkDialog {
        fun onAccept(path: String, description: String)
    }

    private var _binding: DialogAddWorkBinding? = null
    private val binding get() = _binding!!
    private var galleryPermissionResultLauncher: ActivityResultLauncher<String>? = null
    private var openPictureResultLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        galleryPermissionResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            galleryPermissionResultCallback
        )
        openPictureResultLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            openPictureResultCallback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        _binding = DialogAddWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnAddWork.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                galleryPermissionResultLauncher?.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
        binding.btnAddWork.setOnClickListener(null)
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
            delegate.onAccept(realPath.absolutePath, binding.etDescription.text.toString())
            dismissAllowingStateLoss()
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