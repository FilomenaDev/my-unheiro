package com.filomenadeveloper.myunheiro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.filomenadeveloper.myunheiro.Comerciante.ui.Screen_Main_Salao;
import com.filomenadeveloper.myunheiro.Admin.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText ImpotEmailOrPhone,ImpotPassword;
    Button login;
    private ProgressDialog loading;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImpotEmailOrPhone = findViewById(R.id.login_phone_number_input);
        ImpotPassword = findViewById(R.id.login_password_input);
        login = findViewById(R.id.login_btn);
        loading = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    checkUserAccType();
                }
            }
        };
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        
    }

    private void LoginUser() {
        String EmailOrPhone = ImpotEmailOrPhone.getText().toString();
        String password = ImpotPassword.getText().toString();

     if (TextUtils.isEmpty(EmailOrPhone)){
        Toast.makeText(this,"Insira o Email ou Telafone", Toast.LENGTH_LONG).show();
    }if (TextUtils.isEmpty(password)){
        Toast.makeText(this,"Insira o Senha", Toast.LENGTH_LONG).show();
    }else {
            loading.setTitle("Verificando");
            loading.setMessage("Por favor aguarde...");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            AllowAccesstoAccount(EmailOrPhone,password);
            loading.dismiss();
        }
    }

    private void AllowAccesstoAccount(String emailOrPhone, String password) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child("customer");
        mAuth.signInWithEmailAndPassword(emailOrPhone, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                   Snackbar.make(findViewById(R.id.relative1), "erro de login", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkUserAccType(){
        final DatabaseReference mCustomerDatabase;
        final String userID;

        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("User").child("customer").child(userID);
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0){
                    Intent intent = new Intent(LoginActivity.this,ActivityDrawer.class);
                    startActivity(intent);
                    loading.dismiss();
                    finish();
                    return;
                }else {
                    final DatabaseReference CustomerDatabase;
                    CustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("comerciante").child(userID);
                    CustomerDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0) {
                                Intent intent = new Intent(LoginActivity.this, Screen_Main_Salao.class);
                                startActivity(intent);
                                finish();
                                return;
                            }else {
                                Intent inten = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(inten);
                                finish();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
