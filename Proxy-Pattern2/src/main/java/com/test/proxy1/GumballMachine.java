package com.test.proxy1;

import com.test.proxy1.remote.GumballMachineRemote;
import com.test.proxy1.state.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 糖果机
 */
public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
	String location;
 
	State noQuarterState;
	State hasQuarterState;
	State soldState;
	State soldOutState;
	State winnerState;

	State state = soldOutState;
	int count = 0;
 
	public GumballMachine(String location, int numberGumballs) throws RemoteException {
		noQuarterState = new NoQuarterState(this);
		hasQuarterState = new HasQuarterState(this);
		soldState = new SoldState(this);
		soldOutState = new SoldOutState(this);
		winnerState = new WinnerState(this);

		this.location = location;
		this.count = numberGumballs;
 		if (numberGumballs > 0) {
			state = noQuarterState;
		} 
	}
	public void insertQuarter() {
		state.insertQuarter();
	}
	public void ejectQuarter() {
		state.ejectQuarter();
	}
	public void turnCrank() {
		state.turnCrank();
		state.dispense();
	}
	public void setState(State state) {
		this.state = state;
	}
	public void releaseBall() {
		System.out.println("一颗糖果已发放！");
		if (count != 0) {
			count = count - 1;
		}
	}
	public int getCount() {
		return count;
	}
	void refill(int count) {
		this.count = count;
		state = noQuarterState;
	}
    public State getState() {
        return state;
    }
    public State getSoldOutState() {
        return soldOutState;
    }
    public State getNoQuarterState() {
        return noQuarterState;
    }
    public State getHasQuarterState() {
        return hasQuarterState;
    }
    public State getSoldState() {
        return soldState;
    }
	public State getWinnerState() {
		return winnerState;
	}
	public String getLocation() {
		return location;
	}
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("\n大白兔糖果");
		result.append("还有: " + count + " 颗");
		result.append("\n糖果机" + state + "\n");
		return result.toString();
	}
}