import java.util.HashMap;
import java.util.Set;

public class World {

  private int xMax;
  private int yMax;
  private HashMap<Character, CharacterPosition> characterMap;

  World(xMax, yMax) {
    this.xMax = xMax;
    this.yMax = yMax;
  }

  public void addCharacter(Character c, int posX, int posY) {
    CharacterPosition pos = new CharacterPosition(posX, posY);
    characters.put(c, pos);
  }

  public void dispatchEvent(Event e) {
    Set<Character> characters = characterMap.keySet();
    for(Character c : characters) {
      if(c instanceof NPC) c.processEvent(e);
    }
  }

  public void dispatchAction(Action a) {
    Character actionTarget = a.target;
    if(characterMap.containsKey(actionTarget)) {
      actionTarget.processAction(a);
    } 
  }

}

class CharacterPosition {
  public int posX;
  public int posY;
  CharacterPosition(int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
  }
}