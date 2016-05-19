package test;

import boundaryclasses.IGate;

public class GateStub implements IGate {

	@Override
	public void sendCloseGate() {
		System.out.println("Das Tor schlieﬂt jetzt.");

	}

	@Override
	public void sendOpenGate() {
		System.out.println("Das Tor offnet jetzt.");

	}

	@Override
	public boolean receivedGateClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receivedGateOpen() {
		// TODO Auto-generated method stub
		return false;
	}

}
