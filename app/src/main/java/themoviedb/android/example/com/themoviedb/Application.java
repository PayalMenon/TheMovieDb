package themoviedb.android.example.com.themoviedb;

import themoviedb.android.example.com.themoviedb.dragger.component.DaggerNetworkComponent;
import themoviedb.android.example.com.themoviedb.dragger.component.NetworkComponent;
import themoviedb.android.example.com.themoviedb.dragger.module.ApplicationModule;
import themoviedb.android.example.com.themoviedb.dragger.module.NetworkModule;

public class Application extends android.app.Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule("https://api.themoviedb.org/3/movie/"))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
