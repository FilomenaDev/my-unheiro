package com.filomenadeveloper.myunheiro.ui.notifications;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.filomenadeveloper.myunheiro.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    TextView date,time;
    RadioGroup radioGroup;
    RadioButton salao,domicilio;
    Button mBtnDate,mBtnTime, BtnAgendar;
    Calendar myCalendar = Calendar.getInstance(Locale.getDefault());
    Calendar myTime = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener Dat;
    TimePickerDialog.OnTimeSetListener Time;
    CheckBox checkBoxMaos, checkBoxPes;
    private RadioRealButtonGroup mRadioGroup,mRadioGroup1;
    NotificationsFragment mContext = this ;
    String atendimento;
    private DatabaseReference mAgendarDatabese;
    private DatabaseReference mRefDatabese;
    private DatabaseReference mCustomerDatabese;
    private FirebaseAuth mAuth;
    private String userID;
    private String mName;
    private String mHora;
    private String mData;
    private String mMaos;
    private String mPes;
    private String mLocal;
    private int nPessoal;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        date = root.findViewById(R.id.textData);
        time = root.findViewById(R.id.textTime);
        mBtnDate = root.findViewById(R.id.btn_data);
        mBtnTime = root.findViewById(R.id.btn_time);
        BtnAgendar = root.findViewById(R.id.btn_agenda);
        checkBoxMaos = root.findViewById(R.id.maos);
        checkBoxPes = root.findViewById(R.id.pes);
        mRadioGroup = root.findViewById(R.id.radioRealButtonGroup);
        mRadioGroup1 = root.findViewById(R.id.radioRealButtonGroup1);
        radioGroup = root.findViewById(R.id.radioGrupo);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabese = FirebaseDatabase.getInstance().getReference().child("User").child("customer").child(userID);
        getUser();
        mAgendarDatabese = FirebaseDatabase.getInstance().getReference().child("Agendado");
        mRefDatabese = FirebaseDatabase.getInstance().getReference().child("Agendado");

        final int ano = myCalendar.get(Calendar.YEAR);
        final int mes = myCalendar.get(Calendar.MONTH);
        final int dia = myCalendar.get(Calendar.DATE);
        final int hora = myCalendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = myCalendar.get(Calendar.MINUTE);

        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dat = android.text.format.DateFormat.format("dd/MM/yyyy",myCalendar).toString();
                    date.setText(dat);
                    }
                },dia,mes+1,ano);

                datePickerDialog.show();

            }
        });

        mBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time.setText(hourOfDay+":"+minute);
                    }
                },hora,minuto,false);
                timePickerDialog.show();
            }
        });

        checkBoxPes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxPes.isChecked()){
                    mRadioGroup1.setVisibility(View.VISIBLE);
                    mRadioGroup1.setPosition(0);
                }else {
                    mRadioGroup1.setVisibility(View.GONE);
                    mRadioGroup1.setPosition(-1);
                }
            }
        });

        checkBoxMaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxMaos.isChecked()){
                    mRadioGroup.setVisibility(View.VISIBLE);
                    mRadioGroup.setPosition(0);
                }else {
                    mRadioGroup.setVisibility(View.GONE);
                    mRadioGroup.setPosition(-1);
                }
            }
        });
        
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton radioButton = (RadioButton)radioGroup.findViewById(i);
                switch (i){
                    case R.id.salao:
                        atendimento = "Salao";
                        break;
                    case R.id.domicilio:
                        View mDialogView = LayoutInflater.from(mContext.getContext()).inflate(R.layout.alert_dialog,null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getContext()).setView(mDialogView).setTitle("Bonos Familia");
                        AlertDialog mAlertDialog = builder.show();
                        TextView quante = mDialogView.findViewById(R.id.quant);
                        Button canelar = mDialogView.findViewById(R.id.BtnCancelar);
                        ImageButton MaisButton = mDialogView.findViewById(R.id.alet_button1);
                        ImageButton MeButton = mDialogView.findViewById(R.id.alet_button);

                        MeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nPessoal = 1;
                                mAlertDialog.dismiss();
                            }
                        });

                        MaisButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quante.setVisibility(View.VISIBLE);
                                canelar.setText("Ok");
                            }
                        });
                        canelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(canelar.getText().equals("Ok")){
                                    nPessoal = Integer.parseInt(quante.getText().toString());
                                    mAlertDialog.dismiss();
                                }else {

                                    mAlertDialog.dismiss();
                                }
                            }
                        });
                        
                        atendimento = "Domicilio";
                        break;
                }
            }
        });



        BtnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = date.getText().toString();
                mHora = time.getText().toString();
                mLocal = atendimento;
                final   String UnhaType;
                final String PeType;
                final int selected = mRadioGroup.getPosition();
                final int selected1 = mRadioGroup1.getPosition();

                switch (selected){
                    case 0:
                        UnhaType="Gelinho";
                        break;
                    case 1:
                        UnhaType="Gel";
                        break;
                    case 2:
                        UnhaType="Normal";
                        break;
                    case 3:
                        UnhaType="Noiva";
                        break;
                    default:
                        UnhaType="";

                }

                switch (selected1){
                    case 0:
                        PeType="Normal";
                        break;
                    case 1:
                        PeType="Gelinho";
                        break;
                    case 2:
                        PeType="Gel";
                        break;
                    case 3:
                        PeType="Noiva";
                        break;

                    default:
                        PeType="";
                }


                Map map = new HashMap();
                map.put("userID", userID);
                map.put("nome", mName);
                map.put("maos", UnhaType);
                map.put("pes", PeType);
                map.put("data", mData);
                map.put("hora", mHora);
                map.put("local", mLocal);
                map.put("pessoal", nPessoal);
                mAgendarDatabese.push().setValue(map);
                Snackbar.make(root, "Agendado com sucesso", Snackbar.LENGTH_SHORT).show();
                
            }
        });

        return root;
    }

    private void getUser(){
        mCustomerDatabese.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name") != null) {
                        mName = map.get("name").toString();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}