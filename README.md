#Stock data 

Rodar o yugabyte no docker 
docker run -d --name yugabyte -p7000:7000 -p9000:9000 -p15433:15433 -p5433:5433 -p9042:9042  yugabytedb/yugabyte:2.19.3.0-b140 bin/yugabyted start  --daemon=false


Swagger
http://localhost:8009/swagger-ui/index.html


Exemplo de chamada recebendo o token  
http://localhost:8009/api/v1/auth/register

{
"email":"email-1234-teste@gmail.com",
"password":"12345"
}