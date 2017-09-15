package themoviedb.android.example.com.themoviedb.dragger.component;

import javax.inject.Singleton;

import dagger.Component;
import themoviedb.android.example.com.themoviedb.dragger.module.ApplicationModule;
import themoviedb.android.example.com.themoviedb.dragger.module.NetworkModule;
import themoviedb.android.example.com.themoviedb.ui.ListFragment;
import themoviedb.android.example.com.themoviedb.ui.MainActivity;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject(ListFragment fragment);
    void inject(MainActivity activity);
}
