package com.rivferd.formapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivferd.formapps.databinding.ActivityListParticipantBinding
import com.rivferd.formapps.util.ListParticipantAdapter
import com.rivferd.formapps.util.ParticipantList
import com.rivferd.formapps.util.ParticipantModel

class ListParticipantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListParticipantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListParticipantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvListParticipant.layoutManager = LinearLayoutManager(this)
        binding.rvListParticipant.adapter =
            ListParticipantAdapter(ParticipantList.getParticipants(ParticipantModel(this).selectAll()))
    }
}