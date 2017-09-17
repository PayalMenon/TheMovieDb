package themoviedb.android.example.com.themoviedb.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.CardView;
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
import themoviedb.android.example.com.themoviedb.ui.MainActivity;
import themoviedb.android.example.com.themoviedb.util.Constants;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MovieInfo> movieList;
    private MainActivity.FragmentListener listener;

    public MovieListAdapter(Context context, List<MovieInfo> movieList, MainActivity.FragmentListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

   /* @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MovieListAdapter.MovieViewHolder(view);
    }*/

    @Override
    public int getItemViewType(int position) {
        MovieInfo info = movieList.get(position);
        if (info.getRating() > 5.0) {
            return Constants.HIGH_RATED;
        }
        return Constants.LOW_RATED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.HIGH_RATED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_high_rated, parent, false);
            return new TopRatedViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        MovieInfo info = movieList.get(position);

        if (Constants.HIGH_RATED == viewHolder.getItemViewType()) {
            TopRatedViewHolder holder = (TopRatedViewHolder) viewHolder;
            if(info.isVideo()) {
                holder.playVideoView.setVisibility(View.VISIBLE);
            } else {
                holder.playVideoView.setVisibility(View.GONE);
            }
            Picasso.with(context).load(Constants.BACKDROP_IMAGE_URL + info.getMovieBackdrop())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.bannerView);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(position);
                }
            });
        } else {

            ListViewHolder holder = (ListViewHolder) viewHolder;
            holder.titleView.setText(info.getMovieTitle());
            holder.overviewView.setText(info.getMovieOverview());
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                Picasso.with(context).load(Constants.IMAGE_URL + info.getMoviePoster())
                        .placeholder(R.drawable.placeholder)
                        .into(holder.posterView);
            } else {
                Picasso.with(context).load(Constants.BACKDROP_IMAGE_URL + info.getMovieBackdrop())
                        .placeholder(R.drawable.placeholder)
                        .into(holder.posterView);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(position);
                }
            });
        }
    }

    /*@Override
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
    }*/

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    /*public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView overviewView;
        private ImageView posterView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.itemTitle);
            overviewView = itemView.findViewById(R.id.itemOverview);
            posterView = itemView.findViewById(R.id.itemImage);
        }
    }*/

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView overviewView;
        private ImageView posterView;
        private CardView cardView;

        public ListViewHolder(View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.itemTitle);
            overviewView = itemView.findViewById(R.id.itemOverview);
            posterView = itemView.findViewById(R.id.itemImage);
            cardView = itemView.findViewById(R.id.item_cardView);
        }
    }

    public class TopRatedViewHolder extends RecyclerView.ViewHolder {

        private ImageView bannerView;
        private CardView cardView;
        private ImageView playVideoView;

        public TopRatedViewHolder(View itemView) {
            super(itemView);

            bannerView = itemView.findViewById(R.id.itemImage);
            cardView = itemView.findViewById(R.id.item_cardView);
            playVideoView = itemView.findViewById(R.id.itemPlayVideo);
        }
    }

    public void updateDateSet(List<MovieInfo> list) {
        this.movieList = list;
    }
}
