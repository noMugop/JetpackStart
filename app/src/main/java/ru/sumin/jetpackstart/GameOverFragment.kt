package ru.sumin.jetpackstart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.sumin.jetpackstart.databinding.FragmentGameOverBinding

class GameOverFragment : Fragment() {

    private lateinit var binding: FragmentGameOverBinding
    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goToStartGame()
                remove()
            }
        })
        binding.buttonRetry.setOnClickListener {
            goToStartGame()
        }
        with(gameResult) {
            binding.tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                countOfRightAnswers,
                gameSettings.minCountOfRightAnswers
            )
            binding.tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                percentageOfRightAnswers,
                gameSettings.minPercentOfRightAnswers
            )
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(ARG_GAME_RESULT)) {
            throw RuntimeException("$this must contain argument $ARG_GAME_RESULT")
        }
        gameResult = args.getParcelable(ARG_GAME_RESULT) ?: throw RuntimeException(
            "GameResult == null"
        )
    }

    private fun goToStartGame() {
        activity?.supportFragmentManager?.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {

        private const val ARG_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameOverFragment {
            return GameOverFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GAME_RESULT, gameResult)
                }
            }
        }
    }
}
