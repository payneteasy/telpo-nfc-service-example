package com.payneteasy.telpo.nfc.example.cardreader;

import android.os.RemoteException;
import android.util.Log;

import com.payneteasy.telpo.nfc.aidl.ByteArrayResponse;
import com.payneteasy.telpo.nfc.aidl.IRemoteNfcService;
import com.payneteasy.telpo.nfc.aidl.NfcErrorCodes;
import com.payneteasy.telpo.nfc.aidl.NfcResponse;
import com.payneteasy.telpo.nfc.example.client.INfcClient;
import com.payneteasy.telpo.nfc.example.client.NfcClientException;
import com.payneteasy.telpo.nfc.example.client.NfcClientImpl;

public class CardReaderPresenterImpl implements ICardReaderPresenter {

    private static final String TAG = "nfc.CardReaderPresenter";

    private final ICardReaderView view;

    private static final byte[] AES_KEY = new byte[]{
              (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
            , (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
    };

    private static final byte KEY_TYPE = 0x0B;

    public CardReaderPresenterImpl(ICardReaderView aView) {
        view = aView;
    }

    @Override
    public void startReadingCard(final IRemoteNfcService aNfcService) {
        Log.d(TAG, "startReadingCard");
        new Thread(new Runnable() {
            @Override
            public void run() {
                readCard(new NfcClientImpl(aNfcService));
            }
        }).start();
    }

    private void readCard(INfcClient aNfcService) {
        try {

            view.showStatus("Service version: " + aNfcService.getVersion()
                    + "\nsession: " + aNfcService.getSessionId()
                    + "\n\nWaiting for card ...");


            aNfcService.open();
            try {
                byte[] cardInfo = aNfcService.waitForCard(10_000); //

                aNfcService.mifarePlusSl3Authenicate((byte) 8, KEY_TYPE, AES_KEY);

                byte[] data = aNfcService.mifarePlusSl3Read((byte) 1);
                processData(data);

            } finally {
                aNfcService.close();
            }

        } catch (RemoteException e) {
            Log.e(TAG, "Cannot invoke service method", e);
            view.onError("Cannot invoke remote method: " + e.getMessage());

        } catch (NfcClientException e) {
            Log.e(TAG, "NFC error", e);
            view.onError("Error " + e.getErrorCode() + ": " + e.getMessage());

        } catch (Exception e) {
            Log.e(TAG, "Unknown error", e);
            view.onError(e.getMessage());
        }
    }

    private void processData(byte[] data) {
        if(data != null && data.length > 0) {
            if(data[0] == 0x00) {
                view.onApproved();
            } else {
                view.onDeclined();
            }
        } else {
            view.onError("Data is too small");
        }
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
