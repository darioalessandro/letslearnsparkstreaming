#!/usr/bin/env bash

if [ -z "${KAFKA_HOME}" ]; then
    echo "KAFKA_HOME is unset or set to the empty string"
    exit -1
fi

cd $KAFKA_HOME

echo "Starting Zookeeper"
./bin/zookeeper-server-start.sh config/zookeeper.properties &
sleep 20
echo "Starting Kafka Broker"
./bin/kafka-server-start.sh config/server.properties &
sleep 20
echo "Started Kafka consumer"
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streaming_notifications --from-beginning