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
import ru.sumin.jetpackstart.R
import ru.sumin.jetpackstart.databinding.FragmentGameBinding
import ru.sumin.jetpackstart.domain.Level
import java.util.function.BinaryOperator

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    private lateinit var level: Level
    private var tvAnswers = mutableListOf<TextView>()

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
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        getTextViewsOptions()
        setupClickListenersToOptions()
        observeViewModel()
        if (savedInstanceState == null) {
            viewModel.startGame(level)
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(ARG_LEVEL)) {
            throw RuntimeException("Required param level is absent")
        }
        args.getParcelable<Level>(ARG_LEVEL)?.let {
            level = it
        }
    }

    private fun getTextViewsOptions() {
        with(binding) {
            tvAnswers.add(tvOption1)
            tvAnswers.add(tvOption2)
            tvAnswers.add(tvOption3)
            tvAnswers.add(tvOption4)
            tvAnswers.add(tvOption5)
            tvAnswers.add(tvOption6)
        }
    }

    private fun setupClickListenersToOptions() {
        for (textView in tvAnswers) {
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
                setupAnswersToTextViews(it.answers)
            }
        }
        viewModel.leftFormattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            val fragment = if (it.winner) {
                WonFragment.newInstance(it)
            } else {
                GameOverFragment.newInstance(it)
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughPercentage.observe(viewLifecycleOwner) {
            setupProgressColorByState(it)
        }
        viewModel.minPercentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
    }

    private fun setupAnswersToTextViews(answers: List<Int>) {
        for (i in answers.indices) {
            tvAnswers[i].text = answers[i].toString()
        }
    }

    private fun setupProgressColorByState(enoughPercentage: Boolean) {
        val colorResId = if (enoughPercentage) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        val color = ContextCompat.getColor(requireContext(), colorResId)
        binding.progressBar.progressTintList = ColorStateList.valueOf(color)
    }

    companion object {

        private const val ARG_LEVEL = "level"
        const val NAME = "GameFragment"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_LEVEL, level)
                }
            }
        }
    }
}
