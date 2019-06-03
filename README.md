[![CircleCI](https://circleci.com/gh/payneteasy/telpo-nfc-service-example.svg?style=svg)](https://circleci.com/gh/payneteasy/telpo-nfc-service-example)

# Telpo NFC service example

## How to build and run 

```bash
./gradlew assembleDebug
```

Do not forget to install *Kassatka NFC Service*

## Adding Telpo NFC service to your project

Add http://paynet-qa.clubber.me/reader/maven repo to your build.gradle file

```groovy
repositories {
    maven { url "http://paynet-qa.clubber.me/reader/maven" }
}
```

Add library to your build.gradle file

```groovy
dependencies {
    implementation "com.payneteasy.telpo-nfc-service-api:telpo-nfc-aidl:1.0-2";
}
```
## How to use NFC Service

### Using raw AIDL file

Add the following AIDL files from the http://paynet-qa.clubber.me/reader/maven/com/payneteasy/telpo-nfc-service-api/telpo-nfc-aidl/1.0-2
to your project into src/main/aidl
* IRemoteNfcService.aidl
* ByteArrayResponse.aidl 
* NfcResponse.aidl

### IRemoveNfcService class

You can use already generated IRemoveNfcService from the com.payneteasy.telpo-nfc-service-api:telpo-nfc-aidl library

### NfcClientImpl class

You can use NfcClientImpl from this example.

For example:
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
