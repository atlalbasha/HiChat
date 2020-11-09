package atlal.basha.hichat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class SingUpActivity extends AppCompatActivity {
    private int image;

    private ImageView imageView;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText conformPassword;

    private String idFirebase;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference userReference;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        imageView = findViewById(R.id.user_image);
        name = findViewById(R.id.edit_text_nameSingUp);
        email = findViewById(R.id.edit_text_emailSingUp);
        password = findViewById(R.id.edit_text_passwordSingUp);
        conformPassword = findViewById(R.id.edit_text_conform_password);


    }


    public void singUpNewUserPressed(View view) {
        String userName = name.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String userConformPass = conformPassword.getText().toString().trim();
        
        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userConformPass.isEmpty()){
            Toast.makeText(this, "Fields can't be empty.", Toast.LENGTH_SHORT).show();
        } else {
            if (userPassword.contentEquals(userConformPass)){
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    idFirebase = mAuth.getCurrentUser().getUid();
                                    userReference = db.collection("users").document(idFirebase);
                                    NewUser newUser = new NewUser(userName, userEmail,idFirebase );

                                    db.collection("users").document(idFirebase).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            friendsIntent();
                                        }
                                    });
                                } else {
                                    Toast.makeText(SingUpActivity.this, task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SingUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(this, "Password dose't match", Toast.LENGTH_SHORT).show();
            }
            
        }
    }
    public void friendsIntent(){
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    public void setImage(View view) {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/'");
    startActivityForResult(intent, image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image){
            if (resultCode == RESULT_OK){
                Uri selectImage = data.getData();
                InputStream inputStream = null;
                try {
                    assert selectImage != null;
                    inputStream = getContentResolver().openInputStream(selectImage);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                BitmapFactory.decodeStream(inputStream);
                imageView.setImageURI(selectImage);
            }
        }
    }
}
