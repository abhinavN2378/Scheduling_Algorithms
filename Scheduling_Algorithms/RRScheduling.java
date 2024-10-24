import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class RRScheduling {
    public static void findWaitingTime(ArrayList<String> pId,ArrayList<Integer> wt_time,int n ,ArrayList<Integer>  cpu_time,int rrTimeQ,ArrayList<Integer>  ct,ArrayList<Integer>  entry_time){

        ArrayList<Integer> rt = new ArrayList<>();

        for(int i=0;i<wt_time.size();i++){
            rt.add(i,cpu_time.get(i));
        }
        int t=0;
        int arrival=0;
        while(true){
            boolean done = true;
            for(int i=0;i<n;i++){
                if(rt.get(i)>0){
                    done =false;
                    if(rt.get(i)>rrTimeQ && entry_time.get(i)<=arrival){
                        t +=rrTimeQ;
                        int rem = rt.get(i)-rrTimeQ;
                        rt.set(i,rem);
                        arrival++;
                    }
                    else{
                        if(entry_time.get(i)<=arrival){
                            arrival++;
                            t+=rt.get(i);
                            rt.set(i,0);
                            ct.set(i,t); }
                    }
                }
            }

            if(done==true)
            {
                break;
            }
        }
    }
    public static void findTurnAroundTime(ArrayList<String>  process ,ArrayList<Integer>  wt_time,int n,ArrayList<Integer>  brusttime,ArrayList<Integer>  tat_time,ArrayList<Integer>  completion_time,ArrayList<Integer>  arrival_time){
        for(int i=0;i<n;i++){
            tat_time.set(i, completion_time.get(i)-arrival_time.get(i));
            wt_time.set(i, tat_time.get(i)-brusttime.get(i));

        }

    }

    public static void findAvgTime(ArrayList<String > processIds,int n,ArrayList<Integer> brust_time,int quantum,ArrayList<Integer>  arrival_time){
        ArrayList<Integer> wt_time = new ArrayList<>();
        ArrayList<Integer>  tat_time = new ArrayList<>();
        ArrayList<Integer>  completion_time = new ArrayList<>();
        for(int i=0;i<processIds.size();i++){
            wt_time.add(0);
            tat_time.add(0);
            completion_time.add(0);
        }

        findWaitingTime(processIds,wt_time,n,brust_time,quantum,completion_time,arrival_time);
        findTurnAroundTime(processIds,wt_time,n,brust_time,tat_time,completion_time,arrival_time);
        int total_wt = 0, total_tat = 0;

        System.out.println("Processes " +" ArrivalTime\t"+ "  BurstTime " +" completionTime"+
                " TurnAroundTime " + " Waitingtime");
        for (int i=0; i<n; i++)
        {
            total_wt = total_wt + wt_time.get(i);
            total_tat = total_tat + tat_time.get(i);
            System.out.println(" " + processIds.get(i) + "\t\t\t\t"+ arrival_time.get(i)+"\t\t\t"+ + brust_time.get(i) +"\t\t\t" +completion_time.get(i)+"\t\t\t\t"
                    +tat_time.get(i) +"\t\t\t\t " + wt_time.get(i));
        }

        System.out.println("Average waiting time = " +
                (float)total_wt / (float)n);
        System.out.println("Average turn around time = " +
                (float)total_tat / (float)n);
    }

    void rrScheduling() {

    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File( "RR_input.csv"));
        ArrayList<Integer> arrival_time = new ArrayList<>();
        ArrayList<String> process_Ids = new ArrayList<>();
        ArrayList<Integer> brust_time = new ArrayList<>();
        int quantum =4;
        int i=0;

        while (sc.hasNext()) {
            String str = sc.nextLine();
            String[] field = str.split(",");
            process_Ids.add(i,field[0]);
            arrival_time.add(i,Integer.parseInt(field[1]));
            brust_time.add(i,Integer.parseInt(field[2]));
            i++;
        }
        sc.close();

        int currentIndex = 0;
        String result = "";
        Integer previousJobEndTime = 0;
        while(true) {
            if(currentIndex>=process_Ids.size()) {
                currentIndex = 0;
            }
            if(process_Ids.size()==0){
                break;
            }

            if(quantum<=brust_time.get(currentIndex)) {
                brust_time.set(currentIndex, brust_time.get(currentIndex)-quantum);
                result += process_Ids.get(currentIndex) + "(" + previousJobEndTime + "-" + (previousJobEndTime+quantum) + ")";
                previousJobEndTime += quantum;
            } else {
                Integer curBurstTime = brust_time.get(currentIndex);
                brust_time.set(currentIndex, 0);
                result += process_Ids.get(currentIndex) + "(" + previousJobEndTime + "-" + (previousJobEndTime+curBurstTime) + ")";
                previousJobEndTime += curBurstTime;
            }
            if(brust_time.get(currentIndex)<=0){
                brust_time.remove(currentIndex);
                process_Ids.remove(currentIndex);
                arrival_time.remove(currentIndex);
                currentIndex--;
            }
            currentIndex++;
        }

        System.out.println(result);

    }
}




