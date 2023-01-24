import java.util.*;
import javax.sound.midi.*;    
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class midi {
    JFrame mframe;
    String[] spinst = {"Synth", "Bass1", "Drum", "Bells"};
    ArrayList<JCheckBox> chblist = new ArrayList<JCheckBox>();
    Track track;
    Sequencer player;
    Sequence seq;
    int bpm = 120;

    public static void main (String[] args){
        midi tt = new midi();
        tt.go();

    }

    public void go (){
        mframe = new JFrame("midi_beatbox");
        mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mframe.setSize(600,600);
        mframe.setVisible(true);
        

//pravo
        JPanel butpan = new JPanel();
        butpan.setBackground(Color.red);
        butpan.setLayout(new BoxLayout(butpan,BoxLayout.Y_AXIS));
        butpan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton bstart = new JButton("start");
        JButton bstop = new JButton("stop");
        JButton tubut = new JButton("tempup");
        JButton tdbut = new JButton("tempdown");
        butpan.add(bstart);
        bstart.addActionListener(new bstart());
        Font sett = new Font("arial", Font.BOLD, 18);
        bstart.setFont(sett);
        butpan.add(bstop);
        butpan.add(tubut);
        butpan.add(tdbut);
        bstop.setFont(sett);
        tubut.setFont(sett);
        tdbut.setFont(sett);
        bstop.addActionListener(new bstop());
        tubut.addActionListener(new tup());
        tdbut.addActionListener(new tdown());
        mframe.getContentPane().add(BorderLayout.EAST, butpan);
    
//levo
        JPanel namepan = new JPanel();
        namepan.setLayout(new BoxLayout(namepan, BoxLayout.Y_AXIS));
        namepan.setBackground(Color.green);
        mframe.getContentPane().add(BorderLayout.WEST, namepan);
        for (int i = 0; i<4; i++) {
            Label key = new Label();
            key.setFont(sett);
            key.setText(spinst[i]);
            namepan.add(key);
            
        }
        namepan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

//center
        JPanel chbpan = new JPanel(new GridLayout(4,16));
        chbpan.setBackground(Color.black);
        JCheckBox key2;
        for (int i =0; i<64;i++) {   // make list
           key2 = new JCheckBox();
           key2.setBackground(Color.blue);
           chblist.add(key2); 
        }
        for (Object i : chblist) {
            JCheckBox key3 = (JCheckBox) i ;
            chbpan.add(key3);
        } 
        mframe.getContentPane().add(BorderLayout.CENTER, chbpan);
        makeseq();
        mframe.pack();
            }
        
// inicializcia sintezatora
        public void makeseq() {
            try{
                player = MidiSystem.getSequencer();
                player.open();
                seq = new Sequence(Sequence.PPQ, 4);
                track = seq.createTrack();
                player.setTempoInBPM(bpm);
            } catch(Exception e) {};

        }

// make event dlia track
        public MidiEvent makeev(int d, int y, int z, int c, int tick) {
            MidiEvent event = null;
            try {
                ShortMessage x = new ShortMessage();
                x.setMessage(d,y,z,c);
                event = new MidiEvent(x, tick);
                } catch(Exception ex) {};
            return event;
        }

//build and start
        public void bands() {
            try{
            seq.deleteTrack(track);
            track = seq.createTrack();
            for (int i =0; i<4; i++) {
                for (int j = 0; j<16;j++ ) {
                    JCheckBox keey = (JCheckBox) chblist.get(16*i+j);
                    if (keey.isSelected()) {
                        if (i == 0) {
                            track.add(makeev(192,1,52,0,j+1));
                            track.add(makeev(144,1,80,70,j+1));
                            track.add(makeev(128,1,80,70,j+2));
                        } 
                        else if (i==1) {
                            track.add(makeev(192,2,39,0,j+1));
                            track.add(makeev(144,2,40,100,j+1));
                            track.add(makeev(128,2,40,100,j+4));
                        }
                        else if (i==2) {
                            track.add(makeev(192,3,116,0,j+1));
                            track.add(makeev(144,3,70,70,j+1));
                            track.add(makeev(128,3,70,70,j+4));
                        }
                        else if (i==3) {
                            track.add(makeev(192,2,14,0,j+1));
                            track.add(makeev(144,2,70,70,j+1));
                            track.add(makeev(128,2,70,70,j+2));
                        }
                    }
                    
                }
            }
            player.setSequence(seq);
            player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            player.start();
            } catch(Exception ex){}
        }

// class bstart
        public class bstart implements ActionListener {
            public void actionPerformed (ActionEvent a) {
                bands();
            }
        }

//class bstop
        public class bstop implements ActionListener {
            public void actionPerformed(ActionEvent a) {
            player.stop();
            }
        }

//class temp up
        public class tup implements ActionListener {
            public void actionPerformed(ActionEvent a) {
                float tf = player.getTempoFactor();
                player.setTempoFactor((float) (tf*1.03));
            }
        }

//class tempdown
        public class tdown implements ActionListener {
            public void actionPerformed(ActionEvent a) {
                float tf = player.getTempoFactor();
                player.setTempoFactor((float) (tf*0.97));
            }
        }
        }
