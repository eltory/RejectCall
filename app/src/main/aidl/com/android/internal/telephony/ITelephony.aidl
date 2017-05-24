// ITelephony.aidl
package com.android.internal.telephony;

// Declare any non-default types here with import statements

interface ITelephony {
    /**
     * ITelephony is to communicate with the other process which is being operated in background.
     * 
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    boolean endCall();
    void answerRingingCall();
    void silenceRinger();

}
