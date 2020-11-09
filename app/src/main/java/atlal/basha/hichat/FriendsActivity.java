package atlal.basha.hichat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;




public class FriendsActivity extends AppCompatActivity {


    private RecyclerView userRecycle;
    private UserAdapter userAdapter;
    private RecyclerView.LayoutManager userManager;



    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference userReference;


    public static ArrayList<NewUser> newUsers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userRecycle = findViewById(R.id.user_list_view);
        userRecycle.setHasFixedSize(true);
        userAdapter = new UserAdapter(newUsers);
        userManager = new LinearLayoutManager(this);
        userRecycle.setLayoutManager(userManager);
        userRecycle.setAdapter(userAdapter);

        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void OnItemClicked(int position, View view) {
                 NewUser user = newUsers.get(position);
                 //user.setName("haha");
                //int color = Color.parseColor("#242627");
                //view.setBackgroundColor(color);
                //userAdapter.notifyItemChanged(position);
                chatIntent();
            }
        });

        createUserList();
        //buildRecyclerView();

    }




    public void signOutPressed(View view) {
        FirebaseAuth.getInstance().signOut();
        mainIntent();
    }
    public void mainIntent (){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void chatIntent (){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void createUserList(){
        for (int i = 0; i < 7; i++) {
            newUsers.add(new NewUser("user "+i));
        }
    }

    /*
    public void buildRecyclerView(){
        userRecycle = findViewById(R.id.user_list_view);
        userRecycle.setHasFixedSize(true);
        userAdapter = new UserAdapter(userList);
        userManager = new LinearLayoutManager(this);
        userRecycle.setLayoutManager(userManager);
        userRecycle.setAdapter(userAdapter);

        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void OnItemClicked(int position, View view) {
                NewUser user = newUsers.get(position);
                int color = Color.parseColor("#242627");
                view.setBackgroundColor(color);
            }
        });
    }

     */

    public void addFriend(View view) {
        //newUsers.add(0, new NewUser("jenny"));
        //userAdapter.notifyDataSetChanged();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(FriendsActivity.this, "done", Toast.LENGTH_SHORT).show();
                                String name = document.getString("name");
                                newUsers.add(0, new NewUser(name));
                                userAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(FriendsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
