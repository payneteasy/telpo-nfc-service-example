package com.payneteasy.telpo.nfc.example.cardreader;

public interface ICardReaderView {

    int APPROVED = 1000;
    int DECLINED = 1001;
    int ERROR    = 1002;

    void showStatus(String aMessage);

    void onApproved();

    void onDeclined();

    void onError(String aMessage);
}
