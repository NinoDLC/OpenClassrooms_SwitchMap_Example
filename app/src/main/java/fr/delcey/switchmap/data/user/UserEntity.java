package fr.delcey.switchmap.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "user")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private final long id;

    @NonNull
    private final String userName;

    @Ignore
    public UserEntity(@NonNull String userName) {
        this(0, userName);
    }

    /**
     * Don't use this constructor, this is for Room / UTs only. Room ain't so good in Java with immutability.
     */
    @VisibleForTesting
    public UserEntity(long id, @NonNull String userName) {
        this.id = id;
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    @Override
    public String toString() {
        return "PersonEntity{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }
}
