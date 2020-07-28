package com.example.serien_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Die Verarbeiteten Daten von RetrieveInfoTask und GetMovieData werden genutzt um die Daten eines gesuchten Filmes anzuzeigen.
 * Hier gibt es keinen Floating Action Button, da es sinnlos wäre einen Film, der bereits in der Watchlist ist noch einmal hinzuzufügen.
 * Wird nur aus der Watchlist heraus verwendet.
 */

public class MovieActivityDel extends AppCompatActivity {

    private List<String> anbieter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_del);

        TextView tv_Title = findViewById(R.id.tv_Title);
        TextView tv_Description = findViewById(R.id.tv_Description);
        TextView tv_GenreYear = findViewById(R.id.tv_genreAndYear);
        ImageView iv_Picture = findViewById(R.id.iv_Picture);

        RatingBar ratingBar = findViewById(R.id.ratingBar_movie_activity);

        ImageView ivNetflix, ivPrime, ivDisney, ivJoyn, ivMaxdome, ivSkyStore, ivSkyGo, ivGoogle, ivApple, ivMicrosoft;

        ivNetflix = findViewById(R.id.iv_netflix);
        ivPrime = findViewById(R.id.iv_prime);
        ivDisney = findViewById(R.id.iv_disney);
        ivJoyn = findViewById(R.id.iv_joyn);
        ivMaxdome = findViewById(R.id.iv_maxdome);
        ivSkyStore = findViewById(R.id.iv_skystore);
        ivSkyGo = findViewById(R.id.iv_skygo);
        ivGoogle = findViewById(R.id.iv_google);
        ivApple = findViewById(R.id.iv_apple);
        ivMicrosoft = findViewById(R.id.iv_microsoft);

        boolean netflix = false;
        boolean amazon = false;
        boolean disney = false;
        boolean joyn = false;
        boolean maxdome = false;
        boolean skystore = false;
        boolean skygo = false;
        boolean googleplay = false;
        boolean itunes = false;
        boolean microsoft = false;

        tv_Description.setMovementMethod(new ScrollingMovementMethod());
        anbieter.clear();

        Bundle b = getIntent().getExtras();
        final Item item = new Item();

        if (b != null) {

            item.setTitle(b.getString("title"));
            item.setImage("https://" + b.getString("image"));
            item.setDescription(b.getString("description"));
            item.setCategoryAndYear(b.getString("genreAndYear"));
            item.setLink(b.getString("link"));
            item.setRating(b.getFloat("rating"));
            item.setProvider(b.getStringArrayList("providers"));
            item.setProviderlinks(b.getStringArrayList("providerlinks"));


            if (item.getImage().contains("Movie-Placeholder")) {
                item.setImage("https://www.werstreamt.es/themes/wse/images/Movie-Placeholder.png");
            } else if (item.getImage().contains("Show-Placeholder")) {
                item.setImage("https://www.werstreamt.es/themes/wse/images/Show-Placeholder.png");
            }

            tv_Title.setText(item.getTitle());
            tv_GenreYear.setText(item.getCategoryAndYear());
            tv_Description.setText(item.getDescription());
            Picasso.get().load(item.getImage()).into(iv_Picture);
            ratingBar.setRating(item.getRating());

            if (b.getStringArrayList("providers").isEmpty()) {
                anbieter.add("Kein Anbieter Verfügbar");
                System.out.println("Nothing!!!");
                final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, anbieter);
            } else {
                final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, b.getStringArrayList("providers"));
                anbieter = b.getStringArrayList("providers");
                for (String s : anbieter) {

                    switch (s) {
                        case "Netflix":
                            ivNetflix.setImageResource(R.drawable.netflix);
                            netflix=true;
                            break;
                        case "Amazon":
                            ivPrime.setImageResource(R.drawable.prime);
                            amazon=true;
                            break;
                        case "Disney+":
                            ivDisney.setImageResource(R.drawable.disney1);
                            disney=true;
                            break;
                        case "Joyn":
                            ivJoyn.setImageResource(R.drawable.joyn);
                            joyn=true;
                            break;
                        case "Maxdome":
                            ivMaxdome.setImageResource(R.drawable.maxdome);
                            maxdome=true;
                            break;
                        case "Sky Store":
                            ivSkyStore.setImageResource(R.drawable.skystore);
                            skystore=true;
                            break;
                        case "Sky Go":
                            ivSkyGo.setImageResource(R.drawable.skygo);
                            skygo=true;
                            break;
                        case "Google Play":
                            ivGoogle.setImageResource(R.drawable.google);
                            googleplay=true;
                            break;
                        case "iTunes":
                            ivApple.setImageResource(R.drawable.itunes);
                            itunes=true;
                            break;
                        case "Microsoft":
                            ivMicrosoft.setImageResource(R.drawable.microsoft);
                            microsoft=true;
                            break;
                    }
                }
            }

            if(netflix) {
                final Intent openNetflix = new Intent(android.content.Intent.ACTION_VIEW);
                ivNetflix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openNetflix.setData(Uri.parse(item.getProviderlinks().get(0)));
                        startActivity(openNetflix);
                    }
                });
            }
            if(amazon) {
                final Intent openAmazon = new Intent(android.content.Intent.ACTION_VIEW);
                ivPrime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openAmazon.setData(Uri.parse(item.getProviderlinks().get(1)));
                        startActivity(openAmazon);
                    }
                });
            }
            if(disney) {
                final Intent openDisney = new Intent(android.content.Intent.ACTION_VIEW);
                ivDisney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDisney.setData(Uri.parse(item.getProviderlinks().get(2)));
                        startActivity(openDisney);
                    }
                });
            }
            if(joyn) {
                final Intent openJoyn = new Intent(android.content.Intent.ACTION_VIEW);
                ivJoyn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openJoyn.setData(Uri.parse(item.getProviderlinks().get(3)));
                        startActivity(openJoyn);
                    }
                });
            }
            if(maxdome) {
                final Intent openMaxdome = new Intent(android.content.Intent.ACTION_VIEW);
                ivMaxdome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openMaxdome.setData(Uri.parse(item.getProviderlinks().get(4)));
                        startActivity(openMaxdome);
                    }
                });
            }
            if(skystore) {
                final Intent openSkystore = new Intent(android.content.Intent.ACTION_VIEW);
                ivSkyStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSkystore.setData(Uri.parse(item.getProviderlinks().get(5)));
                        startActivity(openSkystore);
                    }
                });
            }
            if(skygo) {
                final Intent openSkygo= new Intent(android.content.Intent.ACTION_VIEW);
                ivSkyGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSkygo.setData(Uri.parse(item.getProviderlinks().get(6)));
                        startActivity(openSkygo);
                    }
                });
            }
            if(googleplay){
                final Intent openGoogleplay= new Intent(android.content.Intent.ACTION_VIEW);
                ivGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGoogleplay.setData(Uri.parse(item.getProviderlinks().get(7)));
                        startActivity(openGoogleplay);
                    }
                });
            }
            if(itunes){
                final Intent openItunes= new Intent(android.content.Intent.ACTION_VIEW);
                ivApple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openItunes.setData(Uri.parse(item.getProviderlinks().get(8)));
                        startActivity(openItunes);
                    }
                });
            }
            if(microsoft){
                final Intent openMicrosoft= new Intent(android.content.Intent.ACTION_VIEW);
                ivMicrosoft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openMicrosoft.setData(Uri.parse(item.getProviderlinks().get(9)));
                        startActivity(openMicrosoft);
                    }
                });
            }
        }
    }

}