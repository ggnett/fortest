import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test1 implements ControllerEventListener,ActionListener {
    JFrame frame;
    int xr = (int) (Math.random()*100);
    int yr = (int) (Math.random()*100);
    int ir = (int) (Math.random()*100);
    int zr = (int) (Math.random()*100);
    public static void main(String[] args) {
        test1 mini = new test1();
        mini.chek();
        mini.play();
    }

    public void play() {
        try {
        Sequencer player = MidiSystem.getSequencer();
        player.open();
        Sequence seq = new Sequence(Sequence.PPQ, 4);
        Track track = seq.createTrack();
        int[] eventsIWant = {127};
        player.addControllerEventListener(this,eventsIWant);

        for (int i = 5; i < 60; i+=4) {
            track.add(makeev(144,1,i,100,i));
            track.add(makeev(176,1,127,0,i));
            track.add(makeev(128,1,i,100,i+1));
        }
        player.setSequence(seq);
        player.start();
    } catch (Exception ex){
        ex.printStackTrace();
    }
}

public MidiEvent makeev(int a, int b, int c, int d, int tick) {
    MidiEvent event = null;
    try{
    ShortMessage x = new ShortMessage();
    x.setMessage(a,b,c,d);
    event = new MidiEvent(x, tick);
    } catch(Exception exx){}
    return event;
}

public void controlChange(ShortMessage event) {
    frame.repaint();
    xr = (int) (Math.random()*200);
    yr = (int) (Math.random()*200);
    ir = (int) (Math.random()*200);
    zr = (int) (Math.random()*200);
}

class MeDrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(xr,yr,ir,zr);
    }
}

public void chek(){
    frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MeDrawPanel gt = new MeDrawPanel();
        frame.getContentPane().add(gt);
        frame.setSize(300,300);
        frame.setVisible(true);
}
}


