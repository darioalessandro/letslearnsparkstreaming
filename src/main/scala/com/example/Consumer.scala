package com.example

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by darioalessandro on 4/2/17.
  */
object Consumer extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "SparkStreamingConsumer",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val conf = new SparkConf().setAppName("SineWave").setMaster("local[*]")
  val s =  new StreamingContext(conf, Seconds(5))

  val topics = Array("streaming_notifications")

  val stream = KafkaUtils.createDirectStream[String, String](
    s,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams)
  )

  val signalValuesRDD = stream.map(_.value.toDouble)

  signalValuesRDD.foreachRDD { record =>
    val values : Double = record.mean()
    println(values)
  }

  s.start()
  s.awaitTermination()
  s.stop()

}
