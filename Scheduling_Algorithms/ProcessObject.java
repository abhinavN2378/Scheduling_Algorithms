public class ProcessObject {
    private String processNumber;
    private Integer arrivalTime;
    private Integer burstTime;
    private Integer priority;

    public ProcessObject(String processNumber, Integer arrivalTime, Integer brustTime, Integer priority) {
        this.processNumber = processNumber;
        this.arrivalTime = arrivalTime;
        this.burstTime = brustTime;
        this.priority = priority;
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(Integer burstTime) {
        this.burstTime = burstTime;
    }
}
