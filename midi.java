import java.util.*;
import javax.sound.midi.*;    
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.* ;

public class midi {
    JFrame mframe;
    String[] spinst = {"Synth", "Bass1", "Drum", "Bells"};
    ArrayList<JCheckBox> chblist = new ArrayList<JCheckBox>();
    Track track;
    Sequencer player;
    Sequence seq;
    int bpm = 120;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;
    JTextField outtext;
    JTextArea inptex;
    

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
        JButton bstart = new JButton("Start");
        JButton bstop = new JButton("Stop");
        JButton tubut = new JButton("TempUp");
        JButton tdbut = new JButton("TempDown");
        JButton bsave = new JButton("Save");
        JButton bload = new JButton ("Load");
        JButton bclear = new JButton("Clear");
        JButton butoutp = new JButton("Send");
        JLabel space = new JLabel("     ");
        JLabel space1 = new JLabel("     ");
        JLabel space2 = new JLabel("     ");
        JLabel space3 = new JLabel("     ");
        inptex = new JTextArea(30,20);
        JScrollPane inptext = new JScrollPane(inptex);
        inptex.setLineWrap(true);
        inptex.setWrapStyleWord(true);
        inptex.setEditable(false);
        inptext.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inptext.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outtext = new JTextField(20);
        JLabel space4 = new JLabel("     ");
        butpan.add(bstart);
        bstart.addActionListener(new bstart());
        Font sett = new Font("arial", Font.BOLD, 18);
        bstart.setFont(sett);
        bstart.setForeground(Color.yellow);
        bstart.setBackground(Color.black);
        tubut.setMargin(new Insets(0,27,0,27));
        butpan.add(bstop);
        butpan.add(space1);
        butpan.add(tubut);
        butpan.add(tdbut);
        butpan.add(space);
        butpan.add(bsave);
        butpan.add(bload);
        butpan.add(space2);
        butpan.add(bclear);
        butpan.add(space3);
        butpan.add(butoutp);
        butpan.add(outtext);
        butpan.add(space4);
        butpan.add(inptext);
        bstop.setFont(sett);
        bstop.setForeground(Color.yellow);
        bstop.setBackground(Color.black);
        tubut.setFont(sett);
        tubut.setForeground(Color.yellow);
        tubut.setBackground(Color.black);
        tdbut.setFont(sett);
        tdbut.setForeground(Color.yellow);
        tdbut.setBackground(Color.black);
        bsave.setFont(sett);
        bsave.setForeground(Color.yellow);
        bsave.setBackground(Color.black);
        bload.setFont(sett);
        bload.setForeground(Color.yellow);
        bload.setBackground(Color.black);
        bclear.setFont(sett);
        bclear.setForeground(Color.yellow);
        bclear.setBackground(Color.black);
        butoutp.setFont(sett);
        butoutp.setForeground(Color.yellow);
        butoutp.setBackground(Color.black);
        bstop.addActionListener(new bstop());
        tubut.addActionListener(new tup());
        tdbut.addActionListener(new tdown());
        bsave.addActionListener(new bsave());
        bload.addActionListener(new bload());
        butoutp.addActionListener(new butout());
        bclear.addActionListener(new bclear());
        mframe.getContentPane().add(BorderLayout.EAST, butpan);
        
    
//levo
        JPanel namepan = new JPanel();
        namepan.setLayout(new BoxLayout(namepan, BoxLayout.Y_AXIS));
        namepan.setBackground(Color.green);
        mframe.getContentPane().add(BorderLayout.WEST, namepan);
        for (int i = 0; i<spinst.length; i++) {
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
        for (int i =0; i<(spinst.length*16);i++) {   // make list
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
        setupcon();
        Thread t = new Thread(new potok());
        t.start();
     }

// init connect
private void setupcon () {
    try{
    sock = new Socket("192.168.61.214",4999);
    InputStreamReader sreader = new InputStreamReader(sock.getInputStream());
    reader = new BufferedReader(sreader);
    writer = new PrintWriter(sock.getOutputStream());
    System.out.println("connection is ok");
    } catch (Exception ex) {ex.printStackTrace();}
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
            for (int i =0; i<spinst.length; i++) {
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

// class butoutp
        public class butout implements ActionListener {
            public void actionPerformed(ActionEvent a) {
                try {
                writer.println(outtext.getText());
                writer.flush();
                } catch(Exception ex) {ex.printStackTrace();}
                outtext.setText("");
            }
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

//class bsave
        public class bsave implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fsave = new JFileChooser();
                fsave.showSaveDialog(mframe);
                save(fsave.getSelectedFile());
            }

            private void save (File file) {
                try {
                    BufferedWriter savf = new BufferedWriter( new FileWriter(file));
                    int key = 0;
                    for (Object i :chblist) {
                        JCheckBox iter = (JCheckBox) i;
                        if (iter.isSelected()) {
                            savf.write(key+"/");
                            System.out.println(key);
                        }
                        key++;
                    }
                    savf.close();
                    }catch(Exception ex){
                        ex.printStackTrace();
                }
            }
        }

//class bload
            public class bload implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    try{
                    JFileChooser flod = new JFileChooser();
                    flod.showOpenDialog(mframe);
                    File setf = flod.getSelectedFile();
                    FileReader rlod = new FileReader(setf);
                    BufferedReader reader = new BufferedReader(rlod);
                    String line ="";
                    line = reader.readLine();
                    System.out.println(line);
                    reader.close();
                    String [] arload = line.split("/");
                    ArrayList<Integer> iarload = new ArrayList<Integer>();
                    for (String i : arload) {
                        int keyint = Integer.parseInt(i);
                        iarload.add(keyint);
                    }
                    for (int i : iarload) {
                        System.out.print(i);
                        JCheckBox keyb = (JCheckBox) chblist.get(i);
                        keyb.setSelected(true);
                    }                   
                    } catch(Exception ex) {}                    
                } 
                
            }

//class bclear
            public class bclear implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    for (Object i : chblist) {
                        JCheckBox key1 = (JCheckBox) i;
                        key1.setSelected(false);
                    }
                }
            }

//class dlia potoka
            public class potok implements Runnable {
                public void run() {
                String message;
                try {
                    while (true){ 
                        if ((message = reader.readLine()) != null) {
                        System.out.println("read:" + message);
                        inptex.append(message + "\n");
                    }
                } 
                }catch(Exception ex) {}
            }
            }
}        
