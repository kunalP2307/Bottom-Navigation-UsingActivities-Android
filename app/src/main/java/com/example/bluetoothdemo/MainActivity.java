package com.example.bluetoothdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    TextView textViewBTStatus,textViewPairedDev;
    Button btnOn, btnOff,btnShowPaired;
    Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBTStatus = findViewById(R.id.text_BT_status);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        showBtStatus();
        bindComponents();
        addListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showBtStatus();
    }

    public boolean getBtStatus(){
        return bluetoothAdapter.isEnabled();
    }



    public void showBtStatus(){
        if(getBtStatus()){
            textViewBTStatus.setTextColor(Color.BLUE);
            textViewBTStatus.setText("Bluetooth is ON : "+bluetoothAdapter.getName());
        }
        else{
            textViewBTStatus.setTextColor(Color.RED);
            textViewBTStatus.setText("Bluetooth is OFF");
        }
    }

    public void addListeners(){
        this.btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bluetoothAdapter.isEnabled()){
                    Intent turnOnBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOnBT, 0);
                    showBtStatus();
                }
            }
        });
        this.btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.disable();
                showBtStatus();
                showPairedDevices();
            }
        });
        this.btnShowPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPairedDevices();
            }
        });
    }

    public void showPairedDevices(){
        String strpairedDevices = "";
        pairedDevices = bluetoothAdapter.getBondedDevices();

        ArrayList list = new ArrayList();

        for(BluetoothDevice bt : pairedDevices) {
            list.add(bt.getName());
            strpairedDevices += bt.getName();
        }
        textViewPairedDev.setText(strpairedDevices);
    }
    public void bindComponents(){
        this.btnOff = findViewById(R.id.btn_off);
        this.btnOn = findViewById(R.id.btn_on);
        this.btnShowPaired = findViewById(R.id.btn_show_paired);
        this.textViewPairedDev = findViewById(R.id.text_paired_dev);
    }
}