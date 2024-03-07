package todo.app.repo

import todo.app.eventmodel.Event
import java.util.*

interface AppendEventRepoInterface {
    fun appendEvent(event: Event): Event
    fun fetchAllEvents(): List<Event>

    fun fetchEventsByTaskId(taskId: UUID): List<Event>
}
//interface MyEventStore {
//    fun storeEvent(event: Event)
//}
//class ClassBasedRepo(private val eventStore: MyEventStore): AppendEventRepoInterface {
//    override fun appendEvent(event: Event): Event {
//        eventStore.storeEvent(event)
//        return event
//    }
//}
//
//
//
//fun FunctionBasedRepo(eventStore: MyEventStore): (Event) -> Event {
//    return function(eventStore)
//}
//
//private fun function(eventStore: MyEventStore) = { event ->
//    eventStore.storeEvent(event)
//    event
//}
//
//val myfunc: (MyEventStore) -> (Event) -> Event = { myEventStore: MyEventStore ->
//    { event: Event ->
//        myEventStore.storeEvent(event)
//        event
//    }
//
//}
//
//fun main() {
//    val eventStore = object: MyEventStore {
//        override fun storeEvent(event: Event) {
//            TODO("Not yet implemented")
//        }
//    }
//
//    val classBased = ClassBasedRepo(eventStore)
//    val functionBased = FunctionBasedRepo(eventStore)
//    val myAwfulFunctionOne = myfunc(eventStore)
//
//    classBased.appendEvent(Event)
//    myAwfulFunctionOne(Event)
//    functionBased(Event)
//}
//
////val MyFunInterfaceApproach = AppendEventRepoInterface { event ->
////    TODO()
////}
//
//
//

