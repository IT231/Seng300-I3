package com.thelocalmarketplace.software;

import java.math.BigDecimal;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderListener;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;

public class PayWithCardSwipe{
	private double amountToPay;
	private CardIssuer cardIssuer;
	private Card card;
	boolean notified;
	private CardReaderObserver payment;
	AbstractSelfCheckoutStation scs;
	
	
	public PayWithCardSwipe(IcardReader cardReader, CardReaderObserver payment) {
		cardReadListener cardListener = new cardReadListener();
		scs.cardReader.register(cardListener);
		amountToPay = payment.getAmountToPay().doubleValue();
		this.payment = payment;
		
	}
	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aCardHasBeenSwiped() {
		notified = false;
		// TODO Auto-generated method stub
	}
	public void theDataFromACardHasBeenRead(CardData data) {
		card = new Card(data.getType(), data.getNumber(), data.getCardholder(), data.getCVV());
	}
	
	@Override
	public void aCardHasBeenInserted(){
		// TODO Auto-generated method stub
	}
	@Override
	public void theCardHasBeenRemoved() {
		// TODO Auto-generated method stub
	}
	@Override
	public void aCardHasBeenTapped() {
		// TODO Auto-generated method stub
	}
	public boolean paymentCredit(CardIssuer issuer, BigDecimal holdAmount, BigDecimal purchaseAmount){
        // if statment to confirm a card has been read and is a valid debit card
        if ((CardReaderListener.isInserted() || CardReaderListener.isSwiped() || CardReaderListener.isTapped()) && CardReaderListener.isCardDataRead())
        {
            // get hold number, if it is -1 the hold failed
            int holdNumber = issuer.authorizeHold(CardReaderListener.getCardNumber(), holdAmount);
            if (holdNumber != -1)
            {
                // check if purchase was successful
                if (issuer.postTransaction(CardReaderListener.getCardNumber(), holdNumber, purchaseAmount))
                {
                    return true;
                }
            }
        }
        return false;

    }
	
}


















