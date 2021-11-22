package lib;

import java.math.MathContext;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Session {
	public LinkedList<Generator> generators;
	public double money;
	
	public void tick(float deltaTimeInSeconds) {
		money += calculateProgression(deltaTimeInSeconds);
	}
	
	private double calculateProgression(float deltaTimeInSeconds) {
		double sum = 0;
		
		if (!generators.isEmpty()) {
			for (Generator generator : generators) {
				sum +=generator.produce(deltaTimeInSeconds);
			}	
		}
		return  sum;
	}
}
