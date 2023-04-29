package com.example.schedulingproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FirstComeFirstServe {

    private float avg_turnaround_time;
    private float avg_waiting_time;
    int total_turnaround_time = 0;
    int total_waiting_time = 0;
    // int[] is_completed = new int[100];
    int[] burst_remaining= new int[100];
    int current_time = 0;
    //  int completed = 0;
    int prev = 0;
    int temp;
    int n; //Number of processes

    ArrayList<Process> processQueue = new ArrayList<Process>();
<<<<<<< Updated upstream
=======

    /*
     *  @param   n                  number of processes
     *  @param prev                 previous time
     *  @param avg_waiting_time     average waiting time
     *  @param avg_turnaround_time  average turn around time
     */
>>>>>>> Stashed changes
    public void FirstComeFirstServe(ArrayList<Process> p){
        n = p.size();

        // Sort the processes by arrival time
        for(int i = 0; i < n-1; i++){
            if (p.get(i).getArrival_time() > p.get(i + 1).getArrival_time()) {
                Collections.swap(p, i, i + 1);
            }
        }

        // Initialize the burst remaining array
        for (int i = 0; i < n; i++) {
            burst_remaining[i] = p.get(i).getBurst_time();
        }

        int wait = 0;
        total_waiting_time = 0;
        current_time = p.get(0).getArrival_time();

        // Calculate the waiting time and completion time of each process
        for(int i = 0; i < n; i++){
            wait = Math.max(0, current_time - p.get(i).getArrival_time());
            p.get(i).setStart_time(Math.max(prev, p.get(i).getArrival_time()));  // Set the start time of the current process
            current_time = p.get(i).getStart_time() + burst_remaining[i];
            p.get(i).setWaiting_time(wait);
            p.get(i).setCompletion_time(current_time);
            total_waiting_time += wait;
            prev = current_time;
        }

        // Calculate the average waiting time and average turnaround time
        setAvg_waiting_time(total_waiting_time / (float) n);
        avg_turnaround_time = 0;
        for(int i = 0; i < n; i++){
            p.get(i).setTurnaround_time(p.get(i).getWaiting_time() + p.get(i).getBurst_time());
            avg_turnaround_time += p.get(i).getTurnaround_time();
        }
        setAvg_turnaround_time(avg_turnaround_time / (float) n);
    }

    public float getAvg_turnaround_time() {
        return avg_turnaround_time;
    }

    public void setAvg_turnaround_time(float avg_turnaround_time) {
        this.avg_turnaround_time = avg_turnaround_time;
    }

    public float getAvg_waiting_time() {
        return avg_waiting_time;
    }

    public void setAvg_waiting_time(float avg_waiting_time) {
        this.avg_waiting_time = avg_waiting_time;
    }
    public int getTotal_turnaround_time() {
        return total_turnaround_time;
    }

    public void setTotal_turnaround_time(int total_turnaround_time) {
        this.total_turnaround_time = total_turnaround_time;
    }

    public int getTotal_waiting_time() {
        return total_waiting_time;
    }

    public void setTotal_waiting_time(int total_waiting_time) {
        this.total_waiting_time = total_waiting_time;
    }


    public ArrayList<Process> setProcessQueue(ArrayList<Process> pp){
        processQueue.addAll(pp);
        Collections.sort(processQueue, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                return p1.start_time - p2.start_time;
            }
        });
<<<<<<< Updated upstream
=======

        // Checking for idle period at time 0
>>>>>>> Stashed changes
        if(processQueue.get(0).start_time > 0){
            Process idle = new Process();
            idle.setPid(0);
            idle.start_time = 0;
            idle.setBurst_time(processQueue.get(0).start_time);
            idle.setPriority(-1);
            idle.completion_time = processQueue.get(0).start_time;
            idle.setColor("grey");
            processQueue.add(0,idle);
        }
        for(int i = 0; i < processQueue.size()-1; i++){

<<<<<<< Updated upstream
=======
            //Checking for idle periods between processes
>>>>>>> Stashed changes
            if(processQueue.get(i+1).start_time > processQueue.get(i).completion_time){
                Process idle = new Process();
                idle.setPid(0);
                idle.start_time = processQueue.get(i).completion_time;
                idle.setBurst_time(processQueue.get(i + 1).start_time - processQueue.get(i).completion_time);
                idle.setPriority(-1);
                idle.completion_time = processQueue.get(i+1).start_time;
                idle.setColor("grey");
                processQueue.add(i+1,idle);
            }
        }
        return processQueue;
    }
}