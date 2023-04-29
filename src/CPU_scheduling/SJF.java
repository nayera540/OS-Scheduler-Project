import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
* class which simulates the shortest job first scheduler
* */
public class SJFScheduler {
    private static final int noProcess = -1;
    private float averageWaitingTime;
    private float averageTurnaroundTime;
    private final int timeSlot;
    Queue<Process> processQueue;



    public  SJFScheduler(){
        this.timeSlot = 1;
        this.averageWaitingTime = 0;
        this.averageTurnaroundTime = 0;
    }
    public SJFScheduler(int timeSlot){
        if (timeSlot<1) throw new RuntimeException("time slot cannot be less than 1");
        this.timeSlot = timeSlot;
        this.averageWaitingTime = 0;
        this.averageTurnaroundTime = 0;
    }
    public float getAverageWaitingTime(){
        return averageWaitingTime;
    }
    public float getAverageTurnaroundTime(){
        return averageTurnaroundTime;
    }
    public  int getTimeSlot(){
        return timeSlot;
    }

    /**
    * simulates the shortest job first scheduling and fills the processQueue according to which job was scheduled first,
     * if preemtive is true the shortest job is scheduled for a duration = timeSlot, then the processes array is
     * checked again. if preemtive is false the shortest job is scheduled for a duration = the burst time of that
     * process
     *
     * @param processes a list of processes to be scheduled
     * @param preemptive determines if the scheduling is preemptive or not ie. if the process is interrupted after
     *                   its time slot is finished or not
    */
    public void schedule(ArrayList<Process> processes, boolean preemptive) {
        int currentTime = 0, completedProcesses = 0, numberOfProcesses = processes.size();
        int idleTime = 0;
        processQueue = new PriorityQueue<>();

        //preemptive scheduling
        if (preemptive) {
            while (completedProcesses != numberOfProcesses) {
                Process currentProcess = getShortestProcessIndex( processes, currentTime);

                if (currentProcess == null) {
                    currentTime++;
                    idleTime++;

                }
                else {
                    if(idleTime != 0){
                        Process idle = new Process(0,currentTime,idleTime,-1,"grey");
                        processQueue.add(idle);
                        idleTime = 0;
                    }
                    currentProcess.decrementRemain_time(timeSlot);
                    processQueue.add(currentProcess);
                    currentTime++;

                    if (currentProcess.getRemain_time() == 0) { /* if the process is fully completed set
                    completion time to current time and increment completedProcesses*/
                        currentProcess.setCompletion_time(currentTime);
                        completedProcesses++;
                    }
                }
            }

            //calculate turnaround time and waiting time for every process
            for (Process process: processes) {
                calculateOutputs(process);
            }
        }
        //non-preemptive scheduling
        else {

            while (completedProcesses != numberOfProcesses) {
                Process currentProcess =  getShortestProcessIndex( processes, currentTime);
                if (currentProcess == null) { /*  if there's no process increment time and idle time */
                    currentTime++;
                    idleTime++;
                }
                else {
                    if(idleTime != 0){ /* if there's an idle time add it to queue */
                        Process idle = new Process(0,currentTime,idleTime,-1,"grey");
                        processQueue.add(idle); /* add idle time to queue */
                        idleTime = 0; /* reset idle time */
                    }
                    int completionTime = currentTime + currentProcess.getBurst_time();
                    //calculate turnaround time and waiting time for every process
                    calculateOutputs(currentProcess);

                    currentTime += currentProcess.getBurst_time();
                    currentProcess.setRemain_time(0);
                    currentProcess.setCompletion_time(completionTime);
                    completedProcesses++;
                    processQueue.add(currentProcess);
                }
            }
        }


        for (Process process: processes) {
            this.averageWaitingTime += process.getWaiting_time();
            this.averageTurnaroundTime += process.getTurnaround_time();
            process.setRemain_time(process.getBurst_time()); /* reset remaining time to burst time */
        }
        this.averageWaitingTime /= numberOfProcesses;
        this.averageTurnaroundTime /= numberOfProcesses;
    }

    /**
     * calculates turnaround time and waiting time for the current process and modifies it in the process object
     * @param process process for which the output is calculated and modified in it
     */
    private void calculateOutputs(Process process){
        int turnAroundTime = process.getCompletion_time()- process.getArrival_time();
        int waitingTime = process.getTurnaround_time() - process.getBurst_time();
        process.setTurnaround_time(turnAroundTime);
        process.setWaiting_time(waitingTime);
    }

    /**
     * returns the shortest job(with the smallest remaining time greater than 0) that has an arrivalTime less than the currentTime
     * @param processes list of processes
     * @param currentTime represents the current running time
     * @returns Process the shortest process, if not found returns null
    */
    private Process getShortestProcessIndex(ArrayList<Process> processes, int currentTime){
        int min = processes.get(1).getBurst_time(), counter = noProcess;
        for (int i = 0; i < processes.size(); i++) {
            //get the shortest job
            if ((processes.get(i).getArrival_time() <= currentTime) && (processes.get(i).getRemain_time() != 0) && (processes.get(i).getRemain_time() < min)) {
                min = processes.get(i).getBurst_time();
                counter = i;
            }
        }
        if(counter == noProcess)
            return null;
        else
            return processes.get(counter);
    }

}
