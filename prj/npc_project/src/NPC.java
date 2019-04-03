

public class NPC extends Character {

  public DFATree dfaTree;
  public int hitPoints;

  NPC(int hitPoints) {
    this.hitPoints = hitPoints;
    this.dfaTree   = new DFATree(new DFA("idle", false));
  }

  NPC() {
    this.hitPoints = super.hitPoints;
    this.dfaTree   = new DFATree(new DFA("idle", false));
  }

  public void processEvent(Event e) {
    
  }

}