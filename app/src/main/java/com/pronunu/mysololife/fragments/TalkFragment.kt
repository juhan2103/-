package com.pronunu.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.pronunu.mysololife.R
import com.pronunu.mysololife.board.BoardListViewAdapter
import com.pronunu.mysololife.board.BoardModel
import com.pronunu.mysololife.board.BoardWriteActivity
import com.pronunu.mysololife.databinding.FragmentTalkBinding

class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        val boardList = mutableListOf<BoardModel>()
        boardList.add(BoardModel("a", "b", "c", "d"))

        val boardAdapter = BoardListViewAdapter(boardList)
        binding.boardListView.adapter = boardAdapter

        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }
        return binding.root
    }


}