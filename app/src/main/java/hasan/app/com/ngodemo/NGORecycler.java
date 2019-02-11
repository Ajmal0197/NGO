package hasan.app.com.ngodemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;

public class NGORecycler extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngorecycler);

        //Title
        ActionBar actionBar=getSupportActionBar();

        //Recycler view
        mRecyclerView= findViewById(R.id.myrecyclerview);
        mRecyclerView.setHasFixedSize(true);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //layout type
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Firebase// getting json table named json
        mDatabase=FirebaseDatabase.getInstance();
        mRef= mDatabase.getReference("NGO");

    }

    //Firebase search feature
    private void firebaseSearch(String searchText){
        Query firebaseSearchQuuery= mRef.orderByChild("Name")
                .startAt(searchText).endAt(searchText+"\uf8ff");

        FirebaseRecyclerAdapter<NGOclass,NGOViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<NGOclass, NGOViewHolder>(
                        NGOclass.class,R.layout.ngo_row, NGOViewHolder.class,firebaseSearchQuuery
                ) {
                    @Override
                    protected void populateViewHolder(NGOViewHolder viewHolder, NGOclass model, int position) {

                        viewHolder.setDetails(getApplicationContext(), model.getName(),
                                model.getAddress(), model.getImageurl(), model.getBackground(), model.getPhone(), model.getWebsite());
                    }


                    @Override
                    public NGOViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        NGOViewHolder ngoViewHolder=super.onCreateViewHolder(parent, viewType);


                        ngoViewHolder.setOnClickListener(new NGOViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView postTitle1= findViewById(R.id.post_Title);
                                TextView postWebsite1= findViewById(R.id.post_Website1);
                                TextView postLocation1= findViewById(R.id.post_Location);
                                TextView postPhone1= findViewById(R.id.post_Phone1);
                                TextView postAbout1= findViewById(R.id.post_About1);
                                ImageView postImage1=findViewById(R.id.post_Image);

                                //get data from views
                                String mtitle= postTitle1.getText().toString();
                                String mwebsite= postWebsite1.getText().toString();
                                String mlocation= postLocation1.getText().toString();
                                String mphone= postPhone1.getText().toString();
                                String mabout= postAbout1.getText().toString();
                                Drawable mDrawable= postImage1.getDrawable();
                                Bitmap mbitmap=  ((BitmapDrawable)mDrawable).getBitmap();

                                //pass this to new activity
                                Intent intent =new Intent();
                                ByteArrayOutputStream stream= new ByteArrayOutputStream();
                                mbitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes= stream.toByteArray();
                                intent.putExtra("image",bytes);// put bitmap image as array of data
                                intent.putExtra("title", mtitle);
                                intent.putExtra("website", mwebsite);
                                intent.putExtra("location", mlocation);
                                intent.putExtra("phone", mphone);
                                intent.putExtra("about", mabout);

                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //TODO

                            }
                        });
                        return ngoViewHolder;
                    }

                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu, this adds items to action bar if present
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item= menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        //handle other action bar click here
        if(id==R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    // LOad data to RV onStart
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<NGOclass, NGOViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<NGOclass, NGOViewHolder>(
                        NGOclass.class,
                        R.layout.ngo_row,
                        NGOViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(NGOViewHolder viewHolder, NGOclass model, int position) {

                        viewHolder.setDetails(getApplicationContext(), model.getName(),
                                model.getAddress(), model.getImageurl(), model.getBackground(), model.getPhone(), model.getWebsite());

                    }

                    @Override
                    public NGOViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        NGOViewHolder ngoViewHolder=super.onCreateViewHolder(parent, viewType);

                        ngoViewHolder.setOnClickListener(new NGOViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView postTitle1= findViewById(R.id.post_Title);
                                TextView postWebsite1= findViewById(R.id.post_Website1);
                                TextView postLocation1= findViewById(R.id.post_Location);
                                TextView postPhone1= findViewById(R.id.post_Phone1);
                                TextView postAbout1= findViewById(R.id.post_About1);
                                ImageView postImage1=findViewById(R.id.post_Image);

                                //get data from views
                                String mtitle= postTitle1.getText().toString();
                                String mwebsite= postWebsite1.getText().toString();
                                String mlocation= postLocation1.getText().toString();
                                String mphone= postPhone1.getText().toString();
                                String mabout= postAbout1.getText().toString();
                                Drawable mDrawable= postImage1.getDrawable();
                                Bitmap mbitmap=  ((BitmapDrawable)mDrawable).getBitmap();

                                //pass this to new activity
                                Intent intent =new Intent(getApplicationContext(), NGODetailActivity.class);
                                ByteArrayOutputStream stream= new ByteArrayOutputStream();
                                mbitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes= stream.toByteArray();
                                intent.putExtra("image",bytes);// put bitmap image as array of data
                                intent.putExtra("title", mtitle);
                                intent.putExtra("website", mwebsite);
                                intent.putExtra("location", mlocation);
                                intent.putExtra("phone", mphone);
                                intent.putExtra("about", mabout);

                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //TODO

                            }
                        });
                        return ngoViewHolder;
                    }

                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter); //set adapter to firebase
    }
}
