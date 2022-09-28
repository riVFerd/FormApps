package com.rivferd.formapps.util

import android.database.Cursor

object ParticipantList {
    fun getParticipants(dataFromDb: Cursor): ArrayList<Participant> {
        val participants = arrayListOf<Participant>()
        if (!dataFromDb.moveToFirst()) return participants
        while (!dataFromDb.isAfterLast) {
            participants.add(
                Participant(
                    dataFromDb.getInt(0).toString(),
                    dataFromDb.getString(1),
                    dataFromDb.getString(2),
                    dataFromDb.getString(3),
                    dataFromDb.getString(4),
                    dataFromDb.getBlob(5)
                )
            )
            dataFromDb.moveToNext()
        }
        return participants
    }
}

data class Participant(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val gender: String,
    val location: String,
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Participant

        if (id != other.id) return false
        if (name != other.name) return false
        if (phoneNumber != other.phoneNumber) return false
        if (gender != other.gender) return false
        if (location != other.location) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}