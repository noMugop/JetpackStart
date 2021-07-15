package ru.sumin.jetpackstart.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.sumin.jetpackstart.databinding.FragmentGameBinding
import ru.sumin.jetpackstart.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    private lateinit var level: Level
    private var optionsTextViews = mutableListOf<TextView>()
    private val args by navArgs<GameFragmentArgs>()

    private val goodColor by lazy {
        val colorResId = android.R.color.holo_green_light
        ContextCompat.getColor(requireContext(), colorResId)
    }

    private val badColor by lazy {
        val colorResId = android.R.color.holo_red_light
        ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(
                requireActivity().application
            )
        )[GameViewModel::class.java]

        getTextViewsOptions()
        setupClickListenersToOptions()
        observeViewModel()
        if (savedInstanceState == null) {
            viewModel.startGame(level)
        }
    }

    private fun parseArgs() {
        level = args.level
    }

    private fun getTextViewsOptions() {
        with(binding) {
            optionsTextViews.add(tvOption1)
            optionsTextViews.add(tvOption2)
            optionsTextViews.add(tvOption3)
            optionsTextViews.add(tvOption4)
            optionsTextViews.add(tvOption5)
            optionsTextViews.add(tvOption6)
        }
    }

    private fun setupClickListenersToOptions() {
        for (textView in optionsTextViews) {
            textView.setOnClickListener {
                viewModel.chooseAnswer(textView.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
                setupTextToOptions(it.answers)
            }
        }
        viewModel.leftFormattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            val action = GameFragmentDirections.actionGameFragmentToGameFinishedFragment(it)
            findNavController().navigate(action)
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughPercentage.observe(viewLifecycleOwner) {
            setupProgressColorByState(it)
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            setupTextColorByState(it)
        }
        viewModel.minPercentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.rightAnswersProgress.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun setupTextToOptions(answers: List<Int>) {
        for (i in answers.indices) {
            optionsTextViews[i].text = answers[i].toString()
        }
    }

    private fun setupTextColorByState(enoughRightAnswers: Boolean) {
        val color = getColorByState(enoughRightAnswers)
        binding.tvAnswersProgress.setTextColor(color)
    }

    private fun setupProgressColorByState(enoughPercentage: Boolean) {
        val color = getColorByState(enoughPercentage)
        binding.progressBar.progressTintList = ColorStateList.valueOf(color)
    }

    private fun getColorByState(isGoodState: Boolean): Int {
        return if (isGoodState) {
            goodColor
        } else {
            badColor
        }
    }
}
