import java.io.File;
import java.util.Scanner;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.MetaMessage;

public class MidiSequence {

  public static final int NOTE_ON = 0x90;
  public static final int NOTE_OFF = 0x80;
  public static final int SET_TEMPO = 0x51;
  public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);
    System.out.print("File name:");
    String filename = sc.next();
    sc.close();

    int trackNumber = 0;
    
    Sequence sequence = MidiSystem.getSequence(new File(filename));

    for (Track track : sequence.getTracks()) {
      trackNumber++;
      System.out.println("Track " + trackNumber + ": size = " + track.size());
      System.out.println();
      for (int i = 0; i < track.size(); i++) {
        MidiEvent event = track.get(i);
        System.out.print("@" + event.getTick() + " ");
        MidiMessage message = event.getMessage();
        if (message instanceof ShortMessage) {
          ShortMessage sm = (ShortMessage) message;
          System.out.println("Channel: " + sm.getChannel() + " ");
          if (sm.getCommand() == NOTE_ON) {
            int key = sm.getData1();
            int octave = (key / 12) - 1;
            int note = key % 12;
            String noteName = NOTE_NAMES[note];
            int velocity = sm.getData2();
            System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
          } else if (sm.getCommand() == NOTE_OFF) {
            int key = sm.getData1();
            int octave = (key / 12) - 1;
            int note = key % 12;
            String noteName = NOTE_NAMES[note];
            int velocity = sm.getData2();
            System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
          } else {
            System.out.println("Command:" + sm.getCommand());
          }
        } else {
          if(message instanceof MetaMessage) {
            MetaMessage metaMessage = (MetaMessage) message;
            if(metaMessage.getType() == SET_TEMPO) {
              byte[] data = metaMessage.getData();
              int tempo = (data[0] & 0xff) << 16 | (data[1] & 0xff) << 8 | (data[2] & 0xff);
              int bpm = 60000000 / tempo;
              System.out.println("TEMPO " + bpm);
            }
          }
          else {
            System.out.println("Other message: " + message.getClass());
          }
        }
      }

      System.out.println();
    }
    
  }
  
}