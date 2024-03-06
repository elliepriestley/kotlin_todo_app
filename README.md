# ToDoApp

### Milestone 1
## Functionality
- Add a todo item 
  - TODO_ITEM_CREATED (event type - enum class)
  - taskId, taskName (properties of the event)
- Edit todo item 
  - TODO_ITEM_NAME_UPDATED (event type)
  - taskId, taskName
- Mark todo as done
  - TODO_ITEM_MARKED_AS_DONE
  - taskId
- Unmark done todo 
  - TODO_ITEM_MARKED_AS_NOT_DONE
  - taskId
- Get details of a single todo item
- Get details of all todo items by status

## Tech requirements
- Should be usable only as an API 
- Should store data in text files

### Milestone 2
- Separate the reads and writes into different domains. For each read operation (GETs) only return the information we will need for that specific operation.
- For example, for the 'get all todos' operation, return a ToDoModel with only taskId,status and taskName.

### Milestone 3 
- Introduce event sourcing. 
- For the write operations, create events and append them to an events_log file.

base properties of events: eventId, eventName, eventCreatedDate, eventCreator, analytics data

### EXAMPLE:

EVENTS LOG
- ToDo item created: id1 (id, taskName)
- Todo item created: id2 (id, taskName)
- Todo item name updated :id1 (id, taskName)
- Todo item name updated :id1 (id, taskName)
- Todo item marked as done id2 (id)
- Todo item marked as done id1 (id)

- To calculate the state for id1: replay all id1 events and update the model.
- Domain method - get details of single todoItem. - create empty ToDoItem, process first event, update todoItem accordingly. etc. etc. 
