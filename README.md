[![CircleCI](https://circleci.com/gh/payneteasy/telpo-nfc-service-example.svg?style=svg)](https://circleci.com/gh/payneteasy/telpo-nfc-service-example)

# Telpo NFC service example



## How to use NFC Service
```java
aNfcService.open();
try {
    byte[] cardInfo = aNfcService.waitForCard(10_000); //

    aNfcService.mifarePlusSl3Authenicate((byte) 8, KEY_TYPE, AES_KEY);

    byte[] data = aNfcService.mifarePlusSl3Read((byte) 1);
    processData(data);

} finally {
    aNfcService.close();
}
```

For more info please see CardReaderPresenterImpl.java
