/*
 * LevelEditor.java
 *
 * Created on 26. marraskuuta 2006, 15:44
 *
 * Kentti‰ tehd‰‰n osoittamalla hiiren kursorilla paikka johon palikka halutaan ja painetaan hiirenn‰pp‰int‰.
 * Eri palikkatyyppej‰ on 5 joita voi vaihtaa numeron‰pp‰imist‰ 1-5. Palikoita voi poistaa klikkaamalla niit‰.
 * Kent‰n voi tyhjent‰‰ N-n‰pp‰imell‰, ladata voi L-n‰pp‰imell‰ ja tallentaa voi S-n‰pp‰mell‰.
 * Kenttien nimien tulee olla muodossa "kentt‰"+nro jotta peli ymm‰rt‰isi avata niit‰.
 */

package parkanoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ListIterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Kentt‰editori Parkanoid-nimiseen peliin
 * @author Toni Paloniemi
 */
public class LevelEditor extends JFrame{
    PiirtoPaneeli kenttapaneeli;
    JPanel kyselypaneeli;
    Image[] palikkakuvat;
    MediaTracker mt;
    Palikkalista palikat;
    int palikkatyyppi;
    /**
     * LevelEditorin konstruktori. Lataa resurssit ja odottaa ett‰ kaikki on ladattu
     */
    public LevelEditor() {
        
        mt = new MediaTracker(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(640,480);
        kenttapaneeli = new PiirtoPaneeli();
        kyselypaneeli = new JPanel();
        getContentPane().add(kenttapaneeli);
        kenttapaneeli.addMouseListener(new HiiriNappiKuuntelija());
        addKeyListener(new NappaimistoKuuntelija());
        palikat = new Palikkalista();
        palikkakuvat = new Image[5];
        //Ladataan resurssit
        palikkakuvat[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/palikka1.png"));
        palikkakuvat[1] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/palikka2.png"));
        palikkakuvat[2] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/palikka3.png"));
        palikkakuvat[3] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/palikka4.png"));
        palikkakuvat[4] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/palikka5.png"));
        palikkatyyppi = 0;
        try {
            // lis‰t‰‰n kuvat mt olioon
            for(int i = 0; i < 5; i++)
                mt.addImage(palikkakuvat[i],i);
            
            // odotellaan ett‰ kaikki kuvat on ladattu
            // voi aiheuttaa InterruptedException poikkeuksen
            mt.waitForAll();
        } catch (InterruptedException poikkeus) {}

    }
    public static void main(String args[]){
       new LevelEditor().setVisible(true);
    }
    /**
     * Paneeli johon piirret‰‰n kentt‰ jota LevelEditorilla rakennetaan
     */
    public class PiirtoPaneeli extends JPanel{
        public void paintComponent(Graphics g){
            g.setColor(Color.WHITE);
            g.fillRect(0,0,800,600);
            g.setColor(Color.black);
            g.drawString(palikat.palikkamaara()+"",10,10);
            //piirretaan palikat
            ListIterator<Palikka> i = palikat.palautaPalikat().listIterator();
            while(i.hasNext()){
                Palikka palikka = i.next();
                palikka.piirra(g, palikkakuvat[palikka.palautaTyyppi()]);
            }
            
        }
    }
    /**
     * Luokka jonka avulla kuunnellaan hiiren painalluksia
     */
    class HiiriNappiKuuntelija extends MouseAdapter{
        public void mousePressed(MouseEvent me){
            //asetetaan palikka gridiin
            int x = me.getX()/Palikka.PALIKKALEVEYS*Palikka.PALIKKALEVEYS;
            int y = me.getY()/Palikka.PALIKKAKORKEUS*Palikka.PALIKKAKORKEUS;
            palikat.lisaaPalikka2(new Palikka(x,y,palikkatyyppi));
            kenttapaneeli.repaint();
        }
    }
    /**
     *Luokka jonka avulla kuunnellaan n‰pp‰imistˆnpainalluksia
     */
    class NappaimistoKuuntelija extends KeyAdapter{
        public void keyPressed(KeyEvent ke){
            //Tallennetaan kentt‰ S-n‰pp‰imell‰
            if(ke.getKeyCode() == KeyEvent.VK_S){
                File tiedosto;
                JFileChooser dialogi = new JFileChooser();
                dialogi.showSaveDialog(getContentPane());
                tiedosto = dialogi.getSelectedFile();
                try{
                    FileOutputStream fileOut = new FileOutputStream(tiedosto.getPath());
                    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                    objOut.writeObject(palikat);
                    objOut.close();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            //Avataan k‰ytt‰j‰n valitsema kentt‰ L-n‰pp‰imell‰
            if(ke.getKeyCode() == KeyEvent.VK_L){
                File tiedosto;
                JFileChooser dialogi = new JFileChooser();
                dialogi.setMultiSelectionEnabled(false);
                dialogi.showOpenDialog(getContentPane());
                tiedosto = dialogi.getSelectedFile();
                try{
                    FileInputStream fileIn = new FileInputStream(tiedosto.getPath());
                    ObjectInputStream objIn = new ObjectInputStream(fileIn);
                    System.out.println("luetaan objekti");
                    palikat = (Palikkalista)objIn.readObject();
                    objIn.close();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            //vaihdetaaan palikkatyyppi‰ jos k‰ytt‰j‰ painoi numeron‰pp‰imi‰
            if(ke.getKeyCode() == KeyEvent.VK_1){
                palikkatyyppi = 0;
            }
            if(ke.getKeyCode() == KeyEvent.VK_2){
                palikkatyyppi = 1;
            }
            if(ke.getKeyCode() == KeyEvent.VK_3){
                palikkatyyppi = 2;
            }
            if(ke.getKeyCode() == KeyEvent.VK_4){
                palikkatyyppi = 3;
            }
            if(ke.getKeyCode() == KeyEvent.VK_5){
                palikkatyyppi = 4;
            }
            //luodaan uusi kentt‰ N-n‰pp‰imell‰
            if(ke.getKeyCode() == KeyEvent.VK_N){
                palikat = new Palikkalista();
                repaint();
                }
        }
    }
}
