package com.example.appiot;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button btncong,btntru;
    ProgressBar progressBar;
    TextView textprogress, nhietdo;
    ImageButton denxanh,denvang,dendo;
    int val = 0;
    int valprogress = 0;
    int[] backimage;

    boolean red = true;
    boolean green = true;
    boolean yellow = true;


    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btncong = findViewById(R.id.button_cong);
        btntru = findViewById(R.id.button_tru);
        progressBar = findViewById(R.id.progress_bar);
        textprogress = findViewById(R.id.textprogress);
        nhietdo = findViewById(R.id.nhietdo);

        dendo = findViewById(R.id.dendo);
        denvang = findViewById(R.id.denvang);
        denxanh = findViewById(R.id.denxanh);

        backimage = new int[]{R.drawable.elipgray, R.drawable.elipgreen, R.drawable.elipyellow, R.drawable.elipred};


        textprogress.setText("0");


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("NhietDo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue().toString();
                nhietdo.setText(Temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Red").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean r = (boolean) dataSnapshot.getValue();
                if (r == true ){
                    dendo.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[3]));
                } else {
                    dendo.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[0]));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Green").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean g = (boolean) dataSnapshot.getValue();
                if (g == true ){
                    denxanh.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[1]));
                } else {
                    denxanh.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[0]));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Yellow").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean y = (boolean) dataSnapshot.getValue();
                if (y == true ){
                    denvang.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[2]));
                } else {
                    denvang.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[0]));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        denxanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (green == true ){
                    green = false;
                    denxanh.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[0]));
                } else if (green == false){
                    green = true;
                    denxanh.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[1]));
                }
                updateRGB();
            }
        });

        denvang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yellow == true ){
                    yellow = false;
                    denvang.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[0]));
                } else if (yellow == false){
                    yellow = true;
                    denvang.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[2]));
                }
                updateRGB();
            }
        });

        dendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (red == true ){
                    red = false;
                    dendo.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[0]));
                } else if (red == false){
                    red = true;
                    dendo.setBackground(ContextCompat.getDrawable(getApplicationContext(), backimage[3]));
                }
                updateRGB();
            }
        });

        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( val <= 324 && valprogress <= 100){
                    val += 36;
                    valprogress += 10;
                    update();
                }
            }

        });

        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( val >= 36 && valprogress >= 10){
                    val -= 36;
                    valprogress -= 10;
                    update();
                }
            }
        });

    }

    private void update(){
        progressBar.setProgress(valprogress);
        textprogress.setText(String.valueOf(val));
        databaseReference.child("Servo").setValue(val);
    }

    private  void  updateRGB(){
        databaseReference.child("Red").setValue(red);
        databaseReference.child("Green").setValue(green);
        databaseReference.child("Yellow").setValue(yellow);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.child("Servo").setValue(0);
    }
}