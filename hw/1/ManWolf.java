/**
 * ManWolf.java
 * 
 * DFA class for the Man-Wolf-Goat-Cabbage problem
 * 
 * Benjamin Jenkins
 * 3/6/2019
 */

public class ManWolf {

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
   * Process a string and check if it is accepted
   * for the Man-Wolf-Goat-Cabbage problem
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
    if(state == 9) return true;
    return false;

  }

  /**
   * Translates a string, {w,g,c,n}*, to an array of integers
   * corresponding to columns in the delta transition matrix
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