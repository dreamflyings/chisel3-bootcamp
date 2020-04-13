package week3

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object QueueTester {
    def apply(){
        Driver(() => new Module {
            // Example circuit using a Queue
            val io = IO(new Bundle {
            val in = Flipped(Decoupled(UInt(8.W)))
            val out = Decoupled(UInt(8.W))
            })
            val queue = Queue(io.in, 2)  // 2-element queue
            io.out <> queue
            }) { c => new PeekPokeTester(c) {
                // Example testsequence showing the use and behavior of Queue
                poke(c.io.out.ready, 0)
                poke(c.io.in.valid, 1)  // Enqueue an element
                poke(c.io.in.bits, 42)
                println(s"Starting:")
                println(s"\tio.in: ready=${peek(c.io.in.ready)}")
                println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
                step(1)
            
                poke(c.io.in.valid, 1)  // Enqueue another element
                poke(c.io.in.bits, 43)
                // What do you think io.out.valid and io.out.bits will be?
                println(s"After first enqueue:")
                println(s"\tio.in: ready=${peek(c.io.in.ready)}")
                println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
                step(1)
            
                poke(c.io.in.valid, 1)  // Read a element, attempt to enqueue
                poke(c.io.in.bits, 44)
                poke(c.io.out.ready, 1)
                // What do you think io.in.ready will be, and will this enqueue succeed, and what will be read?
                println(s"On first read:")
                println(s"\tio.in: ready=${peek(c.io.in.ready)}")
                println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
                step(1)
            
                poke(c.io.in.valid, 0)  // Read elements out
                poke(c.io.out.ready, 1)
                // What do you think will be read here?
                println(s"On second read:")
                println(s"\tio.in: ready=${peek(c.io.in.ready)}")
                println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
                step(1)
            
                // Will a third read produce anything?
                println(s"On third read:")
                println(s"\tio.in: ready=${peek(c.io.in.ready)}")
                println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
                step(1)
            } 
        }
    }
}