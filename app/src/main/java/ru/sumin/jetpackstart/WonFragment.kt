package ru.sumin.jetpackstart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.sumin.jetpackstart.databinding.FragmentWonBinding

class WonFragment : Fragment() {

    private lateinit var binding: FragmentWonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWonBinding.inflate(inflater, container, false)
        return binding.root
    }
}
