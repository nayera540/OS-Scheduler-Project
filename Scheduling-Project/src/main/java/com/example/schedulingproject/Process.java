package com.example.schedulingproject;
import java.util.*;

public class Process {
    private int pid;
    private int arrival_time;
    private int burst_time;
    private int priority;
    int start_time;
    int completion_time;
    int turnaround_time;
    int waiting_time;
    int response_time;
    private String color;
     int remain_time;

    public Process(){

    }

    public Process(int pid, int arrival_time, int burst_time, int priority, String color) {
        this.pid = pid;
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.priority = priority;
        this.color = color;
        this.start_time = -1;
        this.completion_time = -1;
        this.turnaround_time = -1;
        this.waiting_time = -1;
        this.remain_time = this.burst_time;


    }
    public Process(int pid, int arrival_time, int burst_time, String color){
        this.pid = pid;
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.color = color;
        this.priority = 0;
        this.start_time = -1;
        this.completion_time = -1;
        this.turnaround_time = -1;
        this.waiting_time = -1;
        this.remain_time = this.burst_time;
    }


    public Process(int id) {
        this.pid = id;
    }

    public int getPid() {
        return pid;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public int getPriority() {
        return priority;
    }

    public String getColor() {
        return color;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(int completion_time) {
        this.completion_time = completion_time;
    }

    public int getTurnaround_time() {
        return turnaround_time;
    }

    public void setTurnaround_time(int turnaround_time) {
        this.turnaround_time = turnaround_time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getResponse_time() {
        return response_time;
    }

    public void setResponse_time(int response_time) {
        this.response_time = response_time;
    }
    public int getRemain_time() {
        return remain_time;
    }

    public void setRemain_time(int remain_time) {
        this.remain_time = remain_time;
    }

}

