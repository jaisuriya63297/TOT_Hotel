package com.example.maniy.tot_hotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelDetailsActivity extends AppCompatActivity {

    //Declared the necessary widgets
    EditText hotelNameEdit, hotelAddressEdit, employeeNameEdit, deliveryBoyNameEdittext,food1Edit,food2Edit,food3Edit,food4Edit,food5Edit;
    Button submit;
    ProgressDialog progressDialog;
    List<HotelInfo> hotelInfos;

    //Firebase Initialization
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;

    //RequestCode
    private static final int RC_SIGN_IN = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);



        //setting ID to the views
        hotelNameEdit = findViewById(R.id.HotelNameEditText);
        hotelAddressEdit = findViewById(R.id.HotelAddressEditText);
        employeeNameEdit = findViewById(R.id.EmployeeNumbersEditText);
        deliveryBoyNameEdittext = findViewById(R.id.DeliveryBoysEditText);
        food1Edit = findViewById(R.id.Foods1EditText);
        food2Edit = findViewById(R.id.Foods2EditText);
        food3Edit = findViewById(R.id.Foods3EditText);
        food4Edit = findViewById(R.id.Foods4EditText);
        food5Edit = findViewById(R.id.Foods5EditText);
        submit = findViewById(R.id.submit);


        //list to store CustomerInfos
        hotelInfos = new ArrayList<>();

        //Firebase Instance
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabse = FirebaseDatabase.getInstance();


        //Getting reference to the root of the database
        mDatabaseReference = mFirebaseDatabse.getReference().child("HotelInfo");


        //When button clicked
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(HotelDetailsActivity.this);
                progressDialog.setMessage("Processing...");
                progressDialog.show();
                addHotel();
                progressDialog.dismiss();

                startActivity(new Intent(HotelDetailsActivity.this,FoodRetrieveActivity.class));
            }
        });


        //AuthStateListener to listen whether the user is signed in or out
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // to get the current signed in user
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if (user != null){
                    // signed in
                    Toast.makeText(getApplicationContext(),"Signed in ",Toast.LENGTH_SHORT).show();
                }else{
                    // signed out, so need to display Sign in options
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

    }


    public void addHotel(){

        String hotelName = hotelNameEdit.getText().toString().trim();
        String hotelAddress = hotelAddressEdit.getText().toString().trim();
        String employeeNos = employeeNameEdit.getText().toString().trim();
        String deliveryBoyNos = deliveryBoyNameEdittext.getText().toString().trim();
        String food1 = food1Edit.getText().toString().trim();
        String food2 = food2Edit.getText().toString().trim();
        String food3 = food3Edit.getText().toString().trim();
        String food4 = food4Edit.getText().toString().trim();
        String food5 = food5Edit.getText().toString().trim();



        if (!TextUtils.isEmpty(hotelName) && !TextUtils.isEmpty(hotelAddress) && !TextUtils.isEmpty(employeeNos)
                && !TextUtils.isEmpty(deliveryBoyNos)  ){

            //Getting separate key to store each data
            String id = mDatabaseReference.push().getKey();

            //Creating a HotelInfo Object
            HotelInfo hotelInfo = new HotelInfo( id,  hotelName,  hotelAddress,  employeeNos,  deliveryBoyNos,  food1,  food2,  food3,  food4,  food5);




            //saving data to the database
            mDatabaseReference.child(id).setValue(hotelInfo);

            //setting all edit_text to blank
            hotelNameEdit.setText("");
            hotelAddressEdit.setText("");
            employeeNameEdit.setText("");
            deliveryBoyNameEdittext.setText("");
            food1Edit.setText("");
            food2Edit.setText("");
            food3Edit.setText("");
            food4Edit.setText("");
            food5Edit.setText("");


            //Toast to show the status
            Toast.makeText(getApplicationContext(),"Done!!!",Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getApplicationContext(),"Please enter all the details ",Toast.LENGTH_SHORT).show();
        }


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
                startActivity(new Intent(HotelDetailsActivity.this,LocationActivity.class));
                break;


            case R.id.sign_out_menu:
                //Sign_out Operation
                AuthUI.getInstance().signOut(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();
            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Removing AuthStateListener, since this is in Pause State.
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Adding AuthStateListener, since this is in Resume State
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
