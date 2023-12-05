package test.gui;

import GUIcomponents.GuiForI3.Simulation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSimulation {

    @Before
    public void setUp() {
        Simulation.start(); 
    }

    @Test
    public void testStart() {
        assertNotNull(Simulation.station);
        assertNotNull(Simulation.cardIssuer);
        assertNotNull(Simulation.systemManager);
        assertNotNull(Simulation.payman);
        assertNotNull(Simulation.orderManager);
    }
}
