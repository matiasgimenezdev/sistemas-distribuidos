docker-compose up -d
sleep 12
curl -X GET http://localhost:9000/register
echo -e "\r"
curl -X GET http://localhost:9001/register
echo -e "\r"
curl -X GET http://localhost:9002/register
echo -e "\r"
curl -X GET http://localhost:9003/register
echo -e "\r"
