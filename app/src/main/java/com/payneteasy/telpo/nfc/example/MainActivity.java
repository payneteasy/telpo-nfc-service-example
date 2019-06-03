package com.payneteasy.telpo.nfc.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.payneteasy.telpo.nfc.example.cardreader.CardReaderActivity;
import com.payneteasy.telpo.nfc.example.cardreader.ICardReaderView;

public class MainActivity extends Activity {

    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.resultView);
    }

    public void readCard(View view) {
        resultView.setText("");
        startActivityForResult(new Intent(this, CardReaderActivity.class), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String message;

        switch (resultCode) {
            case ICardReaderView.APPROVED:
                message = "APPROVED";
                break;

            case ICardReaderView.DECLINED:
                message = "DECLINED";
                break;

            case ICardReaderView.ERROR:
                message = "Error is " + data.getStringExtra("MESSAGE");
                break;

            default:
                message = "Unknown result code " + resultCode;
        }

        resultView.setText(message);
    }
}
