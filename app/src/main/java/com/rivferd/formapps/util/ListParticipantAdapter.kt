package com.rivferd.formapps.util

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivferd.formapps.databinding.ListParticipantItemBinding

class ListParticipantAdapter(private val participantList: ArrayList<Participant>) :
    RecyclerView.Adapter<ListParticipantAdapter.ParticipantViewHolder>() {

    inner class ParticipantViewHolder(val binding: ListParticipantItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val binding = ListParticipantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.binding.tvId.text = participantList[position].id
        holder.binding.tvName.text = participantList[position].name
        holder.binding.tvPhoneNumber.text = participantList[position].phoneNumber
        holder.binding.tvGender.text = participantList[position].gender
        holder.binding.tvLocation.text = participantList[position].location
        participantList[position].image.run {
            holder.binding.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(this, 0, this.size))
        }
    }

    override fun getItemCount(): Int {
        return participantList.size
    }

}