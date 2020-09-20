package com.yrl.scala

import java.util.Date

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, WindowedStream}
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow


object WordCount {

  def main(args: Array[String]): Unit = {
    //val environment = ExecutionEnvironment.getExecutionEnvironment
    val file = this.getClass.getClassLoader.getResource("WordCount").getPath;
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val socketSource: DataStream[String] = env.socketTextStream("localhost", 9999)
    val sensorRecordStream: DataStream[SensorRecord] = socketSource.map(fun = (data: String) => {
      println(data+":"+new Date())
      var datas = data.split(",")
      SensorRecord(datas(0), datas(1), datas(2).toDouble)
    })
    val timeStreamFor5min: WindowedStream[SensorRecord, String, TimeWindow] = sensorRecordStream.keyBy((data: SensorRecord) => {
      data.name
    }).timeWindow(Time.minutes(1))
    timeStreamFor5min.sum("temp").print("timeWindow:")
    print("time:"+new Date())
    sensorRecordStream.keyBy((data:SensorRecord)=>{data.name}).
      countWindow(5).
      sum("temp").print("count window:")


    sensorRecordStream.keyBy((data:SensorRecord)=>{data.name})
      //.timeWindow(Time.minutes(1))
        .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
      .process[List[SensorRecord]](new MyProcessFunction).print("process window:")

    env.execute()
    //environment.execute("yang")
  }
}
