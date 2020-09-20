package com.yrl.scala

case class Hello (
                 name: String,
                 age: Integer
                 )
object Hello{

    def apply(
             hello: Hello2
           ) ={
     new Hello(hello.name+"23", 10)
  }

  def toKafkaString():String={
    ""
  }
}