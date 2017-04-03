#!/usr/bin/env bash

if [ -z "${KAFKA_HOME}" ]; then
    echo "KAFKA_HOME is unset or set to the empty string"
    exit -1
fi

cd $KAFKA_HOME


echo "Stopping Zookeeper"
./bin/zookeeper-server-stop.sh config/zookeeper.properties
echo "Stopping Kafka Broker"
./bin/kafka-server-stop.sh config/server.properties