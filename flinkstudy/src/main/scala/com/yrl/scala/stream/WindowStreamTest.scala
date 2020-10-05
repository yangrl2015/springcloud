package com.yrl.scala.stream

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.scala._
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.{TumblingEventTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time

case class Record(id:String, name:String, temp: Double, time: Long)

class MyAggrator(s: Double, id: Long) {
  var sum: Double =_
  var lastId: Long=_
   def MyAggrator: Unit = {
     this.sum += s
     this.lastId = id
   }


  override def toString = s"MyAggrator($sum, $lastId)"
}
 class WindowStreamTest {
   val streamEnv = StreamExecutionEnvironment.getExecutionEnvironment
   val socketSource = streamEnv.socketTextStream("127.0.0.1", 9999)
 def testTumblingWindow(): Unit={
   val recordStream: DataStream[Record] = socketSource.map(content => {
     val str = content.split(",")
     Record(str(0), str(1), str(2).toDouble, str(3).toLong)
   })

   val unit: DataStream[(String, Double)] = recordStream.keyBy(_.id)
     .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
     .aggregate(new AggregateFunction[Record, MyAggrator, (String, Double)] {
       //累加逻辑，当然这里也可以是减法的逻辑
       override def add(value: Record, accumulator: MyAggrator): MyAggrator= {
         accumulator.sum = value.temp + accumulator.sum
         accumulator.lastId = Math.max(accumulator.lastId, value.id.toLong)
         //println("add:"+accumulator)
         accumulator
       }

       //初始化逻辑
       override def createAccumulator() = {
         new MyAggrator(0, 0)
       }

       //最后得到的结果
       override def getResult(accumulator: MyAggrator) =  {
         //println("get Result:"+accumulator)
         (accumulator.lastId.toString, accumulator.sum)
       }

       //两个累加器的合并
       override def merge(a: MyAggrator, b: MyAggrator) : MyAggrator = {
         a.lastId = Math.max(a.lastId, b.lastId)
         a.sum = a.sum + b.sum
         a
       }
     })
   unit.print()

   recordStream.keyBy(_.id).window(TumblingEventTimeWindows.of(Time.minutes(1))).

 }


}

object WindowStreamTest {
  def main(args: Array[String]): Unit = {
    val windowTest = new WindowStreamTest
    windowTest.testTumblingWindow()
    windowTest.streamEnv.execute()
  }
}
