/**
 * file:       driverDFA.java
 * author:     Benjamin Jenkins
 * course:     CMPT 440 - 111
 * assignment: Homework 1
 * due date:   March 12, 2019
 * version:    1.0
 *
 * This file contains a driver for checking solutions to
 * the Man-Wolf-Goat-Cabbage problem using the ManWolf class.
 */

 public class driverDFA {

  public static void main(String[] args) {
    if(args.length > 1) {
      System.out.println("Too many arguments.");
      return;
    }
    else if(args.length < 1) {
      System.out.println("Too few arguments.");
      return;
    }
    boolean accepted = ManWolf.process(args[0]);
    if(accepted) System.out.println("This is a solution.");
    else System.out.println("This is not a solution.");
  }

}