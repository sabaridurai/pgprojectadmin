package com.example.transportadmin;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Objects;

public class viewcarddata extends AppCompatActivity {
    private ImageView qrimg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcarddata);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){

            //
            }
        firebaseAuth=FirebaseAuth.getInstance();
        qrimg=findViewById(R.id.qrcodeimg);
        Button generatebtn = findViewById(R.id.generateQR);
        generatebtn.setOnClickListener(view -> {
            if(firebaseAuth.getCurrentUser()!=null) {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try {
                   // Toast.makeText(viewcarddata.this, firebaseAuth.getUid(), Toast.LENGTH_SHORT).show();
                    BitMatrix bitMatrix = qrCodeWriter.encode(Objects.requireNonNull(firebaseAuth.getUid()), BarcodeFormat.QR_CODE, 200, 200);
                    Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
                    for (int x = 0; x < 200; x++) {
                        for (int y = 0; y < 200; y++) {
                            bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    qrimg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(viewcarddata.this, "Did not catch", Toast.LENGTH_SHORT).show();
            }

        });


    }
}