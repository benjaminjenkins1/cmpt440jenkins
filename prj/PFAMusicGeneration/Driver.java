
/**
 * Music generator using a probabilistic finite automata
 */

import java.io.File;
import java.util.Scanner;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.MetaMessage;

public class Driver {

  public static final int NOTE_ON = 0x90;
  public static final int NOTE_OFF = 0x80;
  public static final int SET_TEMPO = 0x51;
  public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);
    System.out.print("File name: ");
    String filename = sc.next();
    System.out.print("Track number: ");
    int trackNumber = sc.nextInt();
    System.out.print("Outfile: ");
    String outFile = sc.next();
    sc.close();

    MusicPFA generator = new MusicPFA(new File(filename), trackNumber);
    StateSequence seq = generator.generateStateSequence(200);
    MusicPFA.createMIDI(seq, outFile);

  }
}