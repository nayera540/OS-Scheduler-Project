package com.example.schedulingproject;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;


/**
 * class which is invoked to draw the GanttChart
 * */

public class GanttChart {
    public SubScene ganttScene;
    public ScrollPane ganttPane;
//    ganttPane.setFitToWidth(true);

    public void GanttArrayStaticDraw(ArrayList<Process> queue){
        double height = ganttScene.getHeight();
        double rectHeight = (height-100)/2;
        Group group = new Group();
        Text startText = new Text("0");
        startText.setFill(Color.BLACK);
        startText.setFont(Font.font("Arial", 12));
        startText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        startText.setX(ganttPane.getTranslateX() + 13); // adjust the x position to align it with the start of the rectangle
        group.getChildren().add(startText);
        for(int i = 0; i < queue.size(); i++){
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(100);
            rectangle.setWidth(queue.get(i).getBurst_time() * 15);
            rectangle.setFill(Color.valueOf(queue.get(i).getColor()));
            rectangle.setStroke(Color.valueOf("black"));
            group.getChildren().add(rectangle); // add the rectangle to the group first
            Text endText = new Text(String.valueOf(queue.get(i).completion_time));
            endText.setFill(Color.BLACK);
            endText.setFont(Font.font("Arial", 12));
            endText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
            endText.setX(ganttPane.getTranslateX() + (queue.get(i).getBurst_time() * 15) + 10 + (queue.get(i).start_time * 15)); // adjust the x position to align it with the end of the rectangle
            group.getChildren().add(endText);
            rectangle.setY(rectHeight + ganttPane.getTranslateY());
            rectangle.setX(ganttPane.getTranslateX() + 15 + (queue.get(i).start_time * 15));
            ganttScene.setRoot(group);
        }

    }
    public void GanttArrayStaticDrawPriority(ArrayList<Process> queue){
        double height = ganttScene.getHeight();
        double rectHeight = (height-100)/2;
        Group group = new Group();
        Text startText = new Text("0");
        startText.setFill(Color.BLACK);
        startText.setFont(Font.font("Arial", 12));
        startText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        startText.setX(ganttPane.getTranslateX() + 13); // adjust the x position to align it with the start of the rectangle
        group.getChildren().add(startText);
        for(int i = 0; i < queue.size(); i++){
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(100);
            rectangle.setWidth(queue.get(i).getBurst_time() * 18);
            rectangle.setFill(Color.valueOf(queue.get(i).getColor()));
            rectangle.setStroke(Color.valueOf("black"));
            group.getChildren().add(rectangle); // add the rectangle to the group first
            Text endText = new Text(String.valueOf(queue.get(i).completion_time));
            endText.setFill(Color.BLACK);
            endText.setFont(Font.font("Arial", 12));
            endText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
            endText.setX(ganttPane.getTranslateX() + (queue.get(i).getBurst_time() * 18) + 10 + (queue.get(i).start_time * 18)); // adjust the x position to align it with the end of the rectangle
            group.getChildren().add(endText);
            rectangle.setY(rectHeight + ganttPane.getTranslateY());
            rectangle.setX(ganttPane.getTranslateX() + 15 + (queue.get(i).start_time * 18));
            ganttScene.setRoot(group);
        }

    }


    void GanttDynamicDraw(Process queue, Group group2){
        double height = ganttScene.getHeight();
        double rectHeight = (height-100)/2;

        Text startText = new Text("0");
        startText.setFill(Color.BLACK);
        startText.setFont(Font.font("Arial", 12));
        startText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        startText.setX(ganttPane.getTranslateX() + 13); // adjust the x position to align it with the start of the rectangle
        group2.getChildren().add(startText);
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(100);
        rectangle.setWidth(queue.getBurst_time() * 15);
        rectangle.setFill(Color.valueOf(queue.getColor()));
        rectangle.setStroke(Color.valueOf("black"));
        group2.getChildren().add(rectangle); // add the rectangle to the group first
        Text endText = new Text(String.valueOf(queue.completion_time));
        endText.setFill(Color.BLACK);
        endText.setFont(Font.font("Arial", 12));
        endText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        endText.setX(ganttPane.getTranslateX() + (queue.getBurst_time() * 15) + 10 + (queue.start_time * 15)); // adjust the x position to align it with the end of the rectangle
        group2.getChildren().add(endText);
        rectangle.setY(rectHeight + ganttPane.getTranslateY());
        rectangle.setX(ganttPane.getTranslateX() + 15 + (queue.start_time * 15));
        ganttScene.setRoot(group2);

    }
    void GanttDynamicDrawPriority(Process queue, Group group2){
        double height = ganttScene.getHeight();
        double rectHeight = (height-100)/2;

        Text startText = new Text("0");
        startText.setFill(Color.BLACK);
        startText.setFont(Font.font("Arial", 12));
        startText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        startText.setX(ganttPane.getTranslateX() + 13); // adjust the x position to align it with the start of the rectangle
        group2.getChildren().add(startText);
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(100);
        rectangle.setWidth(queue.getBurst_time() * 18);
        rectangle.setFill(Color.valueOf(queue.getColor()));
        rectangle.setStroke(Color.valueOf("black"));
        group2.getChildren().add(rectangle); // add the rectangle to the group first
        Text endText = new Text(String.valueOf(queue.completion_time));
        endText.setFill(Color.BLACK);
        endText.setFont(Font.font("Arial", 12));
        endText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        endText.setX(ganttPane.getTranslateX() + (queue.getBurst_time() * 18) + 10 + (queue.start_time * 18)); // adjust the x position to align it with the end of the rectangle
        group2.getChildren().add(endText);
        rectangle.setY(rectHeight + ganttPane.getTranslateY());
        rectangle.setX(ganttPane.getTranslateX() + 15 + (queue.start_time * 18));
        ganttScene.setRoot(group2);

    }
}
