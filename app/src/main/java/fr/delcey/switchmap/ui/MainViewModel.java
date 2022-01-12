package fr.delcey.switchmap.ui;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.delcey.switchmap.data.UserRepository;
import fr.delcey.switchmap.data.pet.PetEntity;
import fr.delcey.switchmap.data.user.UserEntity;

public class MainViewModel extends ViewModel {

    @NonNull
    private final UserRepository userRepository;

    // 0: Stock l'ID de l'utilisateur sélectionné (ou rien tant qu'on n'a pas cliqué sur un user)
    private final MutableLiveData<Long> selectedUserIdMutableLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 2: La liste des users nous est affichée, rien de bien compliqué ici !
    public LiveData<List<UserEntity>> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // 1: Comme toujours avec les LiveDatas, il faut qu'elles soient observées pour qu'elles s'activent et commencent leurs "réactions"
    //    Allez voir dans la MainActivity, on observe bien cette LiveData, c'est tout bon !
    public LiveData<List<PetEntity>> getAllPetsForSelectedUser() {
        // 4: ... ce qui va permettre de faire réagir le switchMap qui est basé sur cette MutableLiveData, et...
        return Transformations.switchMap(selectedUserIdMutableLiveData, new Function<Long, LiveData<List<PetEntity>>>() {
            @Override
            public LiveData<List<PetEntity>> apply(Long selectedUserId) {
                // 5: ... de pouvoir dire "hey, l'ID de l'utilisateur sélectionné a changé,
                //    j'ai besoin de faire une requête en base de donnée suivant cet ID justement !".
                //    Et dès qu'on a récupéré les animaux de compagnie de cet utilisateur, ils sont donnés automatiquement à la vue
                //    (regardez la signature de la méthode ligne 39 puis ligne 35 : on renvoie une LiveData<List<PetEntity>> !)
                return userRepository.getAllPetsByUserId(selectedUserId);
            }
        });
    }

    // 3: Ça devient intéressant, on a cliqué sur un user !
    //    On met à jour l'ID stocké dans notre petite MutableLiveData interne au ViewModel...
    public void onUserIdChanged(long userId) {
        selectedUserIdMutableLiveData.setValue(userId);
    }
}
