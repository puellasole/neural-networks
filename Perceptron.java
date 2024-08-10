package myown;

import java.util.Arrays;

public class Perceptron {
	private double[] enters;
	private double outer;
	private double[] weights;
	
	private double[][] patterns = {
			{0,0,0}, {0,1,1}, {1,0,1}, {1,1,1}
	};
	
	Perceptron(){
		this.enters = new double[2];
		this.weights = new double[enters.length];
		for(int i=0; i<enters.length; i++) {
			weights[i] = Math.random() * 0.2 + 0.1;
		}
	}
	
	private void countOuter() {
		outer = 0;
		for(int i=0; i<enters.length; i++) {
			outer+=enters[i]*weights[i];
		}
		outer = (outer > 0.5) ? 1 : 0;
	}
	
	private int study() {
		double gError = 0;
		int interractions = 0;
		do {
			interractions++;
			gError = 0;
			for(int p=0; p<patterns.length; p++) {
				enters = Arrays.copyOf(patterns[p], patterns[p].length-1);
				countOuter();
				double error = patterns[p][2] - outer;
				gError += Math.abs(error);
				for(int i=0; i<enters.length; i++) {
					weights[i]+=0.1*error*enters[i];
				}
			}
		} while (gError!=0);
		return interractions;
	}
	
	private void test() {
		int k = study();
		System.out.println(k);
		for(int p=0; p<patterns.length; p++) {
			enters = Arrays.copyOf(patterns[p], patterns[p].length-1);
			countOuter();
			System.out.println(outer);
		}
	}

	public static void main(String[] args) {
		new Perceptron().test();
	}

}
