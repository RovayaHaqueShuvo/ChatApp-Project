package com.own_world.chatapp

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.own_world.chatapp.Adapter.MassageAdapter
import com.own_world.chatapp.Model.Massage
import com.own_world.chatapp.Unit.CHATS
import com.own_world.chatapp.Unit.MASSAGE
import com.own_world.chatapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var massageAdapter: MassageAdapter
    private lateinit var massageList: ArrayList<Massage>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")

        supportActionBar?.title = name

        massageList = ArrayList()
        massageAdapter = MassageAdapter(this, massageList)

        binding.chatrecycler.layoutManager = LinearLayoutManager(this)
        binding.chatrecycler.adapter = massageAdapter

        // adding logic for the recyclerview
        mDbRef = FirebaseDatabase.getInstance().getReference()



        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiveruid + senderUid
        receiverRoom = senderUid + receiveruid

        //adding massage to database
        binding.sendbutton.setOnClickListener {
            val massage = binding.massageBox.text.toString()
            val massageObject = Massage(massage, senderUid.toString())

            mDbRef.child(CHATS).child(senderRoom!!).child(MASSAGE).push()
                .setValue(massageObject).addOnSuccessListener {
                    mDbRef.child(CHATS).child(receiverRoom!!).child(MASSAGE).push()
                        .setValue(massageObject)
                }
            binding.massageBox.setText("")


        }

        mDbRef.child(CHATS).child(senderRoom!!).child(MASSAGE)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    massageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val massage = postSnapshot.getValue(Massage::class.java)
                        massageList.add(massage!!)
                    }
                    massageAdapter.notifyDataSetChanged()
                    binding.chatrecycler.scrollToPosition(massageAdapter.itemCount - 1)

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}