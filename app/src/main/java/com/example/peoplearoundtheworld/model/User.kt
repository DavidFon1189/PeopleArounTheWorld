package com.example.peoplearoundtheworld.model

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//Si existe un elemento con el mismo id lo remplaza
    fun insert(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAll(): LiveData<List<User>>

    @Delete
    fun delete(user: User)
}
