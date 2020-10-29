package com.work_timer;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;


public class ForegroundWindow {
    private static final int MAX_TITLE_LENGTH = 1024;
    

    public  static String getNameOfCurrentWindow () throws Exception {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);        
        return Native.toString(buffer);        
    }
 }