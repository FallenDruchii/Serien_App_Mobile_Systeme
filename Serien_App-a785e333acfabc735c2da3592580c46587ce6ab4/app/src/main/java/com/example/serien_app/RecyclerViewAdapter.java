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
 * Adpater Klasse für die Recycler View.
 * Wird vom SearchFragment genutzt.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Item> itemList;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
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
        String image = "https://" + itemList.get(position).getImage();
        Picasso.get().load(image).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = RetrieveInfoTask.arrayList.get(position);
                Bundle b = new Bundle();

                exchangeLink = item.getLink();
                exchangeLink = "https://www.werstreamt.es/" + exchangeLink;
                String movieDescription = null;
                String movieRating = null;
                ArrayList<String> providers = new ArrayList<>();
                ArrayList<String> providerLinks = new ArrayList<>();
                try {
                    new GetMovieData().execute().get();
                    movieDescription = GetMovieData.movieDesciption;
                    movieRating = GetMovieData.movieRating;
                    //providers = (ArrayList<String>) GetMovieData.streamingServiceList;
                    providers = (ArrayList<String>) GetMovieData.ListNames;
                    providerLinks = (ArrayList<String>) GetMovieData.ListLinks;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, MovieActivity.class);

                b.putString("title", item.getTitle());
                b.putString("image", item.getImage());
                b.putString("description", movieDescription);
                b.putString("genreAndYear", item.getCategoryAndYear());
                b.putString("link", exchangeLink);
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
        return RetrieveInfoTask.exchangeNamesList.size();
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
