package implementation;
import boundaryclasses.IGate;
import boundaryclasses.IHumidifier;
import boundaryclasses.IHumiditySensor;
import boundaryclasses.IManualControl;
import boundaryclasses.IOpticalSignals;
import boundaryclasses.IPump;
import fsm.IFSM;


public class FSMImplementation implements IFSM {
	private FSMState state;
	private IPump pumpA;
	private IPump pumpB;
	private IGate gate;
	private IOpticalSignals signals;
	private IHumiditySensor sensor;
	private IHumidifier humidifier;
	private IManualControl operatorPanel;
	private final double upperBound;
	private final double lowerBound;

	public FSMImplementation( IPump pumpA, IPump pumpB, IGate gate, IOpticalSignals signals,
			IHumidifier humidifier, IHumiditySensor sensor, IManualControl operatorPanel) {
		this.state = FSMState.HumidityOkay;
		this.pumpA = pumpA;
		this.pumpB = pumpB;
		this.gate = gate;
		this.signals = signals;
		this.sensor = sensor;
		this.humidifier = humidifier;
		this.operatorPanel = operatorPanel;
		upperBound = 60;
		lowerBound = 20;
	}
	
	public FSMState getState() {
		return state;
	}

	@Override
	public void evaluate(double temperatur)  {
			
			switch (state) {
			case HumidityOkay:
				if(temperatur<lowerBound){
					state=FSMState.HumidityMin;
					humidifier.sendSprayOn();
					signals.switchLampAOn();
				}
				if(temperatur>upperBound){
					state=FSMState.TorSchliesst;
					signals.switchLampBOn();
					gate.sendCloseGate();
				}
				break;
			case HumidityMin:
				if(temperatur>lowerBound){
					state=FSMState.HumidityOkay;
					signals.switchLampAOff();
					humidifier.sendSprayOff();
				}
				break;
			case TorSchliesst:
				if(gate.receivedGateClosed()){
					state=FSMState.PumpenAktivieren;
					signals.switchLampBOff();
				}
				break;
			case PumpenAktivieren:
				pumpA.sendActivate();
				pumpB.sendActivate();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(pumpA.receivedActivated() && pumpB.receivedActivated()){
					state=FSMState.PumpenAktiv;
				}else{
					state=FSMState.Error;
					pumpA.sendDeactivate();
					pumpB.sendDeactivate();
				}
				break;
			case PumpenAktiv:
				if(temperatur<upperBound){
					pumpA.sendDeactivate();
					pumpB.sendDeactivate();
					signals.switchLampBOn();
					gate.sendOpenGate();
					state=FSMState.TorOeffnet;
				}
				break;
			case Error:
				if(operatorPanel.receivedAcknowledgement()){
					state=FSMState.TorOeffnet;
					signals.switchLampBOn();
					gate.sendOpenGate();
				}
				break;
			case TorOeffnet:
				if(gate.receivedGateOpen()){
					state=FSMState.HumidityOkay;
					signals.switchLampBOff();
				}
				break;
			default:
				break;
			}
		}

}
