package com.example.serien_app;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Thread.sleep;

/**
 * Fragment in der die Suche durchgeführt wird.
 * Die Daten von RetrieveInfoTask werden hier genutzt um die möglichen Treffer einer Suche anzuzeigen.
 */

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText editText;
    private ImageButton button;


    private ArrayList<Item> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_search_fragment);
        editText = view.findViewById(R.id.et_fragment_search);
        button = view.findViewById(R.id.btn_fragment_search);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void run() {
                        methodSearch();
                    }
                }, 2000);   //5 seconds

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                Listen.getSearchHistory().add(editText.getText().toString());

            }
        });



        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    Listen.getSearchHistory().add(editText.getText().toString());
                    new Handler().postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        public void run() {
                            methodSearch();
                        }
                    }, 2000);

                }


                return false;
            }
        });

        return view;

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void methodSearch() {


        String etSearchString = editText.getText().toString();

        String searchString = etSearchString.replaceAll(" ", "+");

        if (etSearchString.equals("")) {
            Toast.makeText(getContext(), "Ändern Sie Ihre Suche!", Toast.LENGTH_SHORT).show();
        } else {


            String link = ("https://www.werstreamt.es/filme-serien/?q=" + searchString + "&action_results=suchen");
            URL url = null;
            try {
                url = new URL(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                getInfo(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arrayList = RetrieveInfoTask.arrayList;

            if (arrayList.isEmpty()) {
                Toast.makeText(getContext(), "Keine Suchergebnisse gefunden!", Toast.LENGTH_SHORT).show();
            } else {
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), arrayList);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

        }

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getInfo(URL url) throws Exception {

        RetrieveInfoTask task = new RetrieveInfoTask();
        task.execute(url);

    }
}
