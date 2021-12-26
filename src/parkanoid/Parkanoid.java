/*
 * Parkanoid.java
 *
 * Aloitettu 19. lokakuuta 2006, 12:55
 *
 * Tekij‰t:
 *  Toni Paloniemi
 *  Severi Vidn‰s
 *
 * Pelin nimi:
 *  Parkanoid
 * 
 * Ohjeet:
 * Peli‰ pelataan liikuttamalla hiirt‰. Hiiren n‰pp‰imell‰ voi vapauttaa pallon mailasta jolloin se alkaa pomppimaan
 * ruudulla. Jos pallo karkaa ruudulta, menet‰t el‰m‰n. Jos el‰m‰si loppuu, joudut aloittamaan pelin alusta.
 * Pallo tuhoaa osuessaan palikoita joista saat pisteit‰. Kun palikat ovat tuhoutuneet, p‰‰set seuraavaan kentt‰‰n.
 * Palikoiden tuhoutuessa ne saattavat tiputtaa poweruppeja. Ker‰‰ poweruppeja mailalla saadaksesi erikoisvaikutuksia
 * peliin. Jotkin erikoisvaikutukset vaikeuttavat pelaamista. Ampumiserikoisvaikutus toimii hiiren n‰pp‰imell‰.
 */

package parkanoid;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/** 
 * Parkanoid-nimisen pelin p‰‰luokka
 * @author Toni Paloniemi, Severi Vidn‰s
 */
public class Parkanoid extends JApplet implements Runnable {
    Thread saie = null;
    Peli peli;
    Image pallokuva;
    Image mailakuva;
    Image ammuskuva;
    Image mailavlaita;
    Image mailaolaita;
    Image[] palikkakuvat;
    Image[] powerupkuvat;
    long paivitysaika;
    long edellinenkerta=0;
    Pallo o;
    JPanel paneeli;
    BorderLayout layout;
    JToggleButton pausenappi;
    JButton uusiPeliNappi;
    JLabel pisteet;
    JLabel elamat;
    /**
     * Pelin alustus. Lataa kuvat ja rakentaa k‰yttˆliittym‰n
     */
    public void init(){
        
        pausenappi = new JToggleButton();
        uusiPeliNappi = new JButton();
        pisteet = new JLabel("Pisteet: ");
        elamat = new JLabel("El‰m‰t: ");
        uusiPeliNappi.setText("Uusi peli");
        pausenappi.setText("Pause");
        //asetetaan pausenapin toiminnot
        pausenappi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               switch(peli.palautaPelitila()){
                   //pelitila m‰‰r‰‰ onko peli pausella vai ei
                   case 2:  peli.asetaPelitila(4);break;
                   case 4:  peli.asetaPelitila(2);break;
               }
            }
        });
        //asetetaan uusipeli-napin toiminta
        uusiPeliNappi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               peli.alustaPeli();
            }
        });
        //paneelit joilla saadaan nappulat ja labelit paikoilleen
        JPanel oikeareuna = new JPanel();
        JPanel vasenreuna = new JPanel();
        oikeareuna.setLayout(new BoxLayout(oikeareuna, BoxLayout.Y_AXIS));
        vasenreuna.setLayout(new BoxLayout(vasenreuna, BoxLayout.Y_AXIS));
        //luodaan paneeli johon asetetaan kaikki nappulat ja labelit
        paneeli = new JPanel();
        paneeli.setLayout(new BorderLayout());
        paneeli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        oikeareuna.add(pausenappi);
        oikeareuna.add(uusiPeliNappi);
        vasenreuna.add(pisteet);
        vasenreuna.add(elamat);
        paneeli.add(oikeareuna, BorderLayout.EAST);
        paneeli.add(vasenreuna, BorderLayout.WEST);
        //itse appletin asettelija
        layout = new BorderLayout();
        Container temp = getContentPane();
        paneeli.setPreferredSize(new Dimension(getWidth(), 60));
        temp.setLayout(layout);
        //paneeli johon piirret‰‰n pelikuva
        PiirtoPaneeli piirtoPaneeli = new PiirtoPaneeli();
        piirtoPaneeli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        temp.add(piirtoPaneeli, BorderLayout.CENTER);
        getContentPane().add(paneeli, BorderLayout.NORTH);
        //k‰ytt‰j‰n syˆtteit‰ varten lis‰t‰‰n kuuntelijat
        addMouseMotionListener(new HiirenLiikeKuuntelija());
        addMouseListener(new kuunteleHiirinappi());
        //ladataan pelin tarvitsemat resurssit
        palikkakuvat = new Image[5];
        powerupkuvat = new Image[8];
        palikkakuvat[0] = getImage(getDocumentBase(),"palikka1.png");
        palikkakuvat[1] = getImage(getDocumentBase(),"palikka2.png");
        palikkakuvat[2] = getImage(getDocumentBase(),"palikka3.png");
        palikkakuvat[3] = getImage(getDocumentBase(),"palikka4.png");
        palikkakuvat[4] = getImage(getDocumentBase(),"palikka5.png");
        powerupkuvat[0] = getImage(getDocumentBase(),"POW_Liimaus.png");
        powerupkuvat[1] = getImage(getDocumentBase(),"POW_Hidastus.png");
        powerupkuvat[2] = getImage(getDocumentBase(),"POW_Nopeutus.png");
        powerupkuvat[3] = getImage(getDocumentBase(),"POW_Jako.png");
        powerupkuvat[4] = getImage(getDocumentBase(),"POW_Laajennus.png");
        powerupkuvat[5] = getImage(getDocumentBase(),"POW_Kavennus.png");
        powerupkuvat[6] = getImage(getDocumentBase(),"POW_Ampuminen.png");
        pallokuva = getImage(getDocumentBase(),"pallo.png");
        mailakuva = getImage(getDocumentBase(),"maila.png");
        mailavlaita = getImage(getDocumentBase(),"mailavlaita.png");
        mailaolaita = getImage(getDocumentBase(),"mailaolaita.png");
        ammuskuva = getImage(getDocumentBase(),"ammus.png");
        peli = new Peli(getWidth(), getHeight()-60,palikkakuvat, powerupkuvat, pallokuva, mailakuva, ammuskuva,mailavlaita,mailaolaita);
        peli.asetaDocumentBase(getDocumentBase());
        peli.asetaAloituskuva(getImage(getDocumentBase(), "aloituskuva.png"));       
    }
    /**
     * K‰ynnist‰‰ s‰ikeen
     */
    public void start(){
        if(saie == null){
            saie = new Thread(this);
            saie.start();
        }
    }
    /**
     * Pyˆritt‰‰ itse peli‰. Kutsuu pelin paivita-metodia kun pelitila on 2
     */
    public void run(){
        while(true){
            //jos peli on k‰ynniss‰ niin p‰ivitet‰‰n sit‰ 
            if(peli.palautaPelitila() == 2){
                peli.paivita();
                this.pisteet.setText("Pisteet: "+peli.palautaPisteet());
                this.elamat.setText("El‰m‰t: "+peli.palautaElamat());
            }
            repaint();
            //hidastetaan pelin toimintaa
            try {
                saie.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * Luokka jonka avulla hoidetaan pelin piirt‰mist‰
     */
    public class PiirtoPaneeli extends JPanel{
        public void paintComponent(Graphics g){
            peli.piirra(g);
            String temp = getDocumentBase().getPath();
            temp = temp.substring(0,temp.lastIndexOf('/')+1);
        }
    }
    /**
     * Hiiren liikutuksen kuuntelua hoitava luokka
     */
    class HiirenLiikeKuuntelija extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent m) {
            //jos hiirt‰ liikutetaan, asetetaan pelaajan maila hiiren koordinaatteihin
            if(peli.palautaPelitila()== 2){
                peli.sijoitaMaila(m.getX());
            }            
        }
        public void mouseDragged(MouseEvent m){
            //Jos hiiren nappia pidet‰‰n pohjassa, mailan koordinaatit eiv‰t p‰ivity
            //pelk‰ll‰ mouseMoved-metodilla, joten on lis‰tt‰v‰ sama koodi myˆs
            //mouseDragged-metodiin
            if(peli.palautaPelitila()== 2){
                peli.sijoitaMaila(m.getX());
            }  
        }
    }
    /**
     * Hiiren nappien kuuntelua hoitava luokka
     */
    class kuunteleHiirinappi extends MouseAdapter{
        public void mousePressed(MouseEvent me){
           //Jos peli on k‰ynniss‰
            if(peli.palautaPelitila() == 2){
                //Vapautetaan pallot ja ammutaan.
                //Molemmat eiv‰t tapahdu koskaan yht‰aikaa koska mailalla voi olla vain yksi ominaisuus
                peli.vapautaPallot();
                peli.ammu();
            }
            //Jos peli ei ole viel‰ alkanut, aloitetaan se hiiren painalluksella
            if(peli.palautaPelitila() == 1){
                peli.uusiPeli();
            }
            //Jos peli on loppunut, palautetaan se alkutilaan hiiren painalluksella
            if(peli.palautaPelitila() == 3){
                peli.alustaPeli();
            }
        }
        
    }
}
