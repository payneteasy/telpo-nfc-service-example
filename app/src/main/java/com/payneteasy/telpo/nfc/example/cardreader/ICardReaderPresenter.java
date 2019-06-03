package com.payneteasy.telpo.nfc.example.cardreader;

import com.payneteasy.telpo.nfc.aidl.IRemoteNfcService;

public interface ICardReaderPresenter {

    void startReadingCard(IRemoteNfcService remoteNfcService);

}
