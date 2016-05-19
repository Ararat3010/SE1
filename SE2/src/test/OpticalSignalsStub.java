package test;

import boundaryclasses.IOpticalSignals;

public class OpticalSignalsStub implements IOpticalSignals {

	@Override
	public void switchLampAOn() {
		System.out.println("Lampe A wird angeschaltet.");

	}

	@Override
	public void switchLampAOff() {
		System.out.println("Lampe A wird ausgeschaltet.");

	}

	@Override
	public void switchLampBOn() {
		System.out.println("Lampe B wird angeschaltet.");

	}

	@Override
	public void switchLampBOff() {
		System.out.println("Lampe B wird ausgeschaltet.");
	}

}
