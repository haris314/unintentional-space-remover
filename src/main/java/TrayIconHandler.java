import lombok.Getter;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

public class TrayIconHandler {

    static PopupMenu popup;
    private static TrayIcon trayIcon;

    @Getter
    private static boolean paused = false;

    private TrayIconHandler(){}
    public static void initialize() {

        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        initializePopup();

        // Get the thumbnail icon image
        Image image = Toolkit.getDefaultToolkit().getImage(TrayIconHandler.class.getResource("icon.jpg"));
        System.out.println("Image is ");
        System.out.println(image);

        // Initialize the tray icon and add popup menu in the tray icon
        trayIcon = new TrayIcon(image, MESSAGE_STRING + 0);
        trayIcon.setPopupMenu(popup);

        // Finally, add the tray icon to the system tray
        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

    /**
     * Initialize popup menu items
     * Set their event listeners
     * Add menu items in the popup
     */
    public static void initializePopup(){

        popup = new PopupMenu();

        // Initialize menu items
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem pauseItem = new MenuItem("Pause");
        MenuItem resumeItem = new MenuItem("Resume");

        // Exit listener
        exitItem.addActionListener(arg0 -> System.exit(0));

        // Pause listener
        pauseItem.addActionListener(arg0 -> {
            paused = true;
            pauseItem.setEnabled(false);
            resumeItem.setEnabled(true);
        });

        // Resume listener
        resumeItem.addActionListener(arg0 -> {
            paused = false;
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(false);
        });

        resumeItem.setEnabled(false);

        popup.add(resumeItem);
        popup.add(pauseItem);
        popup.add(exitItem);
    }

    public static void updateTrayIconToolTip(int count) {
        trayIcon.setToolTip(MESSAGE_STRING + count);
    }

    final static String MESSAGE_STRING = "Unintentional Space Remover\nSpaces removed: ";
}
