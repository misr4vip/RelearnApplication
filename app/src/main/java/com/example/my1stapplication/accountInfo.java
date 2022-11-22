package com.example.my1stapplication;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.Cache;

import java.util.Objects;

import static com.example.my1stapplication.MainActivity.userID;


public class accountInfo extends AppCompatActivity{

        EditText email, name, IBAN,phone;
        Button up,can;
        DatabaseReference db;
      User userRef;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.profile_);
            email = (EditText) findViewById(R.id.textView31);
            name = (EditText) findViewById(R.id.textView32);
            IBAN = (EditText) findViewById(R.id.textView33);
            phone = (EditText) findViewById(R.id.textView34);
            can = (Button) findViewById(R.id.cancel);
            up = (Button) findViewById(R.id.updateInfo);

            db = FirebaseDatabase.getInstance().getReference("Users").child("email");

            db.addListenerForSingleValueEvent(new ValueEventListener() {
               // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Log.e("ID3", (dataSnapshot.getKey()));

                            Log.e("ID03", userID);


                            if (dataSnapshot.getKey().equalsIgnoreCase(userID)) {
                                User one = dataSnapshot.getValue(User.class);
                                assert one != null;
                                email.setText(one.getEmail());
                                name.setText(one.getName());
                                IBAN.setText(one.getIban());
                                phone.setText(one.getPhone());
                                break;
                            }
                            Log.e("ID5", dataSnapshot.getKey());
                        }
                    }catch (Exception E){   E.printStackTrace();}
                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);


            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch (item.getItemId()) {
                        case R.id.home:
                            Toast.makeText(accountInfo.this, "Home", Toast.LENGTH_SHORT).show();
                            Intent Intenthome = new Intent(accountInfo.this, BooksList.class);
                            startActivity(Intenthome);
                            break;
                        case R.id.cart:
                            Toast.makeText(accountInfo.this, "Cart", Toast.LENGTH_SHORT).show();
                            Intent Intentcart = new Intent(accountInfo.this, CartList.class);
                            startActivity(Intentcart);

                            break;

                        case R.id.post:
                            Toast.makeText(accountInfo.this, "post", Toast.LENGTH_SHORT).show();
                            Intent Intentpost = new Intent(accountInfo.this, test.class);
                            startActivity(Intentpost);

                            break;
                        case R.id.fav:
                            Toast.makeText(accountInfo.this, "Profile", Toast.LENGTH_SHORT).show();
                            Intent Intentfav = new Intent(accountInfo.this, favorite.class);
                            startActivity(Intentfav);
                            break;
                    }
                    return true;
                }
            });

            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userRef = new User();
                    userRef.setEmail(email.getText().toString());
                    userRef.setIban(IBAN.getText().toString());
                    userRef.setPhone(phone.getText().toString());
                    userRef.setName(name.getText().toString());
                    db.setValue(userRef);
                    Toast.makeText(accountInfo.this, "Your account's information has been updated", Toast.LENGTH_SHORT).show();
                }
            });
            can.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

