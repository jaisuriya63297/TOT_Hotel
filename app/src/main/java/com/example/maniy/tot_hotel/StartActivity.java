package com.example.maniy.tot_hotel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;

public class StartActivity extends AppCompatActivity {

    Button newHotel,exisitingHotel,test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        newHotel = findViewById(R.id.newHotel);
        exisitingHotel = findViewById(R.id.exisitingHotel);
        test = findViewById(R.id.test);

        newHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,HotelDetailsActivity.class));
            }
        });

        exisitingHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,ChatActivity.class));
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,FoodRetrieveActivity.class));
            }
        });
    }

    //menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //menu option executions using switch
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.location_share_menu:
                startActivity(new Intent(StartActivity.this,LocationActivity.class));
                break;


            case R.id.sign_out_menu:
                //Sign_out Operation
                AuthUI.getInstance().signOut(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
