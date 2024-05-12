package com.it22564436.task.Task

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tasks_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val description: String,
    val priority: Int,
    val deadline: String,
):Parcelable

