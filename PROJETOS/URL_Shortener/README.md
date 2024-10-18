# Comandos

mvn clean install   
docker build -t url-shortener .
docker run -d -p 8080:8080 url-shortener 

docker run --name teste -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=urlshortener -p 5432:5432 -d postgres:17

docker run --name my-app2 --link teste:db -e SPRING_DATASOURCE_URL=jdbc:postgresql://teste:5432/urlshortener -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=root -p 8080:8080 -d test
