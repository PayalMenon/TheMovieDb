package themoviedb.android.example.com.themoviedb.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import themoviedb.android.example.com.themoviedb.Application;
import themoviedb.android.example.com.themoviedb.R;
import themoviedb.android.example.com.themoviedb.adapter.MovieListAdapter;
import themoviedb.android.example.com.themoviedb.model.MovieInfo;
import themoviedb.android.example.com.themoviedb.model.MovieList;
import themoviedb.android.example.com.themoviedb.services.MovieService;
import themoviedb.android.example.com.themoviedb.util.Constants;

public class MainActivity extends AppCompatActivity {

    @Inject
    MovieService service;

    private FragmentListener fragmentListener;
    private String movieType;
    @BindView(R.id.toolbar)
    Toolbar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((Application) getApplication()).getNetworkComponent().inject(this);

        if (savedInstanceState == null) {
            movieType = Constants.NOW_PLAYING;
        }

        setSupportActionBar(actionBar);

        addListFragment();
    }

    private void addListFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ListFragment fragment = (ListFragment) manager.findFragmentByTag(Constants.LIST_FRAGMENT);

        if (fragment == null) {

            fragment = new ListFragment();
            transaction.add(R.id.fragment_container, fragment, Constants.LIST_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constants.NOW_PLAYING.equals(movieType)) {
            Call<MovieList> call = service.getMovies(movieType);
            this.fragmentListener.getMovieList(call);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {

            FragmentManager manager = getSupportFragmentManager();

            ListFragment listFragment = (ListFragment) manager.findFragmentByTag(Constants.LIST_FRAGMENT);
            manager.beginTransaction().remove(listFragment).commit();
        }
    }

    public void setFragmentListener(FragmentListener listener) {

        this.fragmentListener = listener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_up_coming: {
                launchMovieList(Constants.UPCOMING);
                return true;
            }

            case R.id.action_now_playing: {
                launchMovieList(Constants.NOW_PLAYING);
                return true;
            }

            case R.id.action_popular: {
                launchMovieList(Constants.POPULAR);
                return true;
            }

            case R.id.action_top_rated: {
                launchMovieList(Constants.TOP_RATED);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchMovieList(String type) {
        Call<MovieList> call = service.getMovies(type);
        this.fragmentListener.getMovieList(call);
        movieType = type;
    }

    public interface FragmentListener {

        void getMovieList(Call<MovieList> call);

        void onItemClicked(int position);
    }
}
