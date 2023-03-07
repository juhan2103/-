package com.pronunu.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pronunu.mysololife.R
import com.pronunu.mysololife.board.BoardListViewAdapter
import com.pronunu.mysololife.board.BoardModel
import com.pronunu.mysololife.board.BoardWriteActivity
import com.pronunu.mysololife.contentsList.ContentModel
import com.pronunu.mysololife.databinding.FragmentTalkBinding
import com.pronunu.mysololife.utils.FBAuth
import com.pronunu.mysololife.utils.FBRef

class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    private val boardDataList = mutableListOf<BoardModel>()

    private val TAG = TalkFragment::class.java.simpleName

    private lateinit var boardAdapter : BoardListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        boardAdapter = BoardListViewAdapter(boardDataList)
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

        getFBBoardData()

        return binding.root

    }

    private fun getFBBoardData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){

                    Log.d(TAG, dataModel.toString())

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)

                }

                boardAdapter.notifyDataSetChanged()

                Log.d(TAG,boardDataList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)

    }


}