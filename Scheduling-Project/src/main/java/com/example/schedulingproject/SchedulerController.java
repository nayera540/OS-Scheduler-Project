package com.example.schedulingproject;


import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SchedulerController implements Initializable {
    @FXML
    private ComboBox<String> ScheduleType;
    @FXML
    private CheckBox preemptiveOrNot;
    @FXML
    private TextField pid;
    @FXML
    private TextField arrivalTime;
    @FXML
    private TextField burstTime;
    @FXML
    private TextField color;
    @FXML
    private TextField priority;
    @FXML
    private TextField quantumTime;



    @FXML
    private Button addProcess;
    @FXML
    private Button calculateButton;
    @FXML
    private Button simulate;


    @FXML
    private TableView<Process> processTable;
    @FXML
    private TableColumn<Process, Integer> pidCol;
    @FXML
    private TableColumn<Process, Integer> arrivalTimeCol;
    @FXML
    private TableColumn<Process, Integer> priorityCol;
    @FXML
    private TableColumn<Process, String> colorCol;
    @FXML
    private TableColumn<Process, Integer> burstTimeCol;
    @FXML
    private TableColumn<Process, Integer> RemainingTimeCol;
    @FXML
    private TableColumn<Process, Integer> startTimeCol;
    @FXML
    private TableColumn<Process, Integer> completeTimeCol;
    @FXML
    private TableColumn<Process, Integer> turnAroundCol;
    @FXML
    private TableColumn<Process, Integer> waitTimeCol;


    @FXML
    private ScrollPane ganttPane;
    @FXML
    private SubScene ganttScene;



    @FXML
    private Label avgTurnTime;
    @FXML
    private Label avgWaitTime;

    @FXML
    private Button addnewProcess;
    @FXML
    private Button Reset;

    private static boolean first;
    ObservableList<Process> processes = FXCollections.observableArrayList();
    ArrayList<Process> arrayProcess = new ArrayList<>();

    public void UpdateTable(){
        pidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
        burstTimeCol.setCellValueFactory(new PropertyValueFactory<>("burst_time"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        RemainingTimeCol.setCellValueFactory(new PropertyValueFactory<>("remain_time"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        completeTimeCol.setCellValueFactory(new PropertyValueFactory<>("completion_time"));
        turnAroundCol.setCellValueFactory(new PropertyValueFactory<>("turnaround_time"));
        waitTimeCol.setCellValueFactory(new PropertyValueFactory<>("waiting_time"));

        processTable.setItems(processes);




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScheduleType.getItems().addAll("FCFS", "SJF", "Priority", "Round Robin");
        ScheduleType.getSelectionModel().selectFirst();

        //To hide the checkbox and text fields
        preemptiveOrNot.setVisible(false);
        priority.setVisible(false);
        quantumTime.setVisible(false);

        //Visible or Hidden based on what the user select
        ScheduleType.getSelectionModel().selectedItemProperty().addListener((v,o,n)->{
            if (n == "FCFS") {
                preemptiveOrNot.setVisible(false);
                priority.setVisible(false);
                quantumTime.setVisible(false);
            } else if (n == "SJF") {
                preemptiveOrNot.setVisible(true);
                priority.setVisible(false);
                quantumTime.setVisible(false);
            } else if (n == "Priority") {
                preemptiveOrNot.setVisible(true);
                priority.setVisible(true);
                priority.setMaxWidth(priority.getMinWidth());
                quantumTime.setVisible(false);
            } else if (n == "Round Robin") {
                preemptiveOrNot.setVisible(false);
                priority.setVisible(false);
                priority.setMaxWidth(0);
                quantumTime.setVisible(true);
            }
        });
        UpdateTable();







    }
    @FXML
    void onAddProcess(ActionEvent event) {
        if(ScheduleType.getValue() == "Priority"){
            Process process = new Process(Integer.parseInt(pid.getText()), Integer.parseInt(arrivalTime.getText()), Integer.parseInt(burstTime.getText()),
                    Integer.parseInt(priority.getText()),color.getText());
            arrayProcess.add(process);
            processes.add(process);
            processTable.setItems(processes);


        }
        if(!pid.getText().isEmpty() && !arrivalTime.getText().isEmpty() && !burstTime.getText().isEmpty() && !priority.getText().isEmpty() && !color.getText().isEmpty()){
            pid.clear();
            arrivalTime.clear();
            burstTime.clear();
            priority.clear();
            color.clear();
        }

    }


    @FXML
    void onCalculate(ActionEvent event) {
        if(ScheduleType.getValue() == "Priority"){
            PriorityScheduling pp = new PriorityScheduling();
            pp.Priority_Scheduling(arrayProcess,preemptiveOrNot.isSelected());
            for(int i = 0; i < arrayProcess.size(); i++){
                processes.get(processes.indexOf(arrayProcess.get(i))).setRemain_time(0);
                processTable.refresh();
            }

            GanttStaticDraw(pp.setProcessQueue(arrayProcess));

            avgTurnTime.setText(String.format("%.2f", pp.getAvg_turnaround_time()));
            avgWaitTime.setText(String.format("%.2f", pp.getAvg_waiting_time()));



        }

    }
    void GanttStaticDraw(ArrayList<Process> queue){
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
            rectangle.setWidth(queue.get(i).getBurst_time() * 30);
            rectangle.setFill(Color.valueOf(queue.get(i).getColor()));
            rectangle.setStroke(Color.valueOf("black"));
            group.getChildren().add(rectangle); // add the rectangle to the group first
            Text endText = new Text(String.valueOf(queue.get(i).completion_time));
            endText.setFill(Color.BLACK);
            endText.setFont(Font.font("Arial", 12));
            endText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
            endText.setX(ganttPane.getTranslateX() + (queue.get(i).getBurst_time() * 30) + 10 + (queue.get(i).start_time * 30)); // adjust the x position to align it with the end of the rectangle
            group.getChildren().add(endText);
            rectangle.setY(rectHeight + ganttPane.getTranslateY());
            rectangle.setX(ganttPane.getTranslateX() + 15 + (queue.get(i).start_time * 30));
            ganttScene.setRoot(group);
        }

    }


    @FXML
    void onSimulate(ActionEvent event) {

        if(ScheduleType.getValue() == "Priority"){
            PriorityScheduling pp = new PriorityScheduling();
            pp.Priority_Scheduling(arrayProcess,preemptiveOrNot.isSelected());
            ArrayList<Process> queue = pp.setProcessQueue(arrayProcess);

            final int[] currentColumnIndex = {0};


            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
                Process currentProcess = queue.get(currentColumnIndex[0]);
                int remainBurstTime = currentProcess.getRemain_time();
                if (remainBurstTime > 0) {
                    int elapsedTimeInSeconds = (int) timeline.getCurrentTime().toSeconds();
                    int updatedRemainTime = remainBurstTime - elapsedTimeInSeconds;
                    if (updatedRemainTime < 0) {
                        updatedRemainTime = 0;
                    }
                    currentProcess.setRemain_time(updatedRemainTime);
                    waitTimeCol.setVisible(false);
                    turnAroundCol.setVisible(false);
                    completeTimeCol.setVisible(false);
                    processTable.refresh();


                }
                else {
                    GanttDynamicDraw(currentProcess);
                    processTable.refresh();
                    currentColumnIndex[0]++;
                    if (currentColumnIndex[0] >= queue.size()) {
                        timeline.stop();
                        waitTimeCol.setVisible(true);
                        turnAroundCol.setVisible(true);
                        completeTimeCol.setVisible(true);
                        processTable.refresh();
                        avgTurnTime.setText(String.format("%.2f", pp.getAvg_turnaround_time()));
                        avgWaitTime.setText(String.format("%.2f", pp.getAvg_waiting_time()));
                    }
                }

            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();


        }



    }
    Group group2 = new Group();
    void GanttDynamicDraw(Process queue){
        double height = ganttScene.getHeight();
        double rectHeight = (height-100)/2;

        Text startText = new Text("0");
        startText.setFill(Color.BLACK);
        startText.setFont(Font.font("Arial", 12));
        startText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        startText.setX(ganttPane.getTranslateX() + 13); // adjust the x position to align it with the start of the rectangle
        group2.getChildren().add(startText);
//        for(int i = 0; i < queue.size(); i++){
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(100);
        rectangle.setWidth(queue.getBurst_time() * 30);
        rectangle.setFill(Color.valueOf(queue.getColor()));
        rectangle.setStroke(Color.valueOf("black"));
        group2.getChildren().add(rectangle); // add the rectangle to the group first
        Text endText = new Text(String.valueOf(queue.completion_time));
        endText.setFill(Color.BLACK);
        endText.setFont(Font.font("Arial", 12));
        endText.setY(rectHeight + 112 + ganttPane.getTranslateY()); // adjust the y position to place it below the rectangle
        endText.setX(ganttPane.getTranslateX() + (queue.getBurst_time() * 30) + 10 + (queue.start_time * 30)); // adjust the x position to align it with the end of the rectangle
        group2.getChildren().add(endText);
        rectangle.setY(rectHeight + ganttPane.getTranslateY());
        rectangle.setX(ganttPane.getTranslateX() + 15 + (queue.start_time * 30));
        ganttScene.setRoot(group2);
//        }

    }


    @FXML
    void onAddNewProcess(ActionEvent event) {

    }


    @FXML
    void onEditArrivalTimeCol(ActionEvent event) {

    }

    @FXML
    void onEditCompleteTimeCol(ActionEvent event) {

    }

    @FXML
    void onEditPIDCol(ActionEvent event) {

    }

    @FXML
    void onEditPriorityCol(ActionEvent event) {

    }

    @FXML
    void onEditStartTimeCol(ActionEvent event) {

    }

    @FXML
    void onEditTurnAroundCol(ActionEvent event) {

    }

    @FXML
    void onEditWaitTimeCol(ActionEvent event) {

    }



    @FXML
    void oneEditBurstTimeCol(ActionEvent event) {

    }
    @FXML
    void onRemainingTimeCol(ActionEvent event) {

    }


    @FXML
    void onReset(ActionEvent event) {
        processTable.getItems().removeAll(processTable.getSelectionModel().getSelectedItems());
        arrayProcess.clear();
        processes.clear();
        Group root = (Group) ganttScene.getRoot();
        group2.getChildren().clear();
        root.getChildren().clear();


    }


}






