package com.thelocalmarketplace.software.receipt;

import java.util.ArrayList;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.printer.ReceiptPrinterListener;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

/**
 * 
 * @author 
 *
 */
public class PrintReceipt {
	
	public ArrayList<ReceiptPrinterListener> listeners = new ArrayList<>();
	private boolean isOutOfPaper = false; //Flag for running out of paper, set to false in default
	private boolean isOutOfInk = false; //Flag for running out of ink, set to false in default
	private boolean isLowOnPaper = false; //Flag for running low on paper, set to false in default
	private boolean isLowOnInk = false; //Flag for running low on ink, set to false in default
	private boolean duplicateNeeded = false; //Flag for if the receipt was not printed out fully and a duplicate is needed.
	private String receipt; //The receipt that should be printed
	private IReceiptPrinter printer; //The printer associated with the session;

	
	/**
     * Constructor that initializes the funds and registers an inner listener to the self-checkout station.
     * 
     * @param scs The self-checkout station
     */
    public PrintReceipt (AbstractSelfCheckoutStation scs) {
        if (scs == null) {
            throw new IllegalArgumentException("SelfCheckoutStation should not be null.");
        }
        InnerListener listener = new InnerListener();
        scs.printer.register(listener);
        this.printer = scs.printer;
    }
    
    /**
     * Inner class to listen for valid coin additions and update the paid amount.
     */
    public class InnerListener implements ReceiptPrinterListener {

		@Override
		public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		}

		@Override
		public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		}

		@Override
		public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		}

		@Override
		public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		}

		@Override
		public void thePrinterIsOutOfPaper() {
			isOutOfPaper = true;
			duplicateNeeded = true;
		}

		@Override
		public void thePrinterIsOutOfInk() {
			isOutOfInk = true;
			duplicateNeeded = true;
		}

		@Override
		public void thePrinterHasLowInk() {
		}

		@Override
		public void thePrinterHasLowPaper() {
		}

		@Override
		public void paperHasBeenAddedToThePrinter() {
			isOutOfPaper = false;
			for(ReceiptPrinterListener l : listeners) {
				l.paperHasBeenAddedToThePrinter();
			}
			if (duplicateNeeded) {
				// start printing duplicate copy of the receipt again if one is needed
				print();
			}
		}

		@Override
		public void inkHasBeenAddedToThePrinter() {
			isOutOfInk = false;
			for(ReceiptPrinterListener l : listeners) {
				l.inkHasBeenAddedToThePrinter();
			}
			if (duplicateNeeded) {
				// start printing duplicate copy of the receipt again if one is needed
				print();
			}
		}
    }
    
    public void printReceipt(String formattedOrder) {
    	receipt = formattedOrder;
    	this.print();
    }
    
    private void print() {
    	try {
    		printer.print('\n'); // Ensures any new receipt being printed starts on a fresh line
        	for (int i = 0, n = receipt.length() ; i < n ; i++) {
        		// Notify and break out of the printing loop if out of paper or ink
        		if (isOutOfPaper) {
        			for(ReceiptPrinterListener l : listeners) {
        				l.thePrinterIsOutOfPaper();
        			}
        			throw new EmptyDevice("Out of Paper");
        		}
        		if (isOutOfInk) {
        			for(ReceiptPrinterListener l : listeners) {
        				l.thePrinterIsOutOfInk();
        			}
        			throw new EmptyDevice("Out of Ink");
        		}
        		if (isLowOnPaper) {
        			for(ReceiptPrinterListener l : listeners) {
        				l.thePrinterHasLowPaper();
        			}
        			throw new EmptyDevice("Out of Paper");
        		}
        		if (isLowOnInk) {
        			for(ReceiptPrinterListener l : listeners) {
        				l.thePrinterHasLowInk();
        			}
        			throw new EmptyDevice("Out of Ink");
        		}
        		// Send the character to the printer to print
    			printer.print(receipt.charAt(i));
        	}
        	
        // The empty device exception is thrown within the loop when the printer is out of paper or ink
    	} catch (EmptyDevice e) {
			System.err.println("There is either no ink or no paper in the printer");
		} catch (OverloadedDevice e) {
			System.err.println("The line is too long. Add a newline");
		}
    }
    
    
    
    /**
     * Methods for adding listeners to the PrintReceipt
     */
	public synchronized boolean deregister(ReceiptPrinterListener listener) {
		return listeners.remove(listener);
	}

	public synchronized void deregisterAll() {
		listeners.clear();
	}

	public final synchronized void register(ReceiptPrinterListener listener) {
		listeners.add(listener);
	}
}
