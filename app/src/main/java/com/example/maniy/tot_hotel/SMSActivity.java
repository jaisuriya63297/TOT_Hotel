package com.example.maniy.tot_hotel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SMSActivity extends AppCompatActivity {
    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;
    private ClipboardManager chm;

    String sample,sms,ph;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        buttonSend = (Button)findViewById(R.id.buttonSend);
        textPhoneNo = (EditText)findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText)findViewById(R.id.editTextSMS);
        chm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("CustomerInfo");

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CustomerInfo customerInfo = dataSnapshot.getValue(CustomerInfo.class);
                ph = customerInfo.getCustomerPhone();
                textPhoneNo.setText(ph);

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


        Intent i = getIntent();
        sample = i.getStringExtra("b");

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = textPhoneNo.getText().toString();
                sms = sample;
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(SMSActivity.this, "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(SMSActivity.this,
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                textPhoneNo.setText("");
                textSMS.setText("");
            }
        });
    }

    public void Paste(View view){

        ClipData cd2 = chm.getPrimaryClip();
        ClipData.Item item = cd2.getItemAt(0);
        String copied = item.getText().toString();
        textSMS.setText(copied);
    }
}