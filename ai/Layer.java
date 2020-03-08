package driver.ai;

import java.util.Arrays;
import java.util.Random;

public class Layer {

	private double[] output;
	private double[] input;
	private double[] weights;
	private double[] dWeights;
	private Random random;

	public Layer(int inputSize, int outputSize){
		output = new double[outputSize];
		input = new double[inputSize + 1];
		weights = new double[(1 + inputSize) * outputSize];
		dWeights = new double[weights.length];
		this.random = new Random();
		initWeights();
	}

	public void initWeights(){
		for (int i = 0; i < weights.length; i++){
			weights[i] = (random.nextDouble() - 0.5) * 4;
		}
	}

	public double[] run(double[] inputArray){
		
		System.arraycopy(inputArray, 0, input, 0, inputArray.length);
		input[input.length - 1] = 1; // bias
		int offset = 0;
		
		for (int i = 0; i < output.length; i++){
			for (int j = 0; j < input.length; j++){
				output[i] += weights[offset + j] * input[j];
			}
			output[i] = ActivationFunction.sigmoid(output[i]);
			offset += input.length;
		}
		
		return Arrays.copyOf(output, output.length);
	}

	public double[] train(double[] error, double learningRate, double momentum){
		
		int offset = 0;
		double[] nextError = new double[input.length];
		
		for (int i = 0; i < output.length; i++){
			
			double delta = error[i] * ActivationFunction.derivativeSigmoid(output[i]);
			
			// because we have a single hidden layer delta not change
			for (int j = 0; j < input.length; j++){
				int previousWeightIndex = offset + j;
				nextError[j] = nextError[j] + weights[previousWeightIndex] * delta;
				double dw = input[j] * delta * learningRate;
				weights[previousWeightIndex] += dWeights[previousWeightIndex] * momentum + dw;
				dWeights[previousWeightIndex] = dw;
			}
			
			offset += input.length;
		}
		
		return nextError;
	}
}
