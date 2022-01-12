package fr.delcey.switchmap.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.delcey.switchmap.MainApplication;
import fr.delcey.switchmap.data.AppDatabase;
import fr.delcey.switchmap.data.UserRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    private final Executor ioExecutor = Executors.newFixedThreadPool(4);

    private final UserRepository userRepository;

    private ViewModelFactory() {
        AppDatabase appDatabase = AppDatabase.getInstance(MainApplication.getApplication(), ioExecutor);

        userRepository = new UserRepository(
            appDatabase.getUserDao(),
            appDatabase.getPetDao()
        );
    }

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

