package fr.delcey.switchmap.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.delcey.switchmap.data.pet.PetEntity;
import fr.delcey.switchmap.data.user.UserDao;
import fr.delcey.switchmap.data.user.UserEntity;
import fr.delcey.switchmap.data.pet.PetDao;

public class UserRepository {

    @NonNull
    private final UserDao userDao;

    @NonNull
    private final PetDao petDao;

    public UserRepository(@NonNull UserDao userDao, @NonNull PetDao petDao) {
        this.userDao = userDao;
        this.petDao = petDao;
    }

    /**
     * @return All users stored in database
     */
    public LiveData<List<UserEntity>> getAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * @return All the pets for this user (depending on its userId)
     */
    public LiveData<List<PetEntity>> getAllPetsByUserId(long userId) {
        return petDao.getAllById(userId);
    }
}
