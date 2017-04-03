#!/usr/bin/env bash

if [ -z "${KAFKA_HOME}" ]; then
    echo "KAFKA_HOME is unset or set to the empty string"
    exit -1
fi

cd $KAFKA_HOME

./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streaming_notifications