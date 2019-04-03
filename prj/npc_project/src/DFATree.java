

public class DFATree {

  public DFA dfa;
  private HashMap<String, DFATree> stateMap;

  DFATree(DFA dfa) {
    this.dfa = dfa;
    this.stateMap = new HashMap<String, DFATree>;
  }

  public void addSubTree(String state, DFA newDFA) {
    if(dfa.hasState(state)) {
      stateMap.put(state, new DFATree(newDFA));
    }
  }

  public void process(String[] strs) {
    for(String s : strs) {
      processOne(s);
    }
  }

  public void processOne(String s) {
    if(dfa.verify()) return;
    if(stateMap.containsKey(dfa.state)) {
      DFATree currentSubTree = stateMap.get(dfa.state);
      if(currentSubTree.dfa.verify()) {
        dfa.processOne(s);
      } else {
        currentSubTree.processOne(s);
      }
    } else {
      dfa.processOne(s);
    }
  }

}