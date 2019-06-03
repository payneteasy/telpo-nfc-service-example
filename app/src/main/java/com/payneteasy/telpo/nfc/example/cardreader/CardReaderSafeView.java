package com.payneteasy.telpo.nfc.example.cardreader;

import android.util.Log;

import java.lang.ref.WeakReference;

public class CardReaderSafeView implements ICardReaderView {

    private static final String TAG = "nfc.CardReaderSafeView";

    private final WeakReference<ICardReaderView> ref;

    public CardReaderSafeView(WeakReference<ICardReaderView> aRef) {
        ref = aRef;
    }

    @Override
    public void showStatus(final String aMessage) {
        invoke(new IViewInvoker() {
            @Override
            public void invoke(ICardReaderView aView) {
                aView.showStatus(aMessage);
            }
        });
    }

    private void invoke(IViewInvoker aInvoker) {
        ICardReaderView view = ref.get();
        if(view == null) {
            Log.d(TAG, "View is null");
        } else {
            aInvoker.invoke(view);
        }

    }

    @Override
    public void onApproved() {
        invoke(new IViewInvoker() {
            @Override
            public void invoke(ICardReaderView aView) {
                aView.onApproved();
            }
        });
    }

    @Override
    public void onDeclined() {
        invoke(new IViewInvoker() {
            @Override
            public void invoke(ICardReaderView aView) {
                aView.onDeclined();
            }
        });
    }

    @Override
    public void onError(final String aMessage) {
        invoke(new IViewInvoker() {
            @Override
            public void invoke(ICardReaderView aView) {
                aView.onError(aMessage);
            }
        });
    }

    private interface IViewInvoker {
        void invoke(ICardReaderView aView);
    }
}
