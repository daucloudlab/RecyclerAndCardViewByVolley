package kaznitu.kz.recyclerandcardviewbyvolley.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaznitu.kz.recyclerandcardviewbyvolley.R;
import kaznitu.kz.recyclerandcardviewbyvolley.models.Movie;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder>{

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView ;
        ImageView imageView ;
        TextView mTitle, mRating, mGenre, mReleaseYear ;
        Context mContext ;
        public MyViewHolder(View view){
            super(view) ;
            cardView = (CardView)view.findViewById(R.id.card) ;
            imageView = (ImageView)view.findViewById(R.id.thumbnail) ;
            mTitle = (TextView)view.findViewById(R.id.title) ;
            mRating = (TextView)view.findViewById(R.id.rating) ;
            mGenre = (TextView)view.findViewById(R.id.genre) ;
            mReleaseYear = (TextView)view.findViewById(R.id.releaseYear) ;
            mContext = view.getContext() ;
        }
    }

    private List<Movie> movieItems ;

    public RVAdapter(List<Movie> movies){
        movieItems = movies ;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,
                parent, false) ;
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = movieItems.get(position) ;
        Picasso.with(holder.mContext).load(movie.getThumbnailUrl()).into(holder.imageView);
        holder.mTitle.setText(movie.getTitle());
        holder.mReleaseYear.setText(Integer.toString(movie.getYear()));
        holder.mRating.setText(Double.toString(movie.getRating()));
        String genreStr  = "";
        for(String s:movie.getGenre())
            genreStr += s + ", " ;
        holder.mGenre.setText(genreStr);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }
}
