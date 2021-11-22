package lib;

import java.io.Serializable;


public class Generator implements Serializable {

	/**
	 * Automatisch generiert um den Generator speicherbar zu machen
	 */
	private static final long serialVersionUID = 1L;
	
	public int owned;
	public double baseCost;
	public double baseRevenue;
	public float baseProductionTimeInSeconds;
	public double costFactor;
	public double productionCycleInSeconds;
	
	public double nextBuildingCostsForOne;
	
	public double productionTimeInSeconds;
	
	
	public boolean canBeBuild(Session session) {
		return session.money>=nextBuildingCostsForOne;
	}
	
	public void build(Session session) {
		if (!canBeBuild(session)) {
			
		}
		
		owned++;
		session.money -= nextBuildingCostsForOne;
		
		precalculate();
	}
	
	public double produce(float deltaTimeInSeconds) {
		if (owned == 0) {
			return 0;
		}
		
		productionCycleInSeconds += deltaTimeInSeconds;
		
		double calculatedSum = 0;
		
		while (productionCycleInSeconds >= productionTimeInSeconds) {
			calculatedSum+=baseRevenue * owned;
			productionCycleInSeconds -= productionTimeInSeconds;
		}
		
		return calculatedSum;
	}
	
	private void updateNextBuildingCosts() {
		double kOverR= Math.pow(costFactor, owned);
		double kPlusOverR= Math.pow(costFactor, owned+1);
		
		nextBuildingCostsForOne = baseCost * 
				(
						(kOverR-kPlusOverR)
						/
						(1-costFactor)
				);
	}
	
	private void updateProductionTime() {
		productionTimeInSeconds = baseProductionTimeInSeconds;
		
		
//		kann genutzt werden um bei einer bestimmten anzahl der im besitz befindlichen generatoren die produktionszeit massiv zu reduzieren
//		if (owned>10) {
//			productionTimeInSeconds/= 2;
//		}
	}
	
	private void precalculate() {
		updateProductionTime();
		updateNextBuildingCosts();
	}
}
