package com.rajat.TaskManager.DTO;

public class TaskSummaryDTO {
    private long totalTasks;
    private long completedTasks;
    private long highPriorityCount;
    private long pendingTasks;
    private long dueToday;

    public long getTotalTasks(){
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks){
        this.totalTasks = totalTasks;
    }

    public long getCompletedTasks(){
        return completedTasks;
    }

    public void setCompletedTasks(long completedTasks){
        this.completedTasks = completedTasks;
    }

    public long getHighPriorityCount(){
        return highPriorityCount;
    }

    public void setHighPriorityCount(long highPriorityCount){
        this.highPriorityCount = highPriorityCount;
    }

    public long getDueToday(){
        return dueToday;
    }

    public void setDueToday(long dueToday){
        this.dueToday = dueToday;
    }

    public long getPendingTasks(){
        return pendingTasks;
    }

    public void setPendingTasks(long pendingTasks){
        this.pendingTasks = pendingTasks;
    }
}
