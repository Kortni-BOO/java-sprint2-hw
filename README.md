# java-sprint2-hw
Second sprint homework
<h3>Class HTTPTaskServer:</h3>
<h1 align="center"> HTTP Method: </h1>
<h3>GET /tasks:</h3>
No parameters, 
Response code 200,
Response content type application/json
<h3>GET /tasks/task</h3>
No parameters, 
Response code 200,
Response content type application/json
<h3>GET /tasks/task?id=1</h3>
<h5>Получение task по ID</h5>
No parameters, 
Response code 200,
Response content type application/json
<h3>GET /tasks/epic</h3>
No parameters, 
Response code 200,
Response content type application/json
<h3>GET /tasks/history</h3>
No parameters, 
Response code 200,
Response content type application/json
<h3>GET /tasks/subtask?id=1</h3>
<h5>Получение subTask по ID epic</h5>
No parameters, 
Response code 200,
Response content type application/json
<h3>POST /tasks/task</h3>
Создание нового эпика. 
Параметры эпика передаются в теле запроса<br>
{<br>
"title" : "Test",<br>
"subtitle" : "Test",<br>
"startTime" : "2022-12-03T22:55",<br>
"duration" : 121<br>
}<br>
Response code 201,

<h3>DELETE /tasks/task</h3>
<h5>Удаляет все задачи созданные ранее </h5>
No parameters,
Response code 200,
Response content type application/json
<h3>DELETE /tasks/task?id=1</h3>
<h5>Удаляет задачу по ID</h5>
No parameters,
Response code 200,
Response content type application/json