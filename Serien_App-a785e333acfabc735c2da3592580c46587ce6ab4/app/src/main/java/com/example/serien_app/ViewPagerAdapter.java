package com.example.serien_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import static com.example.serien_app.MainActivity.exchangeLink;

/**
 * Adapter Klasse für den Viepager / Swipeview.
 * Wird vom Watchlist Fragment genutzt.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<Item> models;
    private LayoutInflater layoutInflater;
    private Context context;


    public ViewPagerAdapter(List<Item> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.swipeview_item, container, false);

        ImageView imageView;
        TextView title;

        ImageButton del = view.findViewById(R.id.btn_item_del);

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);

        String s = "https://" + models.get(position).getImage();

        if (s.contains("Movie-Placeholder")) {
            s = "https://www.werstreamt.es/themes/wse/images/Movie-Placeholder.png";
        }
        if (s.contains("Show-Placeholder")) {
            s = "https://www.werstreamt.es/themes/wse/images/Show-Placeholder.png";
        }

        Picasso.get().load(s).into(imageView);


        title.setText(models.get(position).getTitle());

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, Listen.getWatchList().get(position).getTitle() + " was deleted from your Watchlist.", Toast.LENGTH_SHORT).show();

                Listen.getWatchList().remove(position);

                WatchlistFragment.setAdapter(context);
                for (Item item : Listen.getWatchList()) {
                    System.out.println(item.getImage());
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                Item item = Listen.getWatchList().get(position);

                exchangeLink = item.getLink();
                exchangeLink = "https://www.werstreamt.es/" + exchangeLink;

                try {
                    new GetMovieData().execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, MovieActivityDel.class);

                b.putString("title", item.getTitle());
                b.putString("image", item.getImage().replace("https://", ""));
                b.putString("description", item.getDescription());
                b.putString("link", item.getLink());
                b.putString("genreAndYear", item.getCategoryAndYear());
                b.putFloat("rating", item.getRating());
                b.putStringArrayList("providers", item.getProvider());
                b.putStringArrayList("providerlinks",item.getProviderlinks());


                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
