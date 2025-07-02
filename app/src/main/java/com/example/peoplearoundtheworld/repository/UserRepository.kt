package com.example.peoplearoundtheworld.repository

import androidx.lifecycle.LiveData
import com.example.peoplearoundtheworld.data.RestDataSource
import com.example.peoplearoundtheworld.model.User
import com.example.peoplearoundtheworld.model.UserDao
import javax.inject.Inject

interface UserRepository {
    suspend fun  getNewUser(): User
    suspend fun deleteUser(toDelete: User)
    fun getAllUser(): LiveData<List<User>>
}

class UserRespositoryImp @Inject constructor(
    private val dataSource: RestDataSource,
    private val userDao: UserDao
): UserRepository {
    override suspend fun getNewUser(): User {

        val name = dataSource.getUserName().results[0].name!!
        val location = dataSource.getUserLocation().results[0].location!!
        val picture = dataSource.getUserPicture().results[0].picture
        val user = User(name.first, name.last, location.city, picture.thumbnail)
        userDao.insert(user)
        return  user
    }

    override suspend fun deleteUser(toDelete: User) = userDao.delete(toDelete)

    override fun getAllUser(): LiveData<List<User>> = userDao.getAll()

}