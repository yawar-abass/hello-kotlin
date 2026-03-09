package org.example.rpm

enum class TASKSTATUS{
    PENDING, COMPLETED, FAILED
}

data class AgentTask(val id:Int, val prompt:String, val status:TASKSTATUS=TASKSTATUS.PENDING, val result:String?)


interface AiAgent {
    fun processTask(task: AgentTask): AgentTask

}

class SummarizationAgent(task: AgentTask):AiAgent{

    override fun processTask(task: AgentTask): AgentTask {
        return task.copy(id=task.id, prompt =  task.prompt, status =  TASKSTATUS.COMPLETED, result="This is the result summary of task")
    }

}

class VisionAgent(task: AgentTask):AiAgent{
    override fun processTask(task: AgentTask): AgentTask {
        return if (task.prompt.contains("image")){
            task.copy(id=task.id, prompt =  task.prompt, status =  TASKSTATUS.COMPLETED, result="Image has been processed")
        } else{
            task.copy(id = task.id, prompt = task.prompt, status = TASKSTATUS.FAILED, result = "Task fialed as it is not vision task")
        }
    }
}

object Orchestrator{
    private var tasks= mutableListOf<AgentTask>()
    fun  addNewTask(task: AgentTask){
        tasks.also {
            println("Task added : ${task.id}")
        }.add(task)
    }
}

fun executeWithRetry(task: AgentTask, maxRetries:Int,action:(AgentTask)->AgentTask){
    val count:Int =0

    if (count<maxRetries && task.status === TASKSTATUS.FAILED  ){
        val resultTask =action(task)
        executeWithRetry(resultTask,maxRetries-1,action)
    }
    else{
        return
    }
}

fun  List<AgentTask>.generateReport(){

}