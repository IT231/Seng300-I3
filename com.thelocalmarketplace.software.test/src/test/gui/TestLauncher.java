package test.gui;

import static org.junit.Assert.*;
import javax.swing.SwingUtilities;
import org.junit.Test;
import GUIcomponents.GuiForI3.Launcher;

public class TestLauncher {

    @Test
    public void testMainMethod() {
        Launcher.main(null);

        try {
            SwingUtilities.invokeAndWait(() -> {
            });
        } catch (Exception e) {
        }

        boolean adminGuiOpened = false;
        boolean startWindowOpened = false;


       }
}
