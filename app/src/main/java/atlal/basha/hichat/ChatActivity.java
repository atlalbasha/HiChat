package atlal.basha.hichat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private EditText editText_MSG;
    private TextView textView_MSG;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference userReference; // = db.collection("Message").document();






    private ArrayList<NewUser> newUsers;
    private ArrayList<MSG> sendMessage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editText_MSG = findViewById(R.id.send_message);
        textView_MSG = findViewById(R.id.message_textView);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    public void sendMessage(View view) {



        String getMSG = editText_MSG.getText().toString();
        //db.collection("message").document(idFireMSG);

        MSG newMSG = new MSG( "ME" ,getMSG, true);

                db.collection("message").document("sendTo")
                        .set(newMSG).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //textView_MSG.append(getMSG);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                editText_MSG.getText().clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.collection("message").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    String sender = documentSnapshot.getString("sender");
                    String msg = documentSnapshot.getString("message");
                    textView_MSG.append(msg + "\n");
                    textView_MSG.append(sender + "\n");
                    textView_MSG.append("\n");

                }

            }
        });

    }
}