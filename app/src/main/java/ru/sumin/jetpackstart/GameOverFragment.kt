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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack("GameFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                remove()
            }
        })
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
        binding.tvTest.text = "GameOverFragment: ${gameResult}"
    }

    companion object {

        fun newInstance(gameResult: GameResult): GameOverFragment {
            val fragment = GameOverFragment()
            fragment.gameResult = gameResult
            return fragment
        }
    }
}
