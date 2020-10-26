package com.tarun.launcher.utils;

import android.util.Log;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class InjectManager {

    private static final String TAG = "InjectManager";
    public static final int LAUNCH_APP = -1;

    public interface InjectedEventNotifier {
        void onReceiveEvent(int eventType, Object object);
    }

    private static InjectManager mgr;

    private final ConcurrentHashMap<Integer, Vector<InjectedEventNotifier>> mapOfListenersBasedOnEventType = new ConcurrentHashMap<>();


    private InjectManager() {

    }


    public static synchronized InjectManager getInstance() {
        if (mgr == null) {
            mgr = new InjectManager();
        }
        return mgr;
    }


    public void addListener(int type, InjectedEventNotifier listener) {
        if (listener == null) {
            Log.e(TAG, "Listener is null , so not adding");
            return;
        }
        Vector<InjectedEventNotifier> listOfListeners = mapOfListenersBasedOnEventType.get(type);
        if (listOfListeners == null) {
            listOfListeners = new Vector<>();
            mapOfListenersBasedOnEventType.put(type, listOfListeners);
        }
        listOfListeners.add(listener);
    }

    public void removeListener(int type, InjectedEventNotifier listener) {
        if (listener == null) {
            Log.e(TAG, "Listener is null , so can't return");
            return;
        }
        Vector<InjectedEventNotifier> listOfListeners = mapOfListenersBasedOnEventType.get(type);
        if (listOfListeners == null) {
            Log.e(TAG, "list of listeners are null so, simply return");
            return;
        }
        listOfListeners.remove(listener);
    }


    public void inject(int type, Object object) {
        Log.e(TAG, "inject: " + type);
        Vector<InjectedEventNotifier> listOfListeners = mapOfListenersBasedOnEventType.get(type);
        if (listOfListeners == null) {
            Log.e(TAG, "No one is interested in injected Event : " + type);
            return;
        }
        for (InjectedEventNotifier listener : listOfListeners) {
            listener.onReceiveEvent(type, object);
        }
    }
}
