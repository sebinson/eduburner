package eduburner.crawler;

import eduburner.crawler.enumerations.ProcessStatus;

public class ProcessResult {
    
    final public static ProcessResult PROCEED = 
        new ProcessResult(ProcessStatus.PROCEED);
    
    final public static ProcessResult FINISH =
        new ProcessResult(ProcessStatus.FINISH);
    
    final public static ProcessResult STUCK = 
        new ProcessResult(ProcessStatus.STUCK);
    
    
    final private ProcessStatus status;
    final private String jumpTarget;
    
    
    private ProcessResult(ProcessStatus status) {
        this(status, null);
    }
    
    
    private ProcessResult(ProcessStatus status, String jumpName) {
        this.status = status;
        this.jumpTarget = jumpName;
    }
    
    
    public ProcessStatus getProcessStatus() {
        return status;
    }
    
    
    public String getJumpTarget() {
        return jumpTarget;
    }
    
    
    public static ProcessResult jump(String jumpTarget) {
        return new ProcessResult(ProcessStatus.JUMP, jumpTarget);
    }
}
