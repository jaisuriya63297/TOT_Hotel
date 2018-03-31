package com.example.maniy.tot_hotel;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class FoodRetrieveActivity extends AppCompatActivity {


    private TextView cname,cpname,tname,tno,coachname,seatno,dest,Food1,Food2,Food3,spin1,spin2,spin3;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseCustomerReference,mDatabaseHotelReference;
    String customerName,customerPhone,trainName,trainNumber,coachName,seatNumber,destinationStation,food1,food2,food3,quantity1,quantity2,quantity3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_retrieve);


        cname = findViewById(R.id.cname);
        cpname = findViewById(R.id.cpname);
        tname = findViewById(R.id.tname);
        tno = findViewById(R.id.tno);
        coachname = findViewById(R.id.coachname);
        seatno = findViewById(R.id.seatno);
        dest = findViewById(R.id.dest);

        Food1 = findViewById(R.id.food1);
        Food2 = findViewById(R.id.food2);
        Food3 = findViewById(R.id.food3);
        spin1 = findViewById(R.id.spin1);
        spin2 = findViewById(R.id.spin2);
        spin3 = findViewById(R.id.spin3);


        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mDatabaseCustomerReference = mFirebaseDatabse.getReference("CustomerInfo");
        mDatabaseHotelReference = mFirebaseDatabse.getReference("FoodInfo");

    }

    @Override
    protected void onStart() {
        super.onStart();



        mDatabaseCustomerReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CustomerInfo customerInfo = dataSnapshot.getValue(CustomerInfo.class);
                customerName = customerInfo.getCustomerName();
                customerPhone = customerInfo.getCustomerPhone();
                trainName = customerInfo.getTrainName();
                trainNumber = customerInfo.getTrainNumber();
                coachName = customerInfo.getCoachName();
                seatNumber = customerInfo.getSeatNumber();
                destinationStation = customerInfo.getDestinationStation();

                cname.setText(customerName);
                cpname.setText(customerPhone);
                tname.setText(trainName);
                tno.setText(trainNumber);
                coachname.setText(coachName);
                seatno.setText(seatNumber);
                dest.setText(destinationStation);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseHotelReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FoodInfo foodInfo = dataSnapshot.getValue(FoodInfo.class);
                food1 = foodInfo.getFoodName1();
                food2 = foodInfo.getFoodName2();
                food3 = foodInfo.getFoodName3();
                quantity1 = foodInfo.getFoodQuantity1();
                quantity2 = foodInfo.getFoodQuantity2();
                quantity3 = foodInfo.getFoodQuantity3();

                Food1.setText(food1);
                Food2.setText(food2);
                Food3.setText(food3);
                spin1.setText(quantity1);
                spin2.setText(quantity2);
                spin3.setText(quantity3);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void callButton(View view){

        Intent i = new Intent(Intent.ACTION_DIAL);
        String uri = "tel:"+customerPhone;
        i.setData(Uri.parse(uri));
        startActivity(i);
    }

    //menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_retrieve_menu, menu);
        return true;
    }

    //menu option executions using switch
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.location_share_menu:
                startActivity(new Intent(FoodRetrieveActivity.this,LocationActivity.class));
                break;

            case R.id.chat:
                startActivity(new Intent(FoodRetrieveActivity.this,ChatActivity.class));
                 break;

            case R.id.sign_out_menu:
                //Sign_out Operation
                AuthUI.getInstance().signOut(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
