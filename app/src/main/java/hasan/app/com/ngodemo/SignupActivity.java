package hasan.app.com.ngodemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101 ;
    private EditText inputEmail, inputPassword;
    private EditText userName, contactNumber;
    private Spinner spinnercity;
    private RadioGroup radioSex;
    private RadioButton radio;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private ImageView profilepic;
    private Uri uriProfileImage;
    String profileImageUrl;
    private DatabaseReference databaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance/ intialization
        auth = FirebaseAuth.getInstance();

        //Storing of user extra details--> init.
        databaseUser= FirebaseDatabase.getInstance().getReference("User");

        radioSex =(RadioGroup) findViewById(R.id.radioSex);
        profilepic=(ImageView) findViewById(R.id.profilepic);
        contactNumber=(EditText)findViewById(R.id.contactnumber);
        userName=(EditText)findViewById(R.id.userName);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        spinnercity = (Spinner) findViewById(R.id.spinnercity);


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageChooser();

            }
        });


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //sign up using only email and password
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveUserInformation();

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });

    }

    private void saveUserInformation() {

        String displayName= userName.getText().toString().trim();
        String phone= contactNumber.getText().toString().trim();
        String cityName= spinnercity.getSelectedItem().toString();
        String profileImage = profileImageUrl; // getting image url

        //Radiogroup takes single radiobutton value
        int selectedId = radioSex.getCheckedRadioButtonId();
        radio = (RadioButton) findViewById(selectedId);
        String gender= radio.getText().toString();

        if (!TextUtils.isEmpty(displayName)) //condition
        {    //databaseUser= FirebaseDatabase.getInstance().getReference("User");
            String id= databaseUser.push().getKey(); //key to uniquely identify each user
            User user =new User(id, displayName, gender, phone, cityName, profileImage); //getting data from model class
            databaseUser.child(id).setValue(user); //setting to firebase

            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "User details stored", Toast.LENGTH_SHORT).show();
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Enter every details", Toast.LENGTH_SHORT).show();
        }


        if(gender.isEmpty()){
        Toast.makeText(this, "Choose Gender", Toast.LENGTH_SHORT).show();
        return;}

        if(displayName.isEmpty()){
            userName.setError("Enter User Name");
        return;}

        if(TextUtils.isEmpty(phone) && phone.length()<9 && phone.length()>11){
            Toast.makeText(getApplicationContext(), "Enter Valid Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if(cityName.isEmpty()){
            Toast.makeText(this, "Choose city", Toast.LENGTH_SHORT).show();
        }

        }

    //Method to make image intent from where image is selected placed under image buttononClick
    private void showImageChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE);
    }

    //To get Image from imageChooser
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data != null && data.getData()!=null){
            uriProfileImage=data.getData(); //returns url from where the image can be selected
            //image url is converted to image by bitmap down here...
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                profilepic.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage(); //We have to make firebase Storage to store data on cloud
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {   //uploading to fb storage

        StorageReference profileImageRef=
                FirebaseStorage.getInstance().getReference("profilepic/"+System.currentTimeMillis()+ ".jpg"); //naming uniquely

        if(uriProfileImage!= null){

            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            profileImageUrl=taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
