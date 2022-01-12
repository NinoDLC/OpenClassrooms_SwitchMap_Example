package fr.delcey.switchmap.data.pet;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PetDao {

    @Insert
    long insert(PetEntity petEntity);

    @Query("SELECT * FROM pet WHERE userId=:userId")
    LiveData<List<PetEntity>> getAllById(long userId);
}
