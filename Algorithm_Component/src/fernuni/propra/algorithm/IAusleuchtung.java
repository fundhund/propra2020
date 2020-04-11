package fernuni.propra.algorithm;

public interface IAusleuchtung {
	
	public abstract boolean validateSolution(String xmlFile);
	
	public abstract int solve(String xmlFile, int timeLimit);

}
