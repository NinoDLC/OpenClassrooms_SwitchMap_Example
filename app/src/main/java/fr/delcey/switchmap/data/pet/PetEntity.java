package fr.delcey.switchmap.data.pet;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

import fr.delcey.switchmap.data.user.UserEntity;

@Entity(tableName = "pet",
    foreignKeys = @ForeignKey(
        entity = UserEntity.class,
        parentColumns = "id",
        childColumns = "userId"
    ))
public class PetEntity {
    @PrimaryKey(autoGenerate = true)
    private final long id;

    @ColumnInfo(index = true)
    private final long userId;

    @NonNull
    private final String petName;

    @Ignore
    public PetEntity(long userId, @NonNull String petName) {
        this(0, userId, petName);
    }

    /**
     * Don't use this constructor, this is for Room / UTs only. Room ain't so good in Java with immutability.
     */
    @VisibleForTesting
    public PetEntity(long id, long userId, @NonNull String petName) {
        this.id = id;
        this.userId = userId;
        this.petName = petName;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    @NonNull
    public String getPetName() {
        return petName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetEntity petEntity = (PetEntity) o;
        return id == petEntity.id && userId == petEntity.userId && petName.equals(petEntity.petName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, petName);
    }

    @NonNull
    @Override
    public String toString() {
        return "PetEntity{" +
            "id=" + id +
            ", userId=" + userId +
            ", petName='" + petName + '\'' +
            '}';
    }
}
