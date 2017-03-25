#!/bin/sh


# current time ms: 1490448842047 ~~> 2017-03-25 14:34:02.659

curl -H "Content-Type: application/json" -X POST -d '{"time":"1490455965687","amount":"223.7"}' http://localhost:10080/transactions
curl -H "Content-Type: application/json" -X POST -d '{"time":"1490456008086","amount":"20.0"}' http://localhost:10080/transactions
curl -H "Content-Type: application/json" -X POST -d '{"time":"1490459160672","amount":"34.8"}' http://localhost:10080/transactions

