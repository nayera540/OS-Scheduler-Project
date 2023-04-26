package piority;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Test {

	public static void main(String[] args){


		System.out.print("Enter the number of processes: ");
		Scanner sc = new Scanner( System.in );
		int n = sc.nextInt();
		ArrayList<Process> process = new ArrayList<>();
		for(int i=0; i<n; i++) {
			Process p = new Process();
			System.out.print("Enter arrival time of process " + (i+1) + ": ");
			p.arrival_time = sc.nextInt();
			System.out.print("Enter burst time of process " + (i+1) + ": ");
			p.burst_time = sc.nextInt();
			System.out.print("Enter priority of the process " + (i+1) + ": ");
			p.priority = sc.nextInt();
			p.pid = i+1;
			process.add(p);
			System.out.println();
		}
		PriorityScheduling pp =new PriorityScheduling();
		pp.Priority_Scheduling(process,true);

		System.out.println("#P\tAT\tBT\tPRI\tST\tCT\tTAT\tWT\tRT\n");
		for(int i = 0; i < n; i++) {
			System.out.println(process.get(i).pid + "\t" + process.get(i).arrival_time + "\t" + process.get(i).burst_time + "\t" + process.get(i).priority + "\t" + process.get(i).start_time + "\t" + process.get(i).completion_time + "\t" + process.get(i).turnaround_time + "\t" + process.get(i).waiting_time + "\t" + process.get(i).response_time + "\t" );
		}
		System.out.println("Average Turnaround Time = " + pp.getAvg_turnaround_time());
		System.out.println("Average Waiting Time = " + pp.getAvg_waiting_time());



	}




}
