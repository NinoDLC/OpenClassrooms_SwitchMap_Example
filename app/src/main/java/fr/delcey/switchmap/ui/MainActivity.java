package fr.delcey.switchmap.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.delcey.switchmap.R;
import fr.delcey.switchmap.data.pet.PetEntity;
import fr.delcey.switchmap.data.user.UserEntity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MainViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        RecyclerView usersRecyclerView = findViewById(R.id.main_rv_users);
        UserAdapter userAdapter = new UserAdapter(new UserAdapter.OnUserClickedListener() {
            @Override
            public void onUserClicked(long userId) {
                viewModel.onUserIdChanged(userId);
            }
        });
        usersRecyclerView.setAdapter(userAdapter);
        viewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                userAdapter.submitList(userEntities);
            }
        });

        RecyclerView petsRecyclerView = findViewById(R.id.main_rv_pets);
        PetAdapter petAdapter = new PetAdapter();
        petsRecyclerView.setAdapter(petAdapter);
        viewModel.getAllPetsForSelectedUser().observe(this, new Observer<List<PetEntity>>() {
            @Override
            public void onChanged(List<PetEntity> petEntities) {
                petAdapter.submitList(petEntities);
            }
        });
    }
}