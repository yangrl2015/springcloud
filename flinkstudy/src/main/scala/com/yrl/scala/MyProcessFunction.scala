package com.yrl.scala

import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

 class MyProcessFunction extends ProcessWindowFunction[SensorRecord, List[SensorRecord], String, TimeWindow]{

  override def process(key: String, context: Context, elements: Iterable[SensorRecord], out: Collector[List[SensorRecord]]): Unit = {

    println("elements:"+elements)
    out.collect(elements.toList)


   }

}
