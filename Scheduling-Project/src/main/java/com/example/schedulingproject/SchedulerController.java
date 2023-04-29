package com.example.schedulingproject;


import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        if(ScheduleType.getValue() == "FCFS"){
            Process process = new Process(Integer.parseInt(pid.getText()), Integer.parseInt(arrivalTime.getText()), Integer.parseInt(burstTime.getText()),color.getText());
            arrayProcess.add(process);
            processes.add(process);
            processTable.setItems(processes);


        }
        if(ScheduleType.getValue() == "SJF"){
            Process process = new Process(Integer.parseInt(pid.getText()), Integer.parseInt(arrivalTime.getText()), Integer.parseInt(burstTime.getText()),color.getText());
            arrayProcess.add(process);
            processes.add(process);
            processTable.setItems(processes);


        }
        if(ScheduleType.getValue() == "Round Robin"){
            Process process = new Process(Integer.parseInt(pid.getText()), Integer.parseInt(arrivalTime.getText()), Integer.parseInt(burstTime.getText()),color.getText());
            arrayProcess.add(process);
            processes.add(process);
            processTable.setItems(processes);


        }
        if(!pid.getText().isEmpty() && !arrivalTime.getText().isEmpty() && !burstTime.getText().isEmpty() || !priority.getText().isEmpty() && !color.getText().isEmpty()){
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

            GanttChart ganttChart = new GanttChart();
            ganttChart.ganttScene = ganttScene;
            ganttChart.ganttPane = ganttPane;
            ganttChart.GanttArrayStaticDraw(pp.setProcessQueue(arrayProcess));

            avgTurnTime.setText(String.format("%.2f", pp.getAvg_turnaround_time()));
            avgWaitTime.setText(String.format("%.2f", pp.getAvg_waiting_time()));



        }
        if(ScheduleType.getValue() == "FCFS"){
            FirstComeFirstServe fcfs = new FirstComeFirstServe();
            fcfs.FirstComeFirstServe(arrayProcess);
            for(int i = 0; i < arrayProcess.size(); i++){
                processes.get(processes.indexOf(arrayProcess.get(i))).setRemain_time(0);
                processTable.refresh();
            }

            GanttChart ganttChart = new GanttChart();
            ganttChart.ganttScene = ganttScene;
            ganttChart.ganttPane = ganttPane;
            ganttChart.GanttArrayStaticDraw(fcfs.setProcessQueue(arrayProcess));

            avgTurnTime.setText(String.format("%.2f", fcfs.getAvg_turnaround_time()));
            avgWaitTime.setText(String.format("%.2f", fcfs.getAvg_waiting_time()));



        }
        if(ScheduleType.getValue() == "SJF"){
            SJFScheduler sjf = new SJFScheduler();
            sjf.schedule(arrayProcess,preemptiveOrNot.isSelected());
            for(int i = 0; i < arrayProcess.size(); i++){
                processes.get(processes.indexOf(arrayProcess.get(i))).setRemain_time(0);
                processTable.refresh();
            }

//            GanttStaticDraw(sjf.processQueue);

            avgTurnTime.setText(String.format("%.2f", sjf.getAverageWaitingTime()));
            avgWaitTime.setText(String.format("%.2f", sjf.getAverageTurnaroundTime()));



        }
        if(ScheduleType.getValue() == "Round Robin"){
            RoundRobin RR = new RoundRobin();
            ArrayList<Process> RRQueue = RR.Round_Robin(arrayProcess,Integer.parseInt(quantumTime.getText()));
            for(int i = 0; i < arrayProcess.size(); i++){
                processes.get(processes.indexOf(arrayProcess.get(i))).setRemain_time(0);
                processTable.refresh();
            }

            GanttChart ganttChart = new GanttChart();
            ganttChart.ganttScene = ganttScene;
            ganttChart.ganttPane = ganttPane;
            ganttChart.GanttArrayStaticDraw(RR.QueueProcess(RRQueue,arrayProcess));

            avgTurnTime.setText(String.format("%.2f", RR.getAvg_turnaround_time()));
            avgWaitTime.setText(String.format("%.2f", RR.getAvg_waiting_time()));



        }

    }

    Group group2 = new Group();
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
                System.out.println(remainBurstTime);
                if (remainBurstTime > 0) {
                    int elapsedTimeInSeconds = (int) timeline.getCurrentTime().toSeconds();
                    int updatedRemainTime = remainBurstTime - elapsedTimeInSeconds;
                    if (updatedRemainTime < 0) {
                        updatedRemainTime = 0;
                    }
                    for(int i = 0; i < arrayProcess.size(); i++){
                        if(currentProcess.getPid() == processes.get(i).getPid()){
                            int remainTime = (arrayProcess.get(i).getRemain_time() - (remainBurstTime - updatedRemainTime));
                            processes.get(i).setRemain_time(remainTime);
                            if(updatedRemainTime == 1){
                                arrayProcess.get(i).setRemain_time(remainTime-1);
                                processes.get(i).setRemain_time(remainTime);
                                break;
                            }
                        }
                        else{
                            currentProcess.setRemain_time(updatedRemainTime);
                        }
                    }
                    waitTimeCol.setVisible(false);
                    turnAroundCol.setVisible(false);
                    completeTimeCol.setVisible(false);
                    processTable.refresh();


                }
                else {
                    GanttChart ganttChart = new GanttChart();
                    ganttChart.ganttScene = ganttScene;
                    ganttChart.ganttPane = ganttPane;
                    ganttChart.GanttDynamicDraw(currentProcess,group2);
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
        if(ScheduleType.getValue() == "FCFS"){
            FirstComeFirstServe fcfs = new FirstComeFirstServe();
            fcfs.FirstComeFirstServe(arrayProcess);
            ArrayList<Process> queue = fcfs.setProcessQueue(arrayProcess);

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
                    GanttChart ganttChart = new GanttChart();
                    ganttChart.ganttScene = ganttScene;
                    ganttChart.ganttPane = ganttPane;
                    ganttChart.GanttDynamicDraw(currentProcess,group2);
                    processTable.refresh();
                    currentColumnIndex[0]++;
                    if (currentColumnIndex[0] >= queue.size()) {
                        timeline.stop();
                        waitTimeCol.setVisible(true);
                        turnAroundCol.setVisible(true);
                        completeTimeCol.setVisible(true);
                        processTable.refresh();
                        avgTurnTime.setText(String.format("%.2f", fcfs.getAvg_turnaround_time()));
                        avgWaitTime.setText(String.format("%.2f", fcfs.getAvg_waiting_time()));
                    }
                }

            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();


        }
        if(ScheduleType.getValue() == "Round Robin"){
            RoundRobin RR = new RoundRobin();
            ArrayList<Process> RRQueue = RR.Round_Robin(arrayProcess,Integer.parseInt(quantumTime.getText()));
            ArrayList<Process> queue = RR.QueueProcess(RRQueue,arrayProcess);
            final int[] currentColumnIndex = {0};

            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
                Process currentProcess = queue.get(currentColumnIndex[0]);
                int remainBurstTime = currentProcess.getRemain_time();
                System.out.println(remainBurstTime);
                System.out.println(currentProcess.getPid());
                if (remainBurstTime > 0) {
                    int elapsedTimeInSeconds = (int) timeline.getCurrentTime().toSeconds();
                    int updatedRemainTime = remainBurstTime - elapsedTimeInSeconds;
                    if (updatedRemainTime < 0) {
                        updatedRemainTime = 0;
                    }
                    for(int i = 0; i < processes.size(); i++){

                        if(currentProcess.getPid() == processes.get(i).getPid()){
                            int remainTime = (arrayProcess.get(i).getRemain_time() - (remainBurstTime - updatedRemainTime));
                            processes.get(i).setRemain_time(remainTime);
                            currentProcess.setRemain_time(updatedRemainTime);
                            if(updatedRemainTime == 1){
                                arrayProcess.get(i).setRemain_time(remainTime-1);
                                processes.get(i).setRemain_time(remainTime);
                                currentProcess.setRemain_time(updatedRemainTime);
                                break;
                            }
                        }
                        else{
                            currentProcess.setRemain_time(updatedRemainTime);
                        }
                    }
                    waitTimeCol.setVisible(false);
                    turnAroundCol.setVisible(false);
                    completeTimeCol.setVisible(false);
                    processTable.refresh();


                }
                else {
                    GanttChart ganttChart = new GanttChart();
                    ganttChart.ganttScene = ganttScene;
                    ganttChart.ganttPane = ganttPane;
                    ganttChart.GanttDynamicDraw(currentProcess,group2);
                    processTable.refresh();
                    currentColumnIndex[0]++;
                    if (currentColumnIndex[0] >= queue.size()) {
                        timeline.stop();
                        waitTimeCol.setVisible(true);
                        turnAroundCol.setVisible(true);
                        completeTimeCol.setVisible(true);
                        processTable.refresh();
                        avgTurnTime.setText(String.format("%.2f", RR.getAvg_turnaround_time()));
                        avgWaitTime.setText(String.format("%.2f", RR.getAvg_waiting_time()));
                    }
                }

            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();


        }



    }




    @FXML
    void onAddNewProcess(ActionEvent event) {

    }


    @FXML
    void onReset(ActionEvent event) {
        processTable.getItems().removeAll(processTable.getSelectionModel().getSelectedItems());
        arrayProcess.clear();
        processes.clear();
        Group root = (Group) ganttScene.getRoot();
        if(!group2.getChildren().isEmpty()){
            group2.getChildren().clear();
        }
        root.getChildren().clear();
        if(!quantumTime.getText().isEmpty())
            quantumTime.clear();


    }


}






