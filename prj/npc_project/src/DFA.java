import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;

public class DFA {

  public String state;
  private String initialState;
  private HashSet<String> finalStates;
  private HashMap<String, HashMap<String, String>> states;
  private HashSet<String> inputs;

  DFA(String initial, boolean isFinal) {
    state = initial;
    initialState = initial;
    states = new HashMap<String, HashMap<String, String>>();
    finalStates = new HashSet<String>();
    inputs = new HashSet<String>();
    states.put(initial, new HashMap<String, String>());
    if(isFinal) finalStates.add(initial);
  }

  public Boolean hasState(String s) {
    if(this.states.containsKey(s)) return true;
    return false;
  }

  public void addState(String state, boolean isFinal) {
    if(state.equals("error")) {
      throw new java.lang.RuntimeException("'error' is reserved for the error state");
    }
    if(states.get(state) == null) {
      states.put(state, new HashMap<String, String>());
    }
    if(isFinal) {
      finalStates.add(state);
    }
  }

  public void addTransition(String srcState, String input, String destState) {
    // ensure the src and dest states exist
    if(!states.containsKey(srcState) || !states.containsKey(destState)) {
      throw new java.lang.RuntimeException("source or destination state does not exist in this DFA");
    }
    inputs.add(input);
    states.get(srcState).put(input, destState);
  }

  public void reset() {
    state = initialState;
  }

  public void process(String[] strs) {
    for(String s : strs) {
      processOne(s);
    }
  }

  public void processOne(String s) {
    String newState = states.get(state).get(s);
    if(newState != null) state = newState;
  }

  public boolean verify() {
    return finalStates.contains(state);
  }

  public boolean run(String[] s) {
    this.reset();
    this.process(s);
    return this.verify();
  }

  public String transitionTable(int cellWidth) {
    String table = padToLength("", cellWidth);
    ArrayList inputsList = new ArrayList<String>();
    Iterator<String> it = inputs.iterator();
    while(it.hasNext()) {
      String s = it.next();
      inputsList.add(s);
      table += padToLength(s, cellWidth);
    }
    table += "\n";
    Set<String> keys = states.keySet();
    for(String key: keys) {
      table += padToLength(key, cellWidth);
      HashMap<String, String> stateMap = states.get(key);
      for(int i = 0; i < inputsList.size(); i++) {
        if(stateMap.containsKey(inputsList.get(i))){ 
          table += padToLength(stateMap.get(inputsList.get(i)), cellWidth);
        } else {
          table += padToLength("", cellWidth);
        }
      }
      table += "\n";
    }
    return table;
  }

  private String padToLength(String s, int length) {
    while(s.length() < length) s = " " + s;
    return s;
  }

}