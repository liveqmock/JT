package ui.frames;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SplashDemo extends Frame implements ActionListener {
    static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading "+comps[(frame/5)%3]+"...", 120, 150);
    }
    public SplashDemo() {
        super("SplashScreen demo");
        setSize(300, 200);
        setLayout(new BorderLayout());
        Menu m1 = new Menu("File");
        MenuItem mi1 = new MenuItem("Exit");
        m1.add(mi1);
        mi1.addActionListener(this);
        this.addWindowListener(closeWindow);

        MenuBar mb = new MenuBar();
        setMenuBar(mb);
        mb.add(m1);
        final SplashScreen splash = SplashScreen.getSplashScreen();
        
        setVisible(true);
        toFront();
    }
    @Override
	public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }
    
    private static WindowListener closeWindow = new WindowAdapter(){
        @Override
		public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
        }
    };
    
    public static void main (String args[]) {
        SplashDemo test = new SplashDemo();
    }
}