import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PrioritySchedulingRR {

    static Integer previousJobEndTime = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("priorityrr_input.csv"));
        int quantum =2;
        HashMap<String, ProcessObject> processMap = new HashMap<>();

        while (sc.hasNext()) {
            String str = sc.nextLine();
            String[] field = str.split(",");
            processMap.put(field[0], new ProcessObject(field[0], Integer.parseInt(field[1]), Integer.parseInt(field[2]), Integer.parseInt(field[3])));
        }

        PrioritySchedulingRR.getPriorityRRBasedScheduling(processMap, quantum);
    }

    private static void getPriorityRRBasedScheduling(HashMap<String,ProcessObject> processMap, int quantum) {
        TreeMap<Integer, LinkedList<ProcessObject>> map = getMapBasedOnPriority(processMap);
        for (Map.Entry<Integer,LinkedList<ProcessObject>> entry: map.entrySet()) {
            if(entry.getValue().size()>1){
                applyRR(entry.getValue(), quantum);
            } else {
                ProcessObject po = entry.getValue().get(0);
                String result = "";
                result += po.getProcessNumber();
                result += "("+(previousJobEndTime)+"-"+(previousJobEndTime+po.getBurstTime())+")\n";
                previousJobEndTime += po.getBurstTime();
                System.out.print(result);
            }
        }
    }

    private static void applyRR(LinkedList<ProcessObject> list, int quantum) {
        int currentIndex = 0;
        String result = "";
        ArrayList<Integer> arrival_time = new ArrayList<>();
        ArrayList<String> process_Ids = new ArrayList<>();
        ArrayList<Integer> brust_time = new ArrayList<>();
        for(ProcessObject po: list) {
            process_Ids.add(po.getProcessNumber());
            arrival_time.add(po.getArrivalTime());
            brust_time.add(po.getBurstTime());
        }
        while(true) {
            if(currentIndex>=process_Ids.size()) {
                currentIndex = 0;
            }
            if(process_Ids.size()==0){
                break;
            }
            if(process_Ids.size()==1) {
                result += process_Ids.get(currentIndex) + "(" + previousJobEndTime + "-" + (previousJobEndTime+brust_time.get(0)) + ")\n";
                previousJobEndTime += brust_time.get(0);
                brust_time.set(currentIndex, 0);
            } else if(quantum<=brust_time.get(currentIndex)) {
                brust_time.set(currentIndex, brust_time.get(currentIndex)-quantum);
                result += process_Ids.get(currentIndex) + "(" + previousJobEndTime + "-" + (previousJobEndTime+quantum) + ")\n";
                previousJobEndTime += quantum;
            } else {
                Integer curBurstTime = brust_time.get(currentIndex);
                brust_time.set(currentIndex, 0);
                result += process_Ids.get(currentIndex) + "(" + previousJobEndTime + "-" + (previousJobEndTime+curBurstTime) + ")\n";
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
        System.out.print(result);
    }

    private static TreeMap<Integer , LinkedList<ProcessObject>> getMapBasedOnPriority(HashMap<String,ProcessObject> processMap) {
        TreeMap<Integer, LinkedList<ProcessObject>> map = new TreeMap<>();
        List<Map.Entry<String , ProcessObject>> processList = new LinkedList<Map.Entry<String, ProcessObject>>(processMap.entrySet());
        Collections.sort(processList, (i1, i2) -> i1.getValue().getPriority().compareTo(i2.getValue().getPriority()));
        for (Map.Entry<String, ProcessObject> aa : processList) {
            LinkedList list = (LinkedList) map.getOrDefault(aa.getValue().getPriority(), new LinkedList<>());
            list.add(aa.getValue());
            map.put(aa.getValue().getPriority(), list);
        }
        return map;
    }
}
