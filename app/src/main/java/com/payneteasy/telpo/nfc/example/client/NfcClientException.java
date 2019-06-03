package com.payneteasy.telpo.nfc.example.client;

public class NfcClientException extends Exception {

    private final int errorCode;

    public NfcClientException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
