public class DFA_test {
  
  public static void main(String[] args) {
    
    boolean pass = true;
    
    DFA dfa = new DFA("initial", false);

    dfa.addState("one",false);
    dfa.addState("two", true);

    dfa.addTransition("initial", "initialToOne", "one");
    dfa.addTransition("one", "oneToTwo", "two");
    dfa.addTransition("two", "twoToInitial", "initial");

    dfa.process(new String[]{"initialToOne", "oneToTwo"});
    checkString(dfa.state, "two");

    dfa.process(new String[]{"twoToInitial"});
    checkString(dfa.state, "initial");

    checkBool(dfa.run(new String[]{"initialToOne", "oneToTwo"}), true);

    System.out.println("Transition table:");
    System.out.println(dfa.transitionTable(15));

  }

  public static void checkString(String expected, String result) {
    if(expected.equals(result)) {
      System.out.printf("OK. Expected '%s', got '%s'\n", expected, result);
    } else {
      System.out.printf("ERROR. Expected '%s', got '%s'\n", expected, result);
    }
  }

  public static void checkBool(boolean expected, boolean result) {
    if(expected == result) {
      System.out.printf("OK. Expected %b, got %b\n", expected, result);
    } else {
      System.out.printf("ERROR. Expected %b, got %b\n", expected, result);
    }
  }
  
}