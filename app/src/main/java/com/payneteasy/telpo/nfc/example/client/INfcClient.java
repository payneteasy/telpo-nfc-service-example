package com.payneteasy.telpo.nfc.example.client;

import android.os.RemoteException;

public interface INfcClient {

    void open() throws NfcClientException, RemoteException;

    void close() throws NfcClientException, RemoteException;

    byte[] waitForCard(int aTimeoutMs) throws NfcClientException, RemoteException;

    void mifarePlusSl3Authenicate(byte aBlockNumber, byte aKeyType, byte[] aAesKey) throws NfcClientException, RemoteException;

    byte[] mifarePlusSl3Read(byte aBlockNumber) throws NfcClientException, RemoteException;

    void mifarePlusSl3Write(byte aBlockNumber, byte[] aData) throws NfcClientException, RemoteException;

    String getVersion() throws RemoteException;

    String getSessionId() throws RemoteException;
}
