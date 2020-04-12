package week3

import chisel3._
import chisel3.iotesters.{TesterOptionsManager, Driver, PeekPokeTester}
import utils.TutorialRunner

object Week3Main {

    // used to test BinaryMealy start！
    // example from https://en.wikipedia.org/wiki/Mealy_machine
    def stateTransition(state: Int, in: Boolean): Int = if(in) 1 else 0

    def output(state: Int, in: Boolean): Int = {
        if (state == 2) {
            return 0
        }
        if ((state == 1 && !in) || (state == 0 && in)) {
            return 1
        } else {
            return 0
        }
    }
    
    val testParams = BinaryMealyParams(nStates = 3, s0 = 2, stateTransition, output)
    // used to test BinaryMealy end!
    
    val tests = Map(
        "sort4ascending" -> {
            (manager: TesterOptionsManager) => iotesters.Driver.execute(() => new Sort4(true), manager) {
                (c) => new Sort4AscendingTester(c)
            }
        },
        "sort4descending" -> {
            (manager: TesterOptionsManager) => iotesters.Driver.execute(() => new Sort4(false), manager) {
                (c) => new Sort4DescendingTester(c)
            }
        },
        "halfadder" -> {
            (manager: TesterOptionsManager) => iotesters.Driver.execute(() => new HalfFullAdder(false), manager) {
                (c) => new HalfAdderTester(c)
            }
        },
        "fulladder" -> {
            (manager: TesterOptionsManager) => iotesters.Driver.execute(() => new HalfFullAdder(true), manager) {
                (c) => new FullAdderTester(c)
            }
        },
        "binarymealy" -> {
            (manager: TesterOptionsManager) => iotesters.Driver.execute(() => new BinaryMealy(testParams), manager) {
                (c) => new BinaryMealyTester(c)
            }
        }
    )


    def main(args: Array[String]): Unit = {
        // TODO: args中的字母全部变小写 args.foreach{_ => _.toLowerCase}。 为什么不对呢
        TutorialRunner("Week3Main", tests, args)
    }
}