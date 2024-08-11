package myown;

public class MLP {
	
	private double[] enters;
	private double[] hidden;
	private double outer;
	private double[][] wEH;
	private double[] wHO;
	private double[][] patterns = {
			{0,0}, {1,0}, {0,1}, {1,1}
	};
	private double[] answers = {0,1,1,0};
	
	MLP(){
		this.enters = new double[patterns[0].length];
		this.hidden = new double[2];
		this.wEH = new double[enters.length][hidden.length];
		this.wHO = new double[hidden.length];
		initWeights();
		int k = study();
		System.out.println(k);
		for(int p=0; p<patterns.length; p++) {
			for(int i=0; i<enters.length; i++) {
				enters[i]=patterns[p][i];
			}
			countOuter();
			System.out.println(outer);
		}
	}
	
	private void initWeights() {
		for(int i=0; i<wEH.length; i++) {
			for(int j=0; j<wEH[i].length; j++) {
				wEH[i][j] = Math.random()*0.2+0.1;
			}
		}
		for(int i=0; i<wHO.length; i++) {
			wHO[i] = Math.random()*0.2+0.1;
		}
	}
	
	private void countOuter() {
		for(int i=0; i<hidden.length; i++) {
			hidden[i]=0;
			for(int j=0; j<enters.length; j++) {
				hidden[i]+=enters[j]*wEH[j][i];
			}
			hidden[i] = (hidden[i] > 0.5) ? 1 : 0;
		}
		outer = 0;
		for(int i=0; i<hidden.length; i++) {
			outer+=hidden[i]*wHO[i];
		}
		outer = (outer > 0.5) ? 1 : 0;
	}
	
	private int study() {
		int iterations=0;
		double[] err = new double[hidden.length];
		double gError = 0;
		do {
			iterations++;
			gError = 0;
			for(int p=0; p<patterns.length; p++) {
				for(int i=0; i<enters.length; i++) {
					enters[i]=patterns[p][i];
				}
				countOuter();
				
				double lErr = answers[p] - outer;
				gError += Math.abs(lErr);
				
				for(int i=0; i<hidden.length; i++) {
					err[i] = lErr*wHO[i];
				}
				for(int i=0; i<enters.length; i++) {
					for(int j=0; j<hidden.length; j++) {
						wEH[i][j]+=0.1*err[j]*enters[i];
					}
				}
				
				for(int i=0; i<hidden.length; i++) {
					wHO[i]+=0.1*lErr*hidden[i];
				}
			}
			
		} while (gError!=0);
		return iterations;
	}

	public static void main(String[] args) {
		MLP mlp = new MLP();
	}

}
