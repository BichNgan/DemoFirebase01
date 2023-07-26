package android.demofirebase01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtAge;
    Button btnInsert, btnDelete, btnUpdate;
    ListView lvKq;

    ArrayList<User>lsUser = new ArrayList<>();
    ArrayList<String> dataListView = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        //------------------
           // initData();
            loadDataForListView();
        //---------------
        addEvent();


    }

    public  void addControls()
    {
        edtName=(EditText) findViewById(R.id.edtName);
        edtAge=(EditText) findViewById(R.id.edtAge);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete=(Button) findViewById(R.id.btnDelete);
        btnUpdate=(Button) findViewById(R.id.btnUpdate);
        lvKq = (ListView) findViewById(R.id.lvKq);
    }
    public  void addEvent()
    {

    }

    public  void initData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        for(int i=0;i<5; i++) {
            User user = new User("Ten " + i, 15+i);
            myRef.child("User_" +i).setValue(user);//thêm push để tạo mục có key random, ko bị đè khi bị trùng

        }
    }

  public   ArrayList<String> convertToArrString(ArrayList<User> lsUs)
    {
        ArrayList<String>lsString=new ArrayList<>();
        for (User u:lsUs ) {
            String s = "Name: " + u.getName() + " -  " + "Age: "+String.valueOf(u.getAge());
            lsString.add(s);
        }
        return lsString;
    }

    public void loadDataForListView()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lsUser.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user =dataSnapshot.getValue(User.class);
                    lsUser.add(user);
                }
                dataListView = convertToArrString(lsUser);
                adapter=new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,dataListView);
                lvKq.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }










}