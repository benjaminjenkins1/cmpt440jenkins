/**
 * Probabilistic finite automata for music generation
 * 
 * Reads a  midi file and generates a PFA based on the sequence
 * of notes/chords in one track on one channel
 */

import java.io.File;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import java.lang.Integer;
import java.lang.Long;
import java.lang.StringBuilder;

import java.util.Comparator;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MusicPFA {

  public static final int NOTE_ON = 0x90;
  public static final int NOTE_OFF = 0x80;
  private static final int SET_TEMPO = 0x51;
  private HashMap<Long, State> tickMap;
  private HashMap<State, ArrayList<State>> transitionProbabilityMap;

  /**
   * Constructs a PFA from one track and one channel of a midi file
   */
  public MusicPFA(File file, int trackNum, int channelNum) throws Exception {

    this.tickMap = new HashMap<Long, State>();
    Sequence seq = MidiSystem.getSequence(file);

    int tempo = readTempo(seq);

    System.out.println("Meta:");
    System.out.println("  Resolution: " + seq.getResolution());
    System.out.println("  Tempo: " + tempo);


    Track[] tracks = seq.getTracks();
    Track track = tracks[trackNum];
    int trackSize = track.size();


    // Construct the tickMap
    // Create a state at each tick and add all notes at that tick to the state
    for(int i = 0; i < trackSize; i++) {
      MidiEvent event = track.get(i);
      long tick = event.getTick();
      MidiMessage message = event.getMessage();
      if(message instanceof ShortMessage) {
        ShortMessage sm = (ShortMessage) message;
        if(sm.getCommand() == NOTE_ON) {
          int key = sm.getData1();
          //int velocity = sm.getData2();
          //long duration = readDuration(track, i);
          Note note = new Note(key);
          // check the tickMap for a state at this tick
          if(this.tickMap.containsKey(new Long(tick))) { // add this note to the statae
            this.tickMap.get(new Long(tick)).addNote(note);
          }
          else { // add a new State to the tickMap with this single note
            ArrayList<Note> ns = new ArrayList<Note>();
            ns.add(note);
            this.tickMap.put(new Long(tick), new State(ns));
          }
        }
      }
    }

    // Print the tickMap
    System.out.println("Tick Map:");
    Set<Long> tickSet = this.tickMap.keySet();
    System.out.println("  Size:" + tickSet.size());
    Long[] tickArr = tickSet.toArray(new Long[tickSet.size()]);
    Arrays.sort(tickArr);
    for(Long l : tickArr) {
      System.out.println("  State at tick " + l.toString() + ":");
      State s = this.tickMap.get(l);
      System.out.print(s.toString());
    }

    // Build the transitionProbabilityMap using the tickArr and tickMap
    this.transitionProbabilityMap = new HashMap<State, ArrayList<State>>();
    for(int i = 0; i < tickArr.length; i++) {
      State thisState = this.tickMap.get(tickArr[i]);
      if(this.transitionProbabilityMap.containsKey(thisState) && i + 1 < tickArr.length) {
        ArrayList<State> transitionStates = this.transitionProbabilityMap.get(this.tickMap.get(tickArr[i]));
        transitionStates.add(this.tickMap.get(tickArr[i+1]));
      }
      else {
        this.transitionProbabilityMap.put(thisState, new ArrayList<State>());
      }
    }

    // Print the transitionProbabilityMap
    System.out.println("Transition Probability Map:");
    System.out.println("  Size:" + this.transitionProbabilityMap.size());
    Set<State> stateSet = this.transitionProbabilityMap.keySet();
    for(State s : stateSet) {
      System.out.println("State:");
      System.out.println("strVal:" + s.strVal());
      System.out.println("hashCode:" + s.hashCode());
      System.out.println("%");
      System.out.println(s.toString());
      for(State ts : this.transitionProbabilityMap.get(s)) {
        System.out.println("&");
        System.out.println(ts.toString());
        System.out.println("&");
      }
      System.out.println("%");
    }



  }

  /**
   * Scan a sequence for the first SET_TEMPO message
   * Returns beats per minute
   */
  private int readTempo(Sequence sequence) {
    for(Track track : sequence.getTracks()) {
      for(int i = 0; i < track.size(); i++) {
        MidiEvent event = track.get(i);
        MidiMessage message = event.getMessage();
        if(message instanceof MetaMessage) {
          MetaMessage metaMessage = (MetaMessage) message;
          if(metaMessage.getType() == SET_TEMPO) {
            byte[] data = metaMessage.getData();
            int tempo = (data[0] & 0xff) << 16 | (data[1] & 0xff) << 8 | (data[2] & 0xff);
            int bpm = 60000000 / tempo;
            return bpm;
          }
        }
      }
    }
    // only reached if there was no SET_TEMPO message in the sequence
    return 120;
  }

  /**
   * Get the duration of a note, in midi ticks
   * 
   * Parameters:
   *   Track track - Track containing the event
   *   int i       - Index of the event in the track
   * Returns:
   *   long        - Duration of the note in midi ticks
   */
  private long readDuration(Track track, int i) {
    MidiEvent event = track.get(i);
    long startTick = event.getTick();
    long endTick = startTick + 1;
    ShortMessage sm = (ShortMessage) event.getMessage();
    int key = sm.getData1();
    // scan the rest of the track for the next NOTE_OFF message for this key
    for(int j = i; j < track.size(); j++) {
      event = track.get(j);
      MidiMessage msg = event.getMessage();
      if(msg instanceof ShortMessage) {
        sm = (ShortMessage) msg;
        if(sm.getCommand() == NOTE_OFF) {
          int k = sm.getData1();
          if(k == key) { // found the first NOTE_OFF for this key after i
            endTick = event.getTick();
          }
        }
      }
    }
    return endTick - startTick;
  }

}

/**
 * The note class
 * Contains key information for a note
 */
class Note {

  public int key;
  //public int velocity;
  //public long duration;

  public Note(int key) {
    this.key = key;
    //this.velocity = velocity;
    //this.duration = duration;
  }

  public Integer getKey() {
    return new Integer(this.key);
  }
  /*
  public Integer getVelocity() {
    return new Integer(this.velocity);
  }
  public Long getDuration() {
    return new Long(this.duration);
  }
  */

}

/**
 * Comparator for sorting notes
 * 
 * Sort by key, then velocity, then duration
 */
class NoteComparator implements Comparator<Note> {
  public int compare(Note a, Note b) {
    return a.getKey().compareTo(b.getKey());
    /*
    if(keyResult != 0) return keyResult;
    int velocityResult = a.getVelocity().compareTo(b.getVelocity());
    if(velocityResult != 0) return velocityResult;
    return a.getDuration().compareTo(b.getDuration());
    */
  }
}

/**
 * The State class
 * Contains a sorted list of notes that together represent a state
 */
class State {
  
  // Sorted list of notes
  ArrayList<Note> notes;

  public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

  public State(ArrayList<Note> ns) {
    Collections.sort(ns, new NoteComparator());
    this.notes = ns;
  }

  public boolean equals(State s) {
    if(this.notes.equals(s.notes)) return true;
    return false;
  }

  public void addNote(Note n) {
    this.notes.add(n);
    Collections.sort(this.notes, new NoteComparator());
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(Note n : this.notes) {
      sb.append("    Note(key:");
      sb.append(n.key);
      sb.append(" note:");
      sb.append(NOTE_NAMES[n.key % 12]);
      sb.append(")\n");
    }
    return sb.toString();
  }

  public String strVal() {
    StringBuilder sb = new StringBuilder();
    for(Note n : notes) {
      sb.append(n.getKey().toString());
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if(o instanceof State) {
      State otherState = (State) o;
      String thisStrVal = this.strVal();
      String otherStrVal = otherState.strVal();
      if(thisStrVal.equals(otherStrVal)) return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    String str = this.strVal();
    return str.hashCode();
  }

}