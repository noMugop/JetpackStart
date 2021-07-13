package ru.sumin.jetpackstart.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.sumin.jetpackstart.R
import ru.sumin.jetpackstart.databinding.FragmentGameFinishedBinding
import ru.sumin.jetpackstart.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var binding: FragmentGameFinishedBinding
    private lateinit var gameResult: GameResult

    private val args by navArgs<GameFinishedFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            goToStartGame()
        }
        with(gameResult) {
            val emojiResId = if (winner) {
                R.drawable.ic_smile
            } else {
                R.drawable.ic_sad
            }
            binding.emojiResult.setImageResource(emojiResId)
            binding.tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                countOfRightAnswers
            )
            binding.tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                gameSettings.minCountOfRightAnswers
            )
            binding.tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameSettings.minPercentOfRightAnswers
            )
            binding.tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                percentageOfRightAnswers
            )
        }
    }

    private fun parseArgs() {
        gameResult = args.gameResult
    }

    private fun goToStartGame() {
        findNavController().navigate(GameFinishedFragmentDirections.actionGameFinishedFragmentToChooseLevelFragment2())
    }

    companion object {

        private const val ARG_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GAME_RESULT, gameResult)
                }
            }
        }
    }
}
