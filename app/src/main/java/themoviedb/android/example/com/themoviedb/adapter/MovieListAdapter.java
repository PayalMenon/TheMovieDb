package themoviedb.android.example.com.themoviedb.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import themoviedb.android.example.com.themoviedb.R;
import themoviedb.android.example.com.themoviedb.model.MovieInfo;
import themoviedb.android.example.com.themoviedb.util.Constants;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieInfo> movieList;

    public MovieListAdapter(Context context, List<MovieInfo> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MovieListAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieInfo info = movieList.get(position);
        holder.titleView.setText(info.getMovieTitle());
        holder.overviewView.setText(info.getMovieOverview());
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(context).load(Constants.IMAGE_URL + info.getMoviePoster())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.posterView);
        } else {
            Picasso.with(context).load(Constants.BACKDROP_IMAGE_URL + info.getMovieBackdrop())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.posterView);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView overviewView;
        private ImageView posterView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.itemTitle);
            overviewView = itemView.findViewById(R.id.itemOverview);
            posterView = itemView.findViewById(R.id.itemImage);
        }
    }

    public void updateDateSet(List<MovieInfo> list) {
        this.movieList = list;
    }
}
