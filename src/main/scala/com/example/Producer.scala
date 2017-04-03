package com.example

import cakesolutions.kafka.{KafkaProducer, KafkaProducerRecord}
import cakesolutions.kafka.KafkaProducer.Conf
import org.apache.kafka.common.serialization.StringSerializer


object Producer {

  val producer = KafkaProducer(
    Conf(new StringSerializer(), new StringSerializer(), bootstrapServers = "localhost:9092")
  )

  val topic = "streaming_notifications"

  def main(args: Array[String]): Unit = {

    var theta : Float = 0
    while(true) {
      val value = Math.sin(theta)
      theta = theta + 0.1F
      println("value " + value)
      val record = KafkaProducerRecord(topic, Some("sine"), value.toString)
      producer.send(record)
      Thread.sleep(1)
    }
  }
}
