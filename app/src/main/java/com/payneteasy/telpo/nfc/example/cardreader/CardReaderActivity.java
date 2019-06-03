package com.payneteasy.telpo.nfc.example.cardreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.payneteasy.telpo.nfc.example.R;
import com.payneteasy.telpo.nfc.example.client.ServiceConnectionHolder;

import java.lang.ref.WeakReference;

public class CardReaderActivity extends Activity implements ICardReaderView {

    private final String TAG = "nfc.CardReaderActivity";

    private TextView statusView;
    private ICardReaderPresenter presenter;
    private ServiceConnectionHolder connectionHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_reader);

        statusView = findViewById(R.id.statusView);

        presenter = new CardReaderPresenterImpl(new CardReaderSafeView(new WeakReference<ICardReaderView>(this)));

        bindService();

    }

    private void bindService() {
        connectionHolder = new ServiceConnectionHolder(presenter);

        Intent intent = new Intent("com.payneteasy.telpo.nfc.service.RemoteNfcService.BIND")
                .setPackage("com.payneteasy.telpo.nfc.service");
        
        if(bindService(intent, connectionHolder, Context.BIND_AUTO_CREATE)) {
            Log.d(TAG, "Service bounded");
        } else {
            Log.e(TAG, "Cannot bind service");
            onError("Cannot bind service");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(connectionHolder);
    }

    @Override
    public void showStatus(final String aMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusView.setText(aMessage);
            }
        });
    }

    @Override
    public void onApproved() {
        Log.d(TAG, "onApproved");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setResult(APPROVED);
                finish();
            }
        });
    }

    @Override
    public void onDeclined() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setResult(DECLINED);
                finish();
            }
        });
    }

    @Override
    public void onError(final String aMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setResult(ERROR, new Intent().putExtra("MESSAGE", aMessage));
                finish();
            }
        });
    }
}
