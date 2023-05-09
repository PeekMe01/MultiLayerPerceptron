package com.example.multilayerperceptron;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.text.DecimalFormat;

public class HelloController {

    private static final DecimalFormat df = new DecimalFormat("0.000000");

    @FXML
    private TextArea textOutput;

    @FXML
    private Circle HiddenLayer1;

    @FXML
    private Circle HiddenLayer2;

    @FXML
    private Label desiredOutput_label;

    @FXML
    private TextField desiredOutput_txtField;

    @FXML
    private Label learningRate_label;

    @FXML
    private TextField learningRate_txtField;

    @FXML
    private Circle outputLayer;

    @FXML
    private Label outputY3_label;

    @FXML
    private TextField outputY3_txtField;

    @FXML
    private Label outputY4_label;

    @FXML
    private TextField outputY4_txtField;

    @FXML
    private Label outputY5_label;

    @FXML
    private TextField outputY5_txtField;

    @FXML
    private Button start_btn;

    @FXML
    private Label weight13_label;

    @FXML
    private Line weight13_line;

    @FXML
    private TextField weight13_txtField;

    @FXML
    private Label weight14_label;

    @FXML
    private Line weight14_line;

    @FXML
    private TextField weight14_txtField;

    @FXML
    private Label weight23_label;

    @FXML
    private Line weight23_line;

    @FXML
    private TextField weight23_txtField;

    @FXML
    private Label weight24_label;

    @FXML
    private Line weight24_line;

    @FXML
    private TextField weight24_txtField;

    @FXML
    private Label weight35_label;

    @FXML
    private Line weight35_line;

    @FXML
    private TextField weight35_txtField;

    @FXML
    private Label weight45_label;

    @FXML
    private Line weight45_line;

    @FXML
    private TextField weight45_txtField;

    @FXML
    private Label x1Input_label;

    @FXML
    private TextField x1Input_txtField;

    @FXML
    private Label x2Input_label;

    @FXML
    private TextField x2Input_txtField;

    @FXML
    private Slider speed_slider;

    @FXML
    void startPerceptron(ActionEvent event){
        final double[] finalY = new double[1];
        final double[] finalY1 = new double[1];
        final double[] finalY2 = new double[1];
        final double[] finalW3 = new double[1];
        final double[] finalW4 = new double[1];
        final double[] finalW1 = new double[1];
        final double[] finalW11 = new double[1];
        final double[] finalW2 = new double[1];
        final double[] finalW21 = new double[1];
        Thread thread1 = new Thread(){
            public void run() {
                boolean correctOutput = false;

                // generations
                int gen = 0;

                // inputs
                double x1 = Double.parseDouble(x1Input_txtField.getText());
                double x2 = Double.parseDouble(x2Input_txtField.getText());

                // weights
                double w13 = Double.parseDouble(weight13_txtField.getText());
                double w14 = Double.parseDouble(weight14_txtField.getText());
                double w23 = Double.parseDouble(weight23_txtField.getText());
                double w24 = Double.parseDouble(weight24_txtField.getText());
                double w35 = Double.parseDouble(weight35_txtField.getText());
                double w45 = Double.parseDouble(weight45_txtField.getText());

                // v's
                double v3, v4, v5;
                // outputs
                double y3, y4, y5;

                // deltas
                double d3, d4, d5;

                // errors
                double err;

                // learning rate
                double n = Double.parseDouble(learningRate_txtField.getText());

                // desired output
                double desiredOutput = Double.parseDouble(desiredOutput_txtField.getText());

                do {
                    // forward pass - START //

                    // get H3 output
                    v3 = ((x1 * w13) + (x2 * w23))*1.0000;
                    y3 = (1 / (1 + Math.exp(-v3)))*1.0000;

                    // get H4 output
                    v4 = ((x1 * w14) + (x2 * w24))*1.0000;
                    y4 = (1 / (1 + Math.exp(-v4)))*1.0000;

                    // get O5 output
                    v5 = ((y3 * w35) + (y4 * w45))*1.0000;
                    y5 = (1 / (1 + Math.exp(-v5)))*1.0000; // here we take this value and check if it matches the desired output

                    finalY[0] = y3;
                    finalY1[0] = y4;
                    finalY2[0] = y5;

                    // check if the value is equal to the desired
                    correctOutput = y5 >= desiredOutput * (0.99) && y5 <= desiredOutput * (1.01);

                    //System.out.println(gen);
                    //System.out.println(y5);

                    // forward pass - END //

                    if (correctOutput) {
                        break;
                    } else {
                        // get the error rate
                        err = desiredOutput - y5;

                        // backward pass - START //

                        d5 = (y5 * (1 - y5) * (desiredOutput - y5))*1.0000;

                        d3 = (y3 * (1 - y3) * w35 * d5)*1.0000;

                        d4 = (y4 * (1 - y4) * w45 * d5)*1.0000;

                        // now change the weights

                        // new weight = old weight + ( learning Rate * delta of where its going to * output of where its coming from )
                        w35 = (w35 + (n * d5 * y3))*1.0000;
                        w45 = (w45 + (n * d5 * y4))*1.0000;

                        w13 = (w13 + (n * d3 * x1))*1.0000;
                        w14 = (w14 + (n * d4 * x1))*1.0000;

                        w23 = (w23 + (n * d3 * x2))*1.0000;
                        w24 = (w24 + (n * d4 * x2))*1.0000;

                        finalW3[0] = w35;
                        finalW4[0] = w45;
                        finalW1[0] = w13;
                        finalW11[0] = w14;
                        finalW2[0] = w23;
                        finalW21[0] = w24;
                    }
                    gen++;
                    textOutput.setText("Generation "+gen
                            +"\nWeights:\t\t\t\t\t\tOutputs:"
                            +"\nWeight 13 = " + df.format(w13)+"\t\t\tY3 = " + df.format(y3)
                            +"\nWeight 14 = " + df.format(w14)+"\t\t\tY4 = " + df.format(y4)
                            +"\nWeight 23 = " + df.format(w23)+"\t\t\tY5 = " + df.format(y5)
                            +"\nWeight 24 = " + df.format(w24)
                            +"\nWeight 35 = " + df.format(w35)
                            +"\nWeight 45 = " + df.format(w45)
                    );
                    long speed = 101 - (long) speed_slider.getValue();
                    try {
                        Thread.sleep(5 * speed);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (!correctOutput);
            }
        };
        thread1.start();

        Thread thread2 = new Thread(){
            public void run(){
                while(thread1.isAlive()){
                    long speed = 101 - (long) speed_slider.getValue();
                    try {
                        Thread.sleep(50 * speed);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    outputY3_txtField.setText(df.format(finalY[0]));
                    outputY4_txtField.setText(df.format(finalY1[0]));
                    outputY5_txtField.setText(df.format(finalY2[0]));

                    weight35_txtField.setText(df.format(finalW3[0]));
                    weight45_txtField.setText(df.format(finalW4[0]));

                    weight13_txtField.setText(df.format(finalW1[0]));
                    weight14_txtField.setText(df.format(finalW11[0]));

                    weight23_txtField.setText(df.format(finalW2[0]));
                    weight24_txtField.setText(df.format(finalW21[0]));
                }
            }
        };
        thread2.start();

        Thread thread3 = new Thread() {
            public void run() {
                while(thread1.isAlive()){}
                outputY3_txtField.setText(String.valueOf(finalY[0]));
                outputY4_txtField.setText(String.valueOf(finalY1[0]));
                outputY5_txtField.setText(String.valueOf(finalY2[0]));
            }
        };
        thread3.start();

    }
}