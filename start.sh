# up the database
docker-compose up -d
sleep 5

./gradlew clean build bootRun