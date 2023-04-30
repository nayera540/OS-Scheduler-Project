package com.example.schedulingproject;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * class which is invoked to check the validity of the text fields inputs
 * */
public class Validator {
    public static int IntegerValidate(TextField input) {
        try {
            int value = Integer.parseInt(input.getText());
            if(value > 0){
                return value;
            }
            else
                return -1;

        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public static int IntegerArrivalPriorityValidate(TextField input) {
        try {
            int value = Integer.parseInt(input.getText());
            if(value >= 0){
                return value;
            }
            else
                return -1;

        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public static String ColorValidate(TextField input) {
        try {
            Color.web(input.getText());
            return input.getText();

        } catch (IllegalArgumentException e) {
            return "-1";
        }
    }
}
