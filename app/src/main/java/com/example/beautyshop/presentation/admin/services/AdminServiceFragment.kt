package com.example.beautyshop.presentation.admin.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.databinding.FragmentAdminServiceBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.admin.services.page.*
import com.example.beautyshop.presentation.dialogs.ITextDialogWithYesNo
import com.example.beautyshop.presentation.dialogs.TextDialogWithYesNo
import com.example.beautyshop.presentation.root.MainActivity

class AdminServiceFragment : Fragment() {
    private var _binding: FragmentAdminServiceBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[AdminServiceViewModel::class.java]
    }

    private val adapter: RenderAdapter<SectionModel> by lazy {
        RenderAdapter(
            RenderViewType.AdminSectionsViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    if (position < 0) {
                        onEditSection(position * -1)
                    }
                    if (position > 0) {
                        onShowMore(position)
                    }
                }
            })
    }

    private fun  onEditSection(position: Int) {
        (requireActivity() as MainActivity).onShowDialogFragment(
            PageSectionDialog(
                viewModel.onGetData().value!!.first { item -> item.id == position },
                object : PageSectionDialog.IPageSectionDialog {
                    override fun onEdit(sectionId: Int, sectionName: String) {
                        (requireActivity() as MainActivity).onShowDialogFragment(
                            TextDialogWithYesNo(
                                "Вы действительно хотите изменить секцию?",
                                object: ITextDialogWithYesNo {
                                    override fun onAccept() {
                                        viewModel.onEditSection(sectionId, sectionName)
                                    }
                                }
                            )
                        )
                    }

                    override fun onDelete(sectionId: Int) {
                        (requireActivity() as MainActivity).onShowDialogFragment(
                            TextDialogWithYesNo(
                                "Вы действительно хотите удалить секцию?",
                                object: ITextDialogWithYesNo {
                                    override fun onAccept() {
                                        viewModel.onRemoveSection(sectionId)
                                    }
                                }
                            )
                        )
                    }
                }
            )
        )
    }

    private fun onShowMore(position: Int) {
        (requireActivity() as MainActivity).onShowDialogFragment(
            SectionServicesDialog(
                viewModel.onGetData().value!!.first { item -> item.id == position },
                object : SectionServicesDialog.ISectionServicesDialog {
                    override fun onCreateService(sectionId: Int) {
                        (requireActivity() as MainActivity).onShowDialogFragment(
                            AddServiceDialog(
                                delegate = object :
                                    AddServiceDialog.IAddServiceDialog {
                                    override fun onAccept(
                                        serviceName: String,
                                        price: Float,
                                        time: Float,
                                        measurement: String
                                    ) {
                                        (requireActivity() as MainActivity).onShowDialogFragment(
                                            TextDialogWithYesNo(
                                                "Вы действительно хотите добавить услугу в секцию?",
                                                object : ITextDialogWithYesNo {
                                                    override fun onAccept() {
                                                        viewModel.onCreateService(
                                                            sectionId,
                                                            serviceName,
                                                            price,
                                                            time,
                                                            measurement
                                                        )
                                                    }
                                                }
                                            )
                                        )
                                    }
                                }
                            )
                        )
                    }

                    override fun onAddMaster(sectionId: Int) {
                        (requireActivity() as MainActivity).onShowDialogFragment(
                            AddWorkersDialog(
                                viewModel.onGetWorkers().value ?: mutableListOf(),
                                object : AddWorkersDialog.IAddWorkersDialog {
                                    override fun onAddMaster(userId: Int) {
                                        (requireActivity() as MainActivity).onShowDialogFragment(
                                            TextDialogWithYesNo(
                                                "Вы действительно хотите добавить мастера к секции?",
                                                object : ITextDialogWithYesNo {
                                                    override fun onAccept() {
                                                        viewModel.onAddMaster(
                                                            sectionId,
                                                            userId
                                                        )
                                                    }
                                                }
                                            )
                                        )
                                    }
                                }
                            )
                        )
                    }

                    override fun onEdit(serviceModel: ServiceModel) {
                        (requireActivity() as MainActivity).onShowDialogFragment(
                            AddServiceDialog(
                                serviceModel = serviceModel,
                                delegate = object :
                                    AddServiceDialog.IAddServiceDialog {
                                    override fun onAccept(
                                        serviceName: String,
                                        price: Float,
                                        time: Float,
                                        measurement: String
                                    ) {
                                        (requireActivity() as MainActivity).onShowDialogFragment(
                                            TextDialogWithYesNo(
                                                "Вы действительно хотите изменить услугу?",
                                                object : ITextDialogWithYesNo {
                                                    override fun onAccept() {
                                                        viewModel.onEditService(
                                                            serviceModel.id,
                                                            serviceName,
                                                            price,
                                                            time,
                                                            measurement
                                                        )
                                                    }
                                                }
                                            )
                                        )
                                    }
                                }
                            )
                        )
                    }

                    override fun onDelete(serviceId: Int) {
                        (requireActivity() as MainActivity).onShowDialogFragment(
                            TextDialogWithYesNo(
                                "Вы действительно хотите удалить услугу?",
                                object : ITextDialogWithYesNo {
                                    override fun onAccept() {
                                        viewModel.onDeleteService(serviceId)
                                    }
                                }
                            )
                        )
                    }

                }
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminServiceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSections.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        binding.btnCreateSection.setOnClickListener {
            (requireActivity() as MainActivity).onShowDialogFragment(AddSectionDialog(object :
                AddSectionDialog.IAddSectionDialog {
                override fun onAccept(sectionName: String) {
                    (requireActivity() as MainActivity).onShowDialogFragment(
                        TextDialogWithYesNo(
                            "Вы хотите создать секцию?",
                            object: ITextDialogWithYesNo {
                                override fun onAccept() {
                                    viewModel.onCreateSection(sectionName)
                                }
                            }
                        )
                    )
                }
            }))
        }
        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
    }
}