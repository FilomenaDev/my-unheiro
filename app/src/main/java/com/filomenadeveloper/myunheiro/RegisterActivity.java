package com.filomenadeveloper.myunheiro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.filomenadeveloper.myunheiro.Comerciante.ui.Screen_Main_Salao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.Utils;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText ImpotName, ImpotEmailOrPhone, ImpotPassword;
    Button CreateAccontButton;
    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    checkUserAccType();
                }
            }
        };

        ImpotName = findViewById(R.id.register_user_text_input);
        ImpotEmailOrPhone = findViewById(R.id.register_phone_number_input);
        ImpotPassword = findViewById(R.id.register_password_input);
        CreateAccontButton = findViewById(R.id.register_btn);
        loading = new ProgressDialog(this);

        CreateAccontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccont();
            }
        });
    }

    private void CreateAccont() {
        String name = ImpotName.getText().toString();
        String EmailOrPhone = ImpotEmailOrPhone.getText().toString();
        String password = ImpotPassword.getText().toString();

        if (TextUtils.isEmpty(name)){
           Toast.makeText(this,"Insira o Nome", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(EmailOrPhone)){
            Toast.makeText(this,"Insira o Email ou Telafone", Toast.LENGTH_LONG).show();
        }if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Insira o Senha", Toast.LENGTH_LONG).show();
        }else {
            loading.setTitle("Criar Conta");
            loading.setMessage("Por favor aguarde...");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            ValidarTelefne(name,EmailOrPhone,password);
        }
    }

    private void ValidarTelefne(String name, String EmailOrPhone, String password) {
        mAuth.createUserWithEmailAndPassword(EmailOrPhone, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Snackbar.make(findViewById(R.id.layoutRegister), "Email ou Senha errada", Snackbar.LENGTH_LONG).show();
                    loading.dismiss();

                } else {
                    String user_id = mAuth.getCurrentUser().getUid();
                    Map userInfo = new HashMap();
                    userInfo.put("id", user_id);
                    userInfo.put("name", name);
                    userInfo.put("email",EmailOrPhone);
                    userInfo.put("imagem", "default");
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User").child("admin").child(user_id).child("name");
                    DatabaseReference current_user = FirebaseDatabase.getInstance().getReference().child("User").child("admin").child(user_id).child("email");
                    current_user_db.setValue(name);
                    current_user.setValue(EmailOrPhone);
                    loading.dismiss();
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
                    Intent intent = new Intent(RegisterActivity.this,ActivityDrawer.class);
                    startActivity(intent);
                    finish();
                    return;
                }else {
                    Intent intent = new Intent(RegisterActivity.this, Screen_Main_Salao.class);
                    startActivity(intent);
                    finish();
                    return;
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
         //   reload();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}