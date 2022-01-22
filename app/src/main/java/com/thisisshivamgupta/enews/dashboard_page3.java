package com.thisisshivamgupta.enews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thisisshivamgupta.enews.Models.NewsApiResponse;
import com.thisisshivamgupta.enews.Models.NewsHeadlines;

import java.util.List;

public class dashboard_page3 extends AppCompatActivity implements SelectListener, View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdaptor adaptor;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page3);

        searchView= findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news articles of "+query);
                dialog.show();

                RequestManager manager =new RequestManager(dashboard_page3.this);
                manager.getNewsHeadlines(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        dialog= new ProgressDialog(this);
        dialog.setTitle("Fetching Latest News..");
        dialog.show();

        b1=findViewById(R.id.btn_1);
        b1.setOnClickListener(this);

        b2=findViewById(R.id.btn_2);
        b2.setOnClickListener(this);

        b3=findViewById(R.id.btn_3);
        b3.setOnClickListener(this);

        b4=findViewById(R.id.btn_4);
        b4.setOnClickListener(this);

        b5=findViewById(R.id.btn_5);
        b5.setOnClickListener(this);

        b6=findViewById(R.id.btn_6);
        b6.setOnClickListener(this);

        b7=findViewById(R.id.btn_7);
        b7.setOnClickListener(this);


        RequestManager manager =new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);





    }
    private final OnFetchDataListener<NewsApiResponse> listener= new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {

            if(list.isEmpty()){
                Toast.makeText(dashboard_page3.this, "Thanos is here!", Toast.LENGTH_SHORT).show();
            }
            else {
                showNews(list);
                dialog.dismiss();

            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(dashboard_page3.this, "Oops!Something went wrong.", Toast.LENGTH_SHORT).show();

        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView= findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adaptor= new CustomAdaptor(this,list,this);
        recyclerView.setAdapter(adaptor);

    }

    @Override
    public void OnNewsCliked(NewsHeadlines headlines) {
        startActivity(new Intent(dashboard_page3.this,detailsActivity_Page4.class)
                .putExtra("data", headlines));


    }

    @Override
    public void onClick(View v) {
        Button button= (Button) v;
        String category= button.getText().toString();
        dialog.setTitle("Fetching new article of "+category);
        dialog.show();
        RequestManager manager =new RequestManager(this);
        manager.getNewsHeadlines(listener,category,null);

    }
}