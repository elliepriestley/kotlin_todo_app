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
  "taskName" : "Go for a walk",
  "createdDate" : "2024-03-01T16:04:43.20388",
  "editedDate" : "null",
  "status" : "NOT_DONE"
}, {
  "id" : "2",
  "taskName" : "Eat some cake",
  "createdDate" : "2024-03-01T16:04:57.100648",
  "editedDate" : "null",
  "status" : "NOT_DONE"
} ]
```


### GET /todos/status/{status}
- To get details of todos by status, either `DONE` or `NOT_DONE`

Request URL: `get /todos/status/DONE`

Response:
```
[ {
  "id" : "6",
  "taskName" : "Skip the gym",
  "createdDate" : "2024-03-01T17:19:55.287336",
  "editedDate" : "2024-03-01T17:25:29.453909",
  "status" : "DONE"
}, {
  "id" : "7",
  "taskName" : "Get ready for the evening",
  "createdDate" : "2024-03-01T17:25:58.865853",
  "editedDate" : "2024-03-01T17:26:11.725108",
  "status" : "DONE"
} ]
```

### GET /todos/{id}
- To get details of a single todo item 

Request URL: `get /todos/4`

Response:
```
{
  "id" : "4",
  "taskName" : "Feed Suki",
  "createdDate" : "2024-03-01T16:08:56.932709",
  "editedDate" : "null",
  "status" : "NOT_DONE"
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
  "id" : "7",
  "taskName" : "buy milk",
  "createdDate" : "2024-03-01T17:25:58.865853",
  "editedDate" : null,
  "status" : "NOT_DONE"
}
```

### PATCH /todos/123
- To edit a todo item, i.e. to change the name or to mark or unmark as done
- You may only change one field per request, i.e. `status` OR `taskName` but not both.

- Request URL: `PATCH /todos/7`
- Request Body:
```
{
    status: "DONE"
}
```
Response:
```
{
  "id" : "7",
  "taskName" : "buy milk",
  "createdDate" : "2024-03-01T17:25:58.865853",
  "editedDate" : "2024-03-01T17:30:15.992232",
  "status" : "DONE"
}
```




2. Rebuild it with event sourcing