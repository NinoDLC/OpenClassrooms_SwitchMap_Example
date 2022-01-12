package fr.delcey.switchmap.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;

import fr.delcey.switchmap.data.user.UserDao;
import fr.delcey.switchmap.data.user.UserEntity;
import fr.delcey.switchmap.data.pet.PetDao;
import fr.delcey.switchmap.data.pet.PetEntity;

@Database(
    entities = {
        UserEntity.class,
        PetEntity.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(
        @NonNull Application application,
        @NonNull Executor ioExecutor
    ) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = create(application, ioExecutor);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase create(
        @NonNull Application application,
        @NonNull Executor ioExecutor
    ) {
        Builder<AppDatabase> builder = Room.databaseBuilder(
            application,
            AppDatabase.class,
            DATABASE_NAME
        );

        builder.addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                ioExecutor.execute(() -> {
                    UserDao userDao = AppDatabase.getInstance(application, ioExecutor).getUserDao();
                    long cindyId = userDao.insert(new UserEntity("Cindy"));
                    long ninoId = userDao.insert(new UserEntity("Nino"));
                    long dimitryId = userDao.insert(new UserEntity("Dimitri"));

                    PetDao petDao = AppDatabase.getInstance(application, ioExecutor).getPetDao();
                    petDao.insert(new PetEntity(cindyId, "Vladimir"));
                    petDao.insert(new PetEntity(ninoId, "Noxxi"));
                    petDao.insert(new PetEntity(ninoId, "Satan"));
                    petDao.insert(new PetEntity(ninoId, "Sakura"));
                    petDao.insert(new PetEntity(dimitryId, "Loki"));
                });
            }
        });

        return builder.build();
    }

    public abstract PetDao getPetDao();

    public abstract UserDao getUserDao();

}
