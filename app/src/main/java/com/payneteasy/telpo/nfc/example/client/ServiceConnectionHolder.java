package com.payneteasy.telpo.nfc.example.client;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.payneteasy.telpo.nfc.aidl.IRemoteNfcService;
import com.payneteasy.telpo.nfc.example.cardreader.ICardReaderPresenter;

public class ServiceConnectionHolder implements ServiceConnection {

    private static final String TAG = "nfc.ServiceConnection";

    private IRemoteNfcService remoteNfcService;
    private final ICardReaderPresenter presenter;

    public ServiceConnectionHolder(ICardReaderPresenter aPresenter) {
        presenter = aPresenter;
    }

    @Override
    public void onServiceConnected(ComponentName aName, IBinder aService) {
        Log.i(TAG, "onServiceConnected " + aName);
        remoteNfcService = IRemoteNfcService.Stub.asInterface(aService);
        presenter.startReadingCard(remoteNfcService);
    }

    @Override
    public void onServiceDisconnected(ComponentName aName) {
        Log.i(TAG, "onServiceDisconnected " + aName);
    }

    @Override
    public void onBindingDied(ComponentName aName) {
        Log.w(TAG, "onBindingDied " + aName);

    }

    @Override
    public void onNullBinding(ComponentName aName) {
        Log.w(TAG, "onNullBinding " + aName);
    }
}
