import java.io.File;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.*;

public class SRTScheduling {
    private static class Process {
        private final String PID;
        private final int arrivalTime;
        private int burst;
        private int completeTime;
        private boolean dispatched;

        public Process(String PID, int arrivalTime, int burst) {
            this.PID = PID;
            this.arrivalTime = arrivalTime;
            this.burst = burst;
            this.completeTime = -1;
            this.dispatched = false;
        }

        public String getPID() {
            return this.PID;
        }

        public int getArrivalTime() {
            return this.arrivalTime;
        }

        public int getBurst() {
            return this.burst;
        }

        public void reduceBurst(int q) {
            burst -= (int) Math.min(burst, q);
        }

        public void setCompleteTime(int time) {
            this.completeTime = time;
        }

        public int getCompleteTime() {
            return this.completeTime;
        }

        public boolean isCompleted() {
            return this.completeTime != -1;
        }

        public void setDispatched() {
            this.dispatched = true;
        }

        public boolean isDispatched() {
            return this.dispatched;
        }
    }

    private static boolean checkCompleted(ArrayList<Process> parr) {
        for(Process p : parr) {
            if(!p.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(new File("input_srtf.csv"));
        ArrayList<Process> parr = new ArrayList<>();
        while (sc.hasNext()) {
            String str = sc.nextLine();
            String[] field = str.split(",");
            parr.add(new Process(field[0], Integer.parseInt(field[1]), Integer.parseInt(field[2])));
        }

        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        PrintWriter out = new PrintWriter(osw);


        int clock = 0;
        int prevCompletionTime = 0;
        int quantum = 1;
        String resultPids = " ";
        String resultCTimes = "0--";
        Process prevProc = null;
        while(!checkCompleted(parr)) {
            // Detect shortest burst process which
            // has not completed
            Process minBurstProc = null;
            int minBurst = Integer.MAX_VALUE;

            for(Process p : parr) {
                if(!p.isCompleted() && p.getArrivalTime() <= clock && p.getBurst() < minBurst) {
                    minBurst = p.getBurst();
                    prevProc = minBurstProc;
                    if(prevProc==null) {
                        prevProc = p;
                    }
                    minBurstProc = p;
                }
            }
            prevCompletionTime = clock+1;

            resultPids += minBurstProc.getPID()+" ";
            if(prevCompletionTime>=10)
                resultCTimes += prevCompletionTime+"-";
            else
                resultCTimes += prevCompletionTime+"--";
            clock += quantum;

            if(minBurstProc != null) {
                minBurstProc.reduceBurst(quantum);

                if(minBurstProc.getBurst() == 0) {
                    minBurstProc.setCompleteTime(clock);
                }
            }
        }
        out.println(resultPids);
        out.println(resultCTimes.substring(0,resultCTimes.length()-1));
        out.close();
    }
}