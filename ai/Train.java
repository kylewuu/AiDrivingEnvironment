package driver.ai;

public class Train {

	public BackpropNeuralNetwork backprop;

	public double learningRate;
	public double momentum;
	public double iterationsMax;

	public void train(double[][] trainData, double[][] trainResults, int inputSize, int hiddenSize, int outputSize){

		learningRate = 0.3;
		momentum = 0.6;
		iterationsMax = 10000;

		backprop = new BackpropNeuralNetwork(inputSize, hiddenSize, outputSize);
		
		for (int iterations = 0; iterations < iterationsMax; iterations++){
			for (int i = 0; i < trainResults.length; i++){
				backprop.train(trainData[i], trainResults[i],
						learningRate, momentum);
			}
			// if ((iterations + 1) % 1000 == 0) {
			// 	System.out.println("Iteration #" + iterations+"\n");
			// 	for (int i = 0; i < trainResults.length; i++) {
			// 		double[] data = trainData[i];
			// 		double[] calculatedOutput = backprop.run(data);
			// 		System.out.println(data[0]+", "+data[1]+ " --> " +Math.round(calculatedOutput[0])+" - "+ Math.round(calculatedOutput[1])+" - "+ Math.round(calculatedOutput[2]));
			// 	}
			// }
		}

	}

	public double[] run(double[] input){
		return backprop.run(input);
	}
}