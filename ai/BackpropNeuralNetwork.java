package driver.ai;

public class BackpropNeuralNetwork {
	private Layer[] layers;

	public BackpropNeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
		layers = new Layer[] { new Layer(inputSize, hiddenSize), new Layer(hiddenSize, outputSize) };
	}

	public Layer getLayer(int index) {
		return layers[index];
	}

	public double[] run(double[] input) {
		double[] inputActivation = input;
		for (int i = 0; i < layers.length; i++) {
			inputActivation = layers[i].run(inputActivation);
		}
		return inputActivation;
	}

	public void train(double[] input, double[] targetOutput, double learningRate, double momentum) {
		double[] calculatedOutput = run(input);
		double[] error = new double[calculatedOutput.length];

		for (int i = 0; i < error.length; i++) {
			error[i] = targetOutput[i] - calculatedOutput[i];
		}

		for (int i = layers.length - 1; i >= 0; i--) {
			error = layers[i].train(error, learningRate, momentum);
		}
	}
}
