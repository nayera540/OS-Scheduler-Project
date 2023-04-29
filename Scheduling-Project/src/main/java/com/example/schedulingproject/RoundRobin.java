package com.example.schedulingproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {
    float avg_turnaround_time;
    float avg_waiting_time;
    int total_turnaround_time = 0;
    int total_waiting_time = 0;
    int[] burst_remaining = new int[100];
    int idx;

    public ArrayList<Process> Round_Robin(ArrayList<Process> p, int tq) {
        int n = p.size();
        ArrayList<Process> result = new ArrayList<>();
        Collections.sort(p, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                if (p1.getArrival_time() < p2.getArrival_time()) {
                    return -1;
                } else if (p1.getArrival_time() > p2.getArrival_time()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for (int i = 0; i < n; i++) {
            burst_remaining[i] = p.get(i).getBurst_time();
        }

        Queue<Integer> q = new LinkedList<>();
        int current_time = 0;
        q.add(0);
        int completed = 0;
        int[] mark = new int[100];
        Arrays.fill(mark, 0);
        mark[0] = 1;
        int z = 0;
        while (completed != n) {
            idx = q.peek();
            q.remove();

            if (burst_remaining[idx] == p.get(idx).getBurst_time()) {

                p.get(idx).start_time = Math.max(current_time, p.get(idx).getArrival_time());
                if (p.get(idx).getArrival_time() > current_time) {
                    result.add(new Process());
                    result.get(z).setPid(0);
                    result.get(z).setStart_time(current_time);
                    result.get(z).setCompletion_time(p.get(idx).getArrival_time());
                    result.get(z).setBurst_time(result.get(z).getCompletion_time()-result.get(z).getStart_time());
                    result.get(z).setColor("grey");

                    z++;

                }
                current_time = p.get(idx).start_time;

            }

            if (burst_remaining[idx] - tq > 0) {
                burst_remaining[idx] -= tq;
                result.add(new Process());
                result.get(z).setStart_time(current_time);
                current_time += tq;
                //result.add(new Process());
                result.get(z).setPid(p.get(idx).getPid());
                // result.get(z).setArrival_time(p.get(idx).getArrival_time());
                //result.get(z).setStart_time(p.get(idx).getStart_time());
                result.get(z).setCompletion_time(current_time);

                result.get(z).setBurst_time(result.get(z).getCompletion_time()-result.get(z).getStart_time());
                result.get(z).setColor(p.get(idx).getColor());
                z++;

            } else {
                result.add(new Process());
                result.get(z).setStart_time(current_time);
                current_time += burst_remaining[idx];
                //result.add(new Process());
                result.get(z).setPid(p.get(idx).getPid());
                // result.get(z).setArrival_time(p.get(idx).getArrival_time());
                // result.get(z).setBurst_time(p.get(idx).getBurst_time());
                //result.get(z).setStart_time(p.get(idx).getStart_time());
                burst_remaining[idx] = 0;
                completed++;

                p.get(idx).completion_time = current_time;
                result.get(z).setCompletion_time(p.get(idx).getCompletion_time());
                result.get(z).setBurst_time(result.get(z).getCompletion_time()-result.get(z).getStart_time());

                z++;
                p.get(idx).turnaround_time = p.get(idx).completion_time - p.get(idx).getArrival_time();
                p.get(idx).waiting_time = p.get(idx).turnaround_time - p.get(idx).getBurst_time();

                total_turnaround_time += p.get(idx).turnaround_time;
                total_waiting_time += p.get(idx).waiting_time;

            }
            for (int i = 1; i < n; i++) {
                if (burst_remaining[i] > 0 && p.get(i).getArrival_time() <= current_time && mark[i] == 0) {
                    q.add(i);
                    mark[i] = 1;
                }
            }
            if (burst_remaining[idx] > 0) {
                q.add(idx);
            }

            if (q.isEmpty()) {
                for (int i = 1; i < n; i++) {
                    if (burst_remaining[i] > 0) {
                        q.add(i);
                        mark[i] = 1;
                        break;
                    }
                }
            }

        }
        avg_turnaround_time = (float) total_turnaround_time / n;
        avg_waiting_time = (float) total_waiting_time / n;

        Collections.sort(p, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                return Integer.compare(p1.getPid(), p2.getPid());
            }

        });
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
