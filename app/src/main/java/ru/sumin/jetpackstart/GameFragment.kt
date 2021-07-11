package ru.sumin.jetpackstart

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.sumin.jetpackstart.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    private lateinit var level: Level

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
        val tvAnswers = mutableListOf<TextView>()
        with(binding) {
            tvAnswers.add(tvOption1)
            tvAnswers.add(tvOption2)
            tvAnswers.add(tvOption3)
            tvAnswers.add(tvOption4)
            tvAnswers.add(tvOption5)
            tvAnswers.add(tvOption6)
        }
        for (textView in tvAnswers) {
            textView.setOnClickListener {
                viewModel.chooseAnswer(textView.text.toString().toInt())
            }
        }
        viewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
                val answers = it.answers.toList()
                tvOption1.text = answers[0].toString()
                tvOption2.text = answers[1].toString()
                tvOption3.text = answers[2].toString()
                tvOption4.text = answers[3].toString()
                tvOption5.text = answers[4].toString()
                tvOption6.text = answers[5].toString()
            }
        }
        viewModel.leftFormattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            Log.d("TEST_TEST", it.toString())
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughPercentage.observe(viewLifecycleOwner) {
            val colorResId = if (it) {
                android.R.color.holo_green_light
            } else {
                android.R.color.holo_red_light
            }
            val color = ContextCompat.getColor(requireContext(), colorResId)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
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

    companion object {

        private const val ARG_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_LEVEL, level)
                }
            }
        }
    }
}
