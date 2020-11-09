package atlal.basha.hichat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edit_text_email);
        password= findViewById(R.id.edit_text_password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            friendsIntent();
        }
    }



    public void loginPressed(View view) {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if (userEmail.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this, "fields can't be empty", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                friendsIntent();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this,
                                        task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                            }
                            // if (!task.isSuccessful()) {
                            //
                            // }

                        }
                    });
        }
       
    }



    public void singUpPressed(View view) {
        singUpIntent();
    }

    public void friendsIntent(){
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    public void singUpIntent(){
        Intent intent = new Intent(this, SingUpActivity.class);
        startActivity(intent);
    }


}