package com.yrl.scala

object Test {

  def main(args: Array[String]): Unit = {
    println(Hello(new Hello2("yang", 12)).productIterator.map(data => {
      print("data:" + data)
      data
    }))
    println(Hello("name", 12).productIterator.map(data => {

      println("2")
      println(data)
      data
    }

    ).mkString(","))
    println(List("1", "2", "3").productIterator.map(data => {
      print("data:"+data )
      data.toString
    }).mkString(","))
  }
}
