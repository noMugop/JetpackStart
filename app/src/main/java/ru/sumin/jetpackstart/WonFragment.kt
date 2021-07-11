package ru.sumin.jetpackstart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.sumin.jetpackstart.databinding.FragmentWonBinding

class WonFragment : Fragment() {

    private lateinit var binding: FragmentWonBinding
    private lateinit var gameResult: GameResult

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
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
        binding = FragmentWonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTest.text = "WonFragment: ${gameResult}"
    }

    companion object {

        fun newInstance(gameResult: GameResult): WonFragment {
            val fragment = WonFragment()
            fragment.gameResult = gameResult
            return fragment
        }
    }
}
