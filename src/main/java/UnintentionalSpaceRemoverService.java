import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class UnintentionalSpaceRemoverService {

    private Robot robot;

    private int count;
    private char previousKey;
    long previousKeyTime;

    public UnintentionalSpaceRemoverService() {
        try {
            robot = new Robot();
        }
        catch(Exception e){
            System.out.println("Something went wrong while initializing the robot");
        }

        // Initialize keyboardHook
        // Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

        System.out.println("Global keyboard hook successfully started");

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyPressed(GlobalKeyEvent event) {
//                System.out.println(event.getKeyChar());

                if (shouldKeyBeSuppressed(event.getKeyChar())) {
                    robot.keyPress(KeyEvent.VK_BACK_SPACE);
                    count++;
                    TrayIconHandler.updateTrayIconToolTip(count);
                }
            }
        });

        // Initialize TrayIconHandler
        TrayIconHandler.initialize();

    }

    private boolean shouldKeyBeSuppressed(char keyChar) {
        if (TrayIconHandler.isPaused()){
            return false;
        }
        boolean result = false;
        long timeDifference = System.currentTimeMillis() - previousKeyTime;

//        System.out.println("Previous key: " + (int)previousKey + "\nCurrent Key: " + (int)keyChar);
//        System.out.println("Time difference: " + Long.toString(timeDifference) + "\n");

        if(previousKey == ' '  && keyChar == ' ' &&  timeDifference <= 300) {
            result = true;
        }
        previousKey = keyChar;
        previousKeyTime = System.currentTimeMillis();

        return result;
    }

}
