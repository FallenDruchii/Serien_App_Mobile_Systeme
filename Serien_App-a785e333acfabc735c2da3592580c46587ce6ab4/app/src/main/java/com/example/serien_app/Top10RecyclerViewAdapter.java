package com.example.serien_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.serien_app.MainActivity.exchangeLink;

/**
 * Recycler View Adapter für die Top20.
 * Wird im Home Fragment verwendet um die Items anzuzeigen.
 */

public class Top10RecyclerViewAdapter extends RecyclerView.Adapter<Top10RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Item> itemList;

    public Top10RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.textView.setText(itemList.get(position).getTitle());
        String image = itemList.get(position).getImage();
        Picasso.get().load(image).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = Listen.getTopList().get(position);
                Bundle b = new Bundle();
                exchangeLink = "https://" + item.getLink();

                String movieDescription = null;
                String movieRating = null;
                ArrayList<String> providers = new ArrayList<>();
                ArrayList<String> providerLinks = new ArrayList<>();
                try {
                    new GetMovieData().execute().get();
                    movieDescription = GetMovieData.movieDesciption;
                    movieRating = GetMovieData.movieRating;
                    //providers = GetMovieData.streamingServiceList;
                    providers = GetMovieData.ListNames;
                    providerLinks = (ArrayList<String>) GetMovieData.ListLinks;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, MovieActivity.class);

                b.putString("title", item.getTitle());
                b.putString("image", item.getImage().replace("https://", ""));
                b.putString("description", movieDescription);
                b.putString("genreAndYear", item.getCategoryAndYear());
                b.putFloat("rating", Float.parseFloat(movieRating));
                b.putStringArrayList("providers", providers);
                b.putStringArrayList("providerlinks",providerLinks);

                intent.putExtras(b);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.tv_item_home_fragment);
            imageView = view.findViewById(R.id.iv_item_home_fragment);
            cardView = view.findViewById(R.id.cardview_home_fragment);
        }
    }
}
