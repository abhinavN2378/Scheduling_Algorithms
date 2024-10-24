import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class PriorityScheduling {
    public static void getPriorityBasedScheduling(HashMap<String, ProcessObject> processMap) {
        List<Map.Entry<String, ProcessObject>> processList
                = new LinkedList<Map.Entry<String, ProcessObject>>(
                (Collection<? extends Map.Entry<String, ProcessObject>>) processMap.entrySet());
        Collections.sort(
                processList,
                (i1,
                 i2) -> i1.getValue().getPriority().compareTo(i2.getValue().getPriority()));

        String result = "";
        Integer previousJobEndTime = 0;

        for (Map.Entry<String, ProcessObject> aa : processList) {
            result += aa.getKey();
            result += "("+(previousJobEndTime)+"-"+(previousJobEndTime+aa.getValue().getBurstTime())+")\n";
            previousJobEndTime += aa.getValue().getBurstTime();
        }

        System.out.println(result);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("priority_input.csv"));

        HashMap<String, ProcessObject> processMap = new HashMap<>();

        while (sc.hasNext()) {
            String str = sc.nextLine();
            String[] field = str.split(",");
            processMap.put(field[0], new ProcessObject(field[0], Integer.parseInt(field[1]), Integer.parseInt(field[2]), Integer.parseInt(field[3])));

        }

        PriorityScheduling.getPriorityBasedScheduling(processMap);
    }

}
