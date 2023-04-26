package com.example.schedulingproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PriorityScheduling {

    private float avg_turnaround_time;
    private float avg_waiting_time;
    int total_turnaround_time = 0;
    int total_waiting_time = 0;
    int[] is_completed = new int[100];
    int[] burst_remaining= new int[100];
    int current_time = 0;
    int completed = 0;
    int prev = 0;
    ArrayList<Process> processQueue = new ArrayList<>();


    public void Priority_Scheduling (ArrayList<Process> p, Boolean preemptive) {
        int n = p.size();
        for (int i = 0; i < n; i++) {
            burst_remaining[i] = p.get(i).getBurst_time();
        }

        while (completed != n) {
            int idx = -1;
            int mx = -1;
            for (int i = 0; i < n; i++) {
                if (p.get(i).getArrival_time() <= current_time && is_completed[i] == 0) {
                    if (p.get(i).getPriority() > mx) {
                        mx = p.get(i).getPriority();
                        idx = i;
                    }
                    if (p.get(i).getPriority() == mx) {
                        if (p.get(i).getArrival_time() < p.get(idx).getArrival_time()) {
                            mx = p.get(i).getPriority();
                            idx = i;
                        }
                    }
                }
            }
            if (idx != -1) {
                if (!preemptive) {
                    p.get(idx).start_time = current_time;
                    p.get(idx).completion_time = p.get(idx).start_time + p.get(idx).getBurst_time();
                    p.get(idx).turnaround_time = p.get(idx).completion_time - p.get(idx).getArrival_time();
                    p.get(idx).waiting_time = p.get(idx).turnaround_time - p.get(idx).getBurst_time();
                    p.get(idx).response_time = p.get(idx).start_time - p.get(idx).getArrival_time();

                    total_turnaround_time += p.get(idx).turnaround_time;
                    total_waiting_time += p.get(idx).waiting_time;
                    is_completed[idx] = 1;
                    completed++;
                    current_time = p.get(idx).completion_time;
                    prev = current_time;
                }
                if (preemptive) {
                    if (burst_remaining[idx] == p.get(idx).getBurst_time()) {
                        p.get(idx).start_time = current_time;
                    }
                    burst_remaining[idx] -= 1;
                    current_time++;
                    prev = current_time;

                    if (burst_remaining[idx] == 0) {
                        p.get(idx).completion_time = current_time;
                        p.get(idx).turnaround_time = p.get(idx).completion_time - p.get(idx).getArrival_time();
                        p.get(idx).waiting_time = p.get(idx).turnaround_time - p.get(idx).getBurst_time();
                        total_turnaround_time += p.get(idx).turnaround_time;
                        total_waiting_time += p.get(idx).waiting_time;
                        is_completed[idx] = 1;
                        completed++;
                    }
                }
            } else {
                current_time++;
            }

        }

        setAvg_turnaround_time((float) total_turnaround_time / n);
        setAvg_waiting_time((float) total_waiting_time / n);
        for (int i = 0; i < p.size() - 1; i++) {
            if (p.get(i).getArrival_time() == p.get(i + 1).getArrival_time()) {
                if (p.get(i).getPriority() < p.get(i + 1).getPriority()) {
                    if (p.get(i).start_time > p.get(i + 1).start_time) {
                        p.get(i).start_time = p.get(i + 1).start_time;
                        p.get(i).completion_time = p.get(i).start_time + p.get(i).getBurst_time();
                        p.get(i + 1).start_time = p.get(i).completion_time;
                        p.get(i + 1).completion_time = p.get(i + 1).start_time + p.get(i + 1).getBurst_time();
                        p.get(i).turnaround_time = p.get(i).start_time + p.get(i).getBurst_time();
                        p.get(i).waiting_time = p.get(i).start_time;
                        p.get(i + 1).turnaround_time = p.get(i + 1).start_time + p.get(i + 1).getBurst_time();
                        p.get(i + 1).waiting_time = p.get(i + 1).start_time;

                    }
                }
            }
        }

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