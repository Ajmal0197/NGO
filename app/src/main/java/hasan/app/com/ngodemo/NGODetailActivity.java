package hasan.app.com.ngodemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NGODetailActivity extends AppCompatActivity {

    ImageView postImage1;
    TextView postTitle1, postWebsite1, postLocation1, postPhone1, postAbout1;
    Button btnCall, btnWeb;

    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngodetail);


        // intialize views
        btnCall = findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall();

            }
        });



     /*   //Website opens on click
        btnWeb=findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number= postWebsite1.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(number));
                startActivity(intent);
            }
        });   */




        postTitle1= findViewById(R.id.post_Title1);
        postWebsite1= findViewById(R.id.post_Website1);
        postLocation1= findViewById(R.id.post_Location1);
        postPhone1= findViewById(R.id.post_Phone1);
        postAbout1= findViewById(R.id.post_About1);
        postImage1=findViewById(R.id.post_Image1);

        //get data from intent
        byte[] bytes= getIntent().getByteArrayExtra("image");
        Bitmap bmp= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        String title= getIntent().getStringExtra("title");
        String website= getIntent().getStringExtra("website");
        String location= getIntent().getStringExtra("location");
        String phone= getIntent().getStringExtra("phone");
        String about= getIntent().getStringExtra("about");


        //set data to views
        postTitle1.setText(title);
        postWebsite1.setText(website);
        postLocation1.setText(location);
        postPhone1.setText(phone);
        postAbout1.setText(about);
        postImage1.setImageBitmap(bmp);


        // Action bar when of activity started by on click on firebase recycler view
        ActionBar actionBar =getSupportActionBar();
        //set back button in action bar
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//Making Phone Call
    private void makePhoneCall() {
        String number= postPhone1.getText().toString();
        if(number.trim().length()>0){

            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse(number));
            try {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(NGODetailActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    return;
                }

                else
                {
                    startActivity(new Intent(getApplicationContext(), NGODetailActivity.class));
                }

            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
            }
        }
        
        else {
            Toast.makeText(this, "Call not made", Toast.LENGTH_SHORT).show();
        }
    }
//Giving permission to call if not have
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
