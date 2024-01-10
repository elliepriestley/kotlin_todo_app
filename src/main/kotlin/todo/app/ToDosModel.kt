package todo.app

class ToDosModel(tasks: MutableList<String>) {
    var tasks = tasks

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
