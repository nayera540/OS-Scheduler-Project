package com.example.schedulingproject;

import java.util.ArrayList;


/**
 * class which simulates the Priority scheduler
 **/

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



    /*
     *  @param   n                  number of processes
     *  @param completed            tells when all processes in the queue end
     *  @param is_completed         tells if a process finishes excution '1' or not '0'
     *  @param prev                 previous time
     *  @param avg_waiting_time     average waiting time
     *  @param avg_turnaround_time  average turn around time
     */

    public ArrayList<Process> Priority_Scheduling (ArrayList<Process> p, Boolean preemptive) {
        ArrayList<Process> result = new ArrayList<>();
        int n = p.size();
        for (int i = 0; i < n; i++) {
            burst_remaining[i] = p.get(i).getBurst_time();
        }
        int z=0;

        while (completed != n) {
            int idx = -1;
            int mx = Integer.MAX_VALUE;

            // Sorting process according to arrival time and priorty
            for (int i = 0; i < n; i++) {
                if (p.get(i).getArrival_time() <= current_time && is_completed[i] == 0) {
                    if (p.get(i).getPriority() < mx) {
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
            // Calculate the waiting time and completion time of each process

            // Non-preemtive priority algorithm
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
                    result.add(new Process());
                    result.get(z).setPid(idx+1);
                    result.get(z).setStart_time(p.get(idx).start_time);
                    result.get(z).setCompletion_time(p.get(idx).completion_time);
                    result.get(z).setBurst_time(p.get(idx).getBurst_time());
                    result.get(z).setColor(p.get(idx).getColor());
                    z++;
                }
                // Preemtive priority algorithm
                if (preemptive) {
                    if (burst_remaining[idx] == p.get(idx).getBurst_time()) {
                        p.get(idx).start_time = current_time;
                    }
                    // burst_remaining[idx] -= 1;
                    //  current_time++;
                    //   prev = current_time;

                    result.add(new Process());
                    result.get(z).setPid(idx+1);
                    result.get(z).setStart_time(current_time);
                    result.get(z).setColor(p.get(idx).getColor());


                    burst_remaining[idx] -= 1;
                    current_time++;
                    result.get(z).setCompletion_time(current_time);
                    result.get(z).setBurst_time(1);
                    z++;
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
                result.add(new Process());
                result.get(z).setPid(0);
                result.get(z).setStart_time(current_time);
                result.get(z).setCompletion_time(current_time+1);
                result.get(z).setBurst_time(1);
                result.get(z).setColor("grey");

                current_time++;
                z++;
            }

        }

        setAvg_turnaround_time((float) total_turnaround_time / n);
        setAvg_waiting_time((float) total_waiting_time / n);
        return result;


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

    public ArrayList<Process> QueueProcess(ArrayList<Process> queue, ArrayList<Process> p){
        //To set the color of the processes in queue
        for(int i = 0; i < queue.size(); i++){
            if(queue.get(i).getPid() == 0){
                queue.get(i).setColor("grey");
            }
            for(int j = 0; j < p.size(); j++){
                if(queue.get(i).getPid() == p.get(j).getPid()){
                    queue.get(i).setColor(p.get(j).getColor());
                }
            }

        }
        for(int i = 0; i < queue.size(); i++){
            queue.get(i).setRemain_time(queue.get(i).getBurst_time());
        }
        return queue;

    }
}