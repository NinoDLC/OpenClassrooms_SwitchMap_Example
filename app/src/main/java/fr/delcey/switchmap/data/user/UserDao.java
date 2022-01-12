package fr.delcey.switchmap.data.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insert(UserEntity userEntity);

    @Query("SELECT * FROM user")
    LiveData<List<UserEntity>> getAllUsers();
}
