package com.pronunu.mysololife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.pronunu.mysololife.R
import com.pronunu.mysololife.databinding.ActivityBoardInsideBinding
import com.pronunu.mysololife.utils.FBRef

class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardInsideBinding

    private val TAG = BoardInsideActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        // 첫 번째 방법
//        val title = intent.getStringExtra("title").toString()
//        val content = intent.getStringExtra("content").toString()
//        val time = intent.getStringExtra("time").toString()
//
//        binding.titleArea.text = title
//        binding.textArea.text = content
//        binding.timeArea.text = time

//        Log.d(TAG, title)
//        Log.d(TAG, content)
//        Log.d(TAG, time)

        // 두 번째 방법

        val key = intent.getStringExtra("key")
        getBoardData(key.toString())
        getImageData(key.toString())

    }

    private fun getImageData(key : String){
        // Reference to an image file in Cloud Storage
        val storageReference =  Firebase.storage.reference.child(key + ".jpeg")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener( {task ->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            }
            else{

            }
        })
    }

    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                Log.d(TAG, dataModel!!.title)

                binding.titleArea.text = dataModel!!.title
                binding.textArea.text = dataModel!!.content
                binding.timeArea.text = dataModel!!.time

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}