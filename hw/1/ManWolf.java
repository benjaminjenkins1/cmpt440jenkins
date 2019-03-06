/**
 * file:       ManWolf.java
 * author:     Benjamin Jenkins
 * course:     CMPT 440 - 111
 * assignment: Homework 1
 * due date:   March 12, 2019
 * version:    1.0
 *
 * This file contains the declaration of the ManWolf class
 */


/**
 * ManWolf
 *  
 * This class provides a method for checking whether a string
 * is accepted for the Man-Wolf-Goat-Cabbage problem.
 */
public class ManWolf {

  private static final int ACCEPTING_STATE = 9;

  /**
   * The delta transition matrix
   * Rows:    states 0-10
   * Columns: wolf | goat | cabbage | nothing
   */
  private static final int[][] delta = {
    {10,  1, 10, 10},
    {10,  0, 10,  2},
    { 3, 10,  4,  1},
    { 2,  5, 10, 10},
    {10,  6,  2, 10},
    {10,  3,  7, 10},
    { 7,  4, 10, 10},
    { 6, 10,  5,  8},
    {10,  9, 10,  7},
    {10,  8, 10, 10},
    {10, 10, 10, 10}
  };

  /**
   * process
   *
   * Process a string and check if it is an accepted
   * solution to the Man-Wolf-Goat-Cabbage problem.
   * 
   * Parameters:
   *   s       - the string to process
   * Returns:
   *   boolean - whether the string is accepted
   */
  public static boolean process(String s) {

    // initial state
    int state = 0;

    // transition on each character
    for(int i : translate(s)) {
      try {
        state = delta[state][i];
      }
      catch(ArrayIndexOutOfBoundsException e) {
        return false;
      }
    }

    // check if state is an accepting state (9)
    if(state == ACCEPTING_STATE) return true;
    return false;

  }

  /**
   * translate
   *
   * Translates a string, {w,g,c,n}*, to an array of integers
   * corresponding to columns in the delta transition matrix
   * 
   * Parameters:
   *   s     - string to translate
   * Returns:
   *   int[] - array of column indices
   */
  private static int[] translate(String s) {
    int len = s.length();
    int[] translated = new int[len];
    for(int i = 0; i < len; i++) {
      switch(s.charAt(i)) {
        case 'w':
          translated[i] = 0;
          break;
        case 'g':
          translated[i] = 1;
          break;
        case 'c':
          translated[i] = 2;
          break;
        case 'n':
          translated[i] = 3;
          break;
        default:
          translated[i] = -1;
          break;
      }
    }
    return translated;
  }

}