package hasan.app.com.ngodemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private TextView mName;
    private ImageView mPic;
    final Random rnd = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//Random screen at navigation activity
        final ImageView img = (ImageView) findViewById(R.id.imgRandom);
        // I have 3 images named img_0 to img_2, so...
        final String str = "fpimg_" + rnd.nextInt(4);
        img.setImageDrawable(getResources().getDrawable(getResourceID(str, "drawable",
                                getApplicationContext())));


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

         //get current user
         final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(NavigationDrawer.this, LoginActivity.class));
                    finish();
                }
            }
        };


/*    ` FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mName   = (TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_name);
       // mPic    = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profileImage);

        //Putting nameto nav draw username
        mName.setText("Welcome, "+"\n"+ user.getEmail());

       // Uri link =user.getPhotoUrl();
       // Glide.with(getApplicationContext()).load(link).into(mPic);
/*

     //   databaseUser = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
       String uid=user.getUid();
       databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Fetch values from you database child and set it to the specific view object.
                databaseUser.child("User").child(user.getUid()).child("uName");
                databaseUser.child("User").child(user.getUid()).child("uCity");
                databaseUser.child("User").child(user.getUid()).child("uImageURL");

                mName.setText(dataSnapshot.child("uName").getValue().toString());
                uCity.setText(dataSnapshot.child("uCity").getValue().toString());

                String link =dataSnapshot.child("uImageURL").getValue().toString();
                Picasso.get()
                        .load(link)
                        .resize(50, 50)
                        .centerCrop()
                        .into(mPic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    }

//Random screen at navigation activity
    protected final static int getResourceID(final String resName, final String resType, final Context ctx)
    {
        final int ResourceID = ctx.getResources().getIdentifier(resName, resType, ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }}




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     /*   if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_logout){
            auth.signOut();
            return true;
            }

        return super.onOptionsItemSelected(item);*/
     switch (item.getItemId()){
         case R.id.action_settings: return true;
         case R.id.action_logout:
             signout();
             return true;
         default:
             return super.onOptionsItemSelected(item);
     }
    }

    private void signout() {
        auth.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment=null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dhome) {
            // Handle the profile action
            startActivity(new Intent(getApplicationContext(), NGORecycler.class));


        } else if (id == R.id.dprofile) {
            //fragment=new HomeFragment();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        } else if (id == R.id.devents) {

        } else if (id == R.id.dquery) {

        } else if (id == R.id.dfeedback) {

        } else if (id == R.id.dself_volunteer) {

        }

        if(fragment!=null){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.screen_area,fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }}


