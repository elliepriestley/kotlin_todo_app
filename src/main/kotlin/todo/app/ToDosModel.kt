package todo.app

class ToDosModel {
    var tasks = mutableListOf<String>()


    fun addTask(task: String) {
        tasks.add(task)
    }

    fun clearTasks() {
        tasks.removeAll(tasks)
    }

    fun returnTasks():List<String> {
        return this.tasks
    }

}
