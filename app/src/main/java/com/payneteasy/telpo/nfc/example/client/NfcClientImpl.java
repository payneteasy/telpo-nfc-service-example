package com.payneteasy.telpo.nfc.example.client;

import android.os.RemoteException;

import com.payneteasy.telpo.nfc.aidl.ByteArrayResponse;
import com.payneteasy.telpo.nfc.aidl.IRemoteNfcService;
import com.payneteasy.telpo.nfc.aidl.NfcErrorCodes;
import com.payneteasy.telpo.nfc.aidl.NfcResponse;

public class NfcClientImpl implements INfcClient {

    private final IRemoteNfcService target;

    public NfcClientImpl(IRemoteNfcService remoteNfcService) {
        target = remoteNfcService;
    }

    @Override
    public String getVersion() throws RemoteException {
        return target.getVersion();
    }

    @Override
    public String getSessionId() throws RemoteException {
        return target.getSessionId();
    }

    @Override
    public void open() throws NfcClientException, RemoteException {
        checkResult(target.open());
    }

    @Override
    public void close() throws NfcClientException, RemoteException {
        checkResult(target.close());
    }

    @Override
    public byte[] waitForCard(int aTimeoutMs) throws NfcClientException, RemoteException {
        return checkResult(target.waitForCard(aTimeoutMs));
    }

    @Override
    public void mifarePlusSl3Authenicate(byte aBlockNumber, byte aKeyType, byte[] aAesKey) throws NfcClientException, RemoteException {
        checkResult(target.mifarePlusSl3Authenicate(aBlockNumber, aKeyType, aAesKey));
    }

    @Override
    public byte[] mifarePlusSl3Read(byte aBlockNumber) throws NfcClientException, RemoteException {
        return checkResult(target.mifarePlusSl3Read(aBlockNumber));
    }

    @Override
    public void mifarePlusSl3Write(byte aBlockNumber, byte[] aData) throws NfcClientException, RemoteException {
        checkResult(target.mifarePlusSl3Write(aBlockNumber, aData));
    }

    private byte[] checkResult(ByteArrayResponse aByteResponse) {
        int errorCode = aByteResponse.getResultCode();
        if(errorCode != NfcErrorCodes.NFC_RESULT_OK) {
            throw new IllegalStateException("Error code " + errorCode + ": " + aByteResponse.getErrorMessage());
        }
        return aByteResponse.getData();
    }

    private void checkResult(NfcResponse aByteResponse) {
        int errorCode = aByteResponse.getResultCode();
        if(errorCode != NfcErrorCodes.NFC_RESULT_OK) {
            throw new IllegalStateException("Error code " + errorCode + ": " + aByteResponse.getErrorMessage());
        }
    }

}
