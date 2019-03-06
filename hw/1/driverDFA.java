/**
 * driverDFA.java
 * 
 * Driver for Man-Wolf-Goat-Cabbage problem
 *
 * Benjamin Jenkins
 * 3/6/2019
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