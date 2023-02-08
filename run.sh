mvn clean package
docker-compose -f docker-compose-mysql.yml up --build -d
docker-compose -f docker-compose-redis.yml up --build -d
docker-compose -f docker-compose-core-app.yml up --build -d
docker image prune -a -f