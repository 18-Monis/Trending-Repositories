package com.example.trendingrepositories.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.BottomSheetTrendingRepositoriesFilterBinding
import com.example.trendingrepositories.common.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TrendingRepositoriesFilterBottomSheetDialog : BaseBottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTrendingRepositoriesFilterBinding
    private var checkedDateType = DateType.LAST_DAY
    var onDateTypeChecked: ((DateType) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return BottomSheetTrendingRepositoriesFilterBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            dateType = this@TrendingRepositoriesFilterBottomSheetDialog.dateType
        }
        handleDateTypeChanged()
        onSaveButtonClicked()
    }

    private fun onSaveButtonClicked() {
        binding.saveButton.setOnClickListener {
            onDateTypeChecked?.invoke(checkedDateType)
            dismiss()
        }
    }

    private fun handleDateTypeChanged() {
        binding.filterGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lastDayButton -> checkedDateType = DateType.LAST_DAY
                R.id.lastWeekButton -> checkedDateType = DateType.LAST_WEEK
                R.id.lastMonthButton -> checkedDateType = DateType.LAST_MONTH
            }
        }
    }

    private val dateType by lazy {
        arguments?.getParcelable(EXTRA_DATE_TYPE) ?: DateType.LAST_DAY
    }

    companion object {

        private val TAG = BottomSheetTrendingRepositoriesFilterBinding::class.java.toString()
        private val EXTRA_DATE_TYPE = TAG + "_DATE_TYPE_EXTRA"

        fun show(
            fragmentManager: FragmentManager,
            dateType: DateType
        ): TrendingRepositoriesFilterBottomSheetDialog {
            return TrendingRepositoriesFilterBottomSheetDialog().apply {
                arguments = bundleOf().apply {
                    putParcelable(EXTRA_DATE_TYPE, dateType)
                }
                show(fragmentManager, TAG)
            }
        }
    }

}