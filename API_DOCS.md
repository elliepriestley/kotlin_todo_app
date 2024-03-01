## Goal: learn about event sourcing in a hands-on manner.

1. Build a simple app without event sourcing
2. 
### GET /todos
- To get details of all todos

Request URL: `get /todos`

Response:
```
[ {
  "id" : "1",
  "taskName" : "Buy some plants",
  "status" : "DONE"
}, {
  "id" : "2",
  "taskName" : "Feed suki",
  "status" : "DONE"
} ]
```


### GET /todos/status/{status}
- To get details of todos by status, either `DONE` or `NOT_DONE`

Request URL: `get /todos/status/NOT_DONE`

Response:
```
[ {
  "id" : "3",
  "taskName" : "Buy dinner",
  "status" : "NOT_DONE"
}, {
  "id" : "7",
  "taskName" : "Another task ",
  "status" : "NOT_DONE"
} ]
```

### GET /todos/{id}
- To get details of a single todo item 

Request URL: `get /todos/123`

Response:
```
{
    id: "123"
    name: "buy milk"
    createdDate: "2024-02-27T13:45:43.999Z+1"
    status: "NOT_DONE"
}

```

### POST /todos
- To add a todo item 

Request
```
{
    name: "buy milk"
}
```
Response:
```
{
    id: "123",
    name: "buy milk",
    createdDate: "2024-02-27T13:45:43.999Z+1" // ISO8601
    status: "NOT_DONE"
}
```

### PATCH /todos/123
- To edit a todo item, i.e. to change the name or to mark or unmark as done
- You may only change one field per request, i.e. `status` OR `taskName` but not both.

Request:
```
{
    status: "NOT_DONE"
}
```
Response:
```
{
    id: "123",
    name: "buy soy milk",
    createdDate: "2024-02-27T13:48:43.999Z+1" // ISO8601
    modifiedDate: "2024-02-27T13:50:43.999Z+1" // ISO8601
    status: "DONE"
}
```




2. Rebuild it with event sourcing