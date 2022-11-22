package com.example.my1stapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Button;
import android.text.TextUtils;
import com.google.firebase.storage.StorageReference;





public class BooksList extends AppCompatActivity{



    List<Post> postslist;
    DatabaseReference db;
    ListView listViewPosts;
    ImageView home;
    ImageButton logout;
    SearchView searchview;
    Button showB;
    accountInfo a = new accountInfo();
    private FirebaseAuth.AuthStateListener authStateListener;
    StorageReference courseReference, materialTypeReference;
    Button logouut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);


        db= FirebaseDatabase.getInstance().getReference("posts");

        listViewPosts = (ListView) findViewById(R.id.listViewPosts);
        //home = (ImageView) findViewById(R.id.home);
      //  logout=(ImageButton) findViewById(R.id.logout);
        searchview=(SearchView) findViewById(R.id.SearchBook);
        postslist= new ArrayList<>();

logouut=(Button)findViewById(R.id.logout);



        logouut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(BooksList.this, ActivityLogin.class);
                startActivity(I);
                Log.e("TAG", "Message");
            }
        });

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);


       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(BooksList.this, "Home", Toast.LENGTH_SHORT).show();
                    //    bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));

                        Intent Intenthome = new Intent(BooksList.this, BooksList.class);
                        startActivity(Intenthome);
                        break;
                    case R.id.cart:
                        Toast.makeText(BooksList.this, "Cart", Toast.LENGTH_SHORT).show();
                        Intent Intentcart = new Intent(BooksList.this, CartList.class);
                        startActivity(Intentcart);

                        break;

                    case R.id.post:
                        Toast.makeText(BooksList.this, "post", Toast.LENGTH_SHORT).show();
                        Intent Intentpost = new Intent(BooksList.this, test.class);
                        startActivity(Intentpost);

                        break;
                    case R.id.fav:
                        Toast.makeText(BooksList.this, "Profile", Toast.LENGTH_SHORT).show();
                        Intent Intentfav = new Intent(BooksList.this, FaviroteActivity.class);
                        startActivity(Intentfav);
                        break;
                }
                return true;
            }
        });





       // logout.setOnClickListener(new View.OnClickListener() {
           // @Override
         //   public void onClick(View v) {
           //     FirebaseAuth.getInstance().signOut();
              //  Intent I = new Intent(BooksList.this, ActivityLogin.class);
              //  startActivity(I);
               // Log.e("TAG", "Message");
           // }
       // });


       // home.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   moveToHome();
            //}
        //});
    }

    //public void moveToHome(){

       // Intent intent = new Intent(BooksList.this, UserActivity.class);
       // startActivity(intent);
  //  }

    @Override
    protected void onStart() {
        super.onStart();
      final  postsList adapter= new postsList(BooksList.this,postslist );

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postslist.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);

                postslist.add(post);
                    listViewPosts.setAdapter(adapter);

                }


               // postsList adapter= new postsList(BooksList.this, postslist );
          //listViewPosts.setAdapter(adapter);



            }// raghad


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    adapter.notifyDataSetChanged();


                }
                else{
                    adapter.filter(newText);
                    listViewPosts.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    //listViewPosts.clearTextFilter();
                    adapter.notifyDataSetChanged();
                }
                else{
                    adapter.filter(newText);
                    //listViewPosts.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

        });


        /*BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(BooksList.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent Intenthome = new Intent(BooksList.this, BooksList.class);
                        startActivity(Intenthome);
                        break;
                    case R.id.cart:
                        Toast.makeText(BooksList.this, "Cart", Toast.LENGTH_SHORT).show();
                        Intent Intentcart = new Intent(BooksList.this, CartList.class);
                        startActivity(Intentcart);

                        break;

                    case R.id.post:
                        Toast.makeText(BooksList.this, "post", Toast.LENGTH_SHORT).show();
                        Intent Intentpost = new Intent(BooksList.this, test.class);
                        startActivity(Intentpost);

                        break;
                    case R.id.profile:
                        Toast.makeText(BooksList.this, "Profile", Toast.LENGTH_SHORT).show();
                        Intent Intentprofile = new Intent(BooksList.this, profile.class);
                        startActivity(Intentprofile);
                        break;
                }
                return true;
            }
        });*/
        }
}
