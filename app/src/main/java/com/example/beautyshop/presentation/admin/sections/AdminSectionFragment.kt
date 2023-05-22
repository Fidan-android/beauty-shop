package com.example.beautyshop.presentation.admin.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.databinding.FragmentAdminSectionBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.admin.sections.add_section.AddSectionDialog
import com.example.beautyshop.presentation.admin.sections.page.PageSectionDialog
import com.example.beautyshop.presentation.root.MainActivity

class AdminSectionFragment : Fragment() {
    private var _binding: FragmentAdminSectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[AdminSectionViewModel::class.java]
    }

    private val adapter: RenderAdapter<SectionModel> by lazy {
        RenderAdapter(
            RenderViewType.AdminSectionsViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    (requireActivity() as MainActivity).onShowDialogFragment(
                        PageSectionDialog(
                            viewModel.onGetData().value!!.first { item -> item.id == position },
                            object : PageSectionDialog.IPageSectionDialog {
                                override fun onEdit(sectionId: Int, sectionName: String) {
                                    viewModel.onEditSection(sectionId, sectionName)
                                }

                                override fun onDelete(sectionId: Int) {
                                    viewModel.onRemoveSection(sectionId)
                                }
                            }
                        )
                    )
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminSectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSections.adapter = adapter
        viewModel.onLoadData()
    }

    override fun onStart() {
        super.onStart()

        binding.btnCreateSection.setOnClickListener {
            (requireActivity() as MainActivity).onShowDialogFragment(AddSectionDialog(object :
                AddSectionDialog.IAddSectionDialog {
                override fun onAccept(sectionName: String) {
                    viewModel.onCreateSection(sectionName)
                }
            }))
        }

        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
    }
}