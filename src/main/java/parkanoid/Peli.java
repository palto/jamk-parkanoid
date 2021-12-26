/*
 * Peli.java
 *
 * Created on 9. lokakuuta 2006, 11:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package parkanoid;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.*;
/**
 * Parkanoid-nimisen pelin logiikkaa hoitava luokka
 * @version Versio 1.0
 * @author Toni Paloniemi, Severi Vidn‰s
 */
public class Peli {
    private ArrayList<Objekti> objektit;
    private ArrayList<Pallo> pallot;
    private ArrayList<Pallo> poistopallot;
    private ArrayList<Palikka> palikat;
    private ArrayList<Palikka> poistopalikat;
    private ArrayList<Powerup> powerupit;
    private ArrayList<Powerup> poistopowerupit;
    private ArrayList<Ammus> ammukset;
    private ArrayList<Ammus> poistoammukset;
    private Image[] palikkakuvat;
    private Image[] powerupkuvat;
    private Image pallokuva;
    private Image mailakuva;
    private Image mailavlaita;
    private Image mailaolaita;
    private Image ammuskuva;
    private Image aloituskuva;
    private Maila maila;
    private URL documentBase;
    static final double ALKUVAUHTI = 4;
    private int pallomaara;
    private int leveys;
    private int korkeus;
    private double palloNopeus;
    private int elamat;
    private int pisteet;
    private String errorMessage;
    private int kentta;
    private int pelitila; //1 = alkuruutu, 2 = pelikuva, 3 = lopetusruutu, 4 = pause
    public static final int KENTTAMAARA = 10;
    private boolean halutaanAmpua;
    /** 
     * Pelin konstruktori. T‰m‰ luo uuden "pelin" ja alustaa sen valmiiksi pelaamista varten.
     * Resurssit l‰hetet‰‰n parametreilla
     *
     * @param leveys Pelikent‰n leveys pikseleiss‰
     * @param korkeus Pelikent‰n korkeus pikseleiss‰
     * @param palikkakuvat Lista peliss‰ k‰ytett‰vien palikoiden kuvista
     * @param powerupkuvat Lista peliss‰ k‰ytett‰vien poweruppien kuvista
     * @param pallokuva Peliss‰ k‰ytett‰v‰n pallon kuva
     * @param mailakuva Peliss‰ k‰ytett‰v‰n mailan kuva
     * @param ammuskuva Peliss‰ k‰ytett‰vien ammusten kuva
     * @param mailavlaita Peliss‰ k‰ytett‰v‰n mailan vasemman laidan kuva
     * @param mailaolaita Peliss‰ k‰ytett‰v‰n mailan oikean laidan kuva
     */
    public Peli(int leveys, int korkeus,Image[] palikkakuvat,Image[] powerupkuvat, Image pallokuva, Image mailakuva, Image ammuskuva, Image mailavlaita, Image mailaolaita) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.palikkakuvat = palikkakuvat;
        this.powerupkuvat = powerupkuvat;
        this.pallokuva = pallokuva;
        this.mailakuva = mailakuva;
        this.mailavlaita = mailavlaita;
        this.mailaolaita = mailaolaita;
        this.ammuskuva = ammuskuva;
        alustaPeli();
    }
    /**
     * Alustaa pelin pelaamista varten. T‰t‰ metodia tarvitaan aina kun halutaan aloittaa peli alusta
     */
    public void alustaPeli(){
        this.elamat = 2;
        this.pisteet = 0;
        errorMessage = "";
        kentta = 1;
        pelitila = 1;
    }
    /**
     * T‰m‰ metodi k‰ynnist‰‰ itse pelin.
     */
    public void uusiPeli(){
        this.halutaanAmpua = false;
        pallomaara = 0;
        this.palloNopeus = this.ALKUVAUHTI;
        objektit = new ArrayList<Objekti>();
        pallot = new ArrayList<Pallo>();
        palikat = new ArrayList<Palikka>();
        poistopalikat = new ArrayList<Palikka>();
        powerupit = new ArrayList<Powerup>();
        poistopowerupit = new ArrayList<Powerup>();
        poistopallot = new ArrayList<Pallo>();
        ammukset = new ArrayList<Ammus>();
        poistoammukset = new ArrayList<Ammus>();
        this.maila = new Maila(100,this.korkeus-15, this.mailakuva,this.leveys, this.mailavlaita, this.mailaolaita);
        Pallo pallo = new Pallo(this.maila.palautaKeskipiste(),this.maila.palautaY()-10,this.pallokuva,270+(int)Math.random()*90-45,this.palloNopeus);
        pallo.asetaLiimaus(true);
        pallo.asetaLiimauskohta(this.maila.palautaLeveys()/2);
        this.lisaaPallo(pallo);
        String temp = this.documentBase.toString();
        temp = temp.substring(0,temp.lastIndexOf('/')+1); 
        lataaUusiKentta(temp+"kentta"+kentta);
        this.pelitila = 2; //pistet‰‰n peli p‰‰lle
        
    }
    /**
     * Lis‰‰ objektin peliin.
     * @param objekti Peliin lis‰tt‰v‰ objekti
     */
    public void lisaaObjekti(Objekti objekti){
        objektit.add(objekti);
    }
    /**
     * Lis‰‰ pallon peliin.
     * @param pallo Peliin lis‰tt‰v‰ pallo
     */
    public void lisaaPallo(Pallo pallo){
        pallot.add(pallo);
        this.pallomaara++;
    }
    /**
     * T‰m‰ metodi piirt‰‰ kaikki peliin lis‰tyt objektit peliruutuun
     * @param g Graphics-pinta johon piirret‰‰n
     */
    public void piirraObjektit(Graphics g){
        ListIterator<Objekti> i = objektit.listIterator();
        while(i.hasNext()){
            Objekti objekti = i.next();
            g.drawImage(objekti.palautaKuva(),objekti.palautaX(), objekti.palautaY(), null);
        }
    }
    /**
     * T‰m‰ metodi piirt‰‰ kaikki peliin lis‰tyt pallot peliruutuun
     * @param g Graphics-pinta johon piirret‰‰n
     */
    public void piirraPallot(Graphics g){
        ListIterator<Pallo> i = pallot.listIterator();
        while(i.hasNext()){
            Pallo pallo = i.next();
            pallo.piirra(g);
        }
    }
    /**
     * T‰ss‰ metodissa toteutetaan kaikki peliss‰ tarvittava logiikka. Siin‰ k‰yd‰‰n l‰pi kaikki pelin
     * pallot, powerupit ja ammukset ja p‰ivitet‰‰n niit‰. Yksi kutsu paivita-metodiin edist‰‰ peli‰ yhdell‰
     * ns. kierroksella
     */
    public void paivita(){
        if(pelitila == 2){
            paivitaPallot();
            paivitaPowerUpit();
            paivitaAmmukset();
            luoAmmukset();
            if(this.elamat < 0){
                this.pelitila =3;
            }
            if (palikat.isEmpty()){
                kentta++;
                if(kentta <= this.KENTTAMAARA){
                    uusiPeli();
                }else{
                    this.pelitila = 3;
                }
            }
        }
    }
    /**
     * T‰m‰ metodi piirt‰‰ kaikki pelist‰ lˆytyv‰t objektit, pallot, palikat, mailan, powerupit ja ammukset
     * Jos peli ei ole viel‰ alkanut, se piirt‰‰ aloitusruudun, ja jos peli on loppunut, se piirt‰‰ lopetusruudun
     * Pause-tilassa piirret‰‰n sama mit‰ piirret‰‰n kun peli on k‰ynniss‰
     *
     * @param g Graphics-pinta johon piirret‰‰n
     */
    public void piirra(Graphics g){
        switch (this.pelitila)
        {
            case 1: {
                g.drawImage(aloituskuva, 0,0,null);
                break;
            }
                    
            case 2: 
            case 4: {
                        piirraObjektit(g);
                        piirraPallot(g);
                        maila.piirra(g);
                        piirraPalikat(g);
                        piirraPowerup(g);
                        piirraAmmukset(g);
                     }break;
            
            case 3: g.drawString("Peli p‰‰ttyi. Sait pisteit‰: "+this.palautaPisteet(), 10,10);break;
        }
    }
    /**
     * Lis‰‰ palikan peliin.
     *
     * @param x Lis‰tt‰v‰n palika x-koordinaatti
     * @param y Lis‰tt‰v‰n palikan y-koordinaatti
     * @param tyyppi Lis‰tt‰v‰n palikan tyyppi
     */
    public void lisaaPalikka(int x, int y, int tyyppi){
        Palikka palikka = new Palikka(x,y,tyyppi);
        palikat.add(palikka);
    }
    /**
     * T‰m‰ metodi piirt‰‰ kaikki peliin lis‰tyt palikat peliruutuun
     * @param g Graphics-pinta johon piirret‰‰n
     */
    public void piirraPalikat(Graphics g){
    ListIterator<Palikka> i = palikat.listIterator();
        while(i.hasNext()){
            Palikka palikka = i.next();
            palikka.piirra(g, palikkakuvat[palikka.palautaTyyppi()]);
        }
    }
    /**
     * T‰m‰ metodi piirt‰‰ kaikki peliin lis‰tyt ammukset peliruutuun
     * @param g Graphics-pinta johon piirret‰‰n
     */
     public void piirraAmmukset(Graphics g){
        ListIterator<Ammus> i = ammukset.listIterator();
        while(i.hasNext()){
            Ammus ammus = i.next();
            ammus.piirra(g);
        }
    }
    /**
    * V‰hent‰‰ palikan kestoa ja poistaa sen jos se on tarpeeksi heikko.
    * Hoitaa myˆs pisteiden lis‰‰misen pelaajalle sek‰ poweruppien tiputtamisen. Jos palikka tuhoutuu,
    * sit‰ ei viel‰ poisteta vaan se lis‰t‰‰n poistolistaan. T‰m‰n j‰lkeen on erikseen kutsuttava poistaPalikat-
    * metodia.
    * @param palikka Palikka johon toiminto halutaan kohdistaa
    * @see #poistaPalikat
    */
    public void poistaPalikka(Palikka palikka){
        this.pisteet += 10;
        palikka.asetaTyyppi(palikka.palautaTyyppi()-1);
        if (palikka.palautaTyyppi() == -1){ // jos palikan kesto menee miinukselle
            poistopalikat.add(palikka); // lis‰t‰‰n se poistettavien listaan
            arvoPowerup(palikka);       // l‰hetet‰‰n arvontak‰sky
        }

    }
    /**
     * Poistaa lopullisesti kaikki poistettavaksi m‰‰r‰tyt palikat pelist‰. 
     * K‰y l‰pi er‰‰nlaisen poistolistan johon on talletettu referenssi
     * kaikista poistettavista palikoista.
     */
    public void poistaPalikat(){
        //poistaa pelist‰ poistolistassa olevat palikat
        ListIterator<Palikka> i = poistopalikat.listIterator();
        while(i.hasNext()){
            Palikka palikka = i.next();
            palikat.remove(palikka);
        }
        poistopalikat.clear();        
    }
    /**
     * Laskee onko jokin tietty piste jonkin tietyn suorakulmion sis‰ll‰
     * 
     * @param x1 Pisteen x-koordinaatti
     * @param y1 Pisteen y-koordinaatti
     * @param x2 Suorakulmion vasemman yl‰kulman x-koordinaatti
     * @param y2 Suorakulmion vasemman yl‰kulman y-koordinaatti
     * @param leveys Suorakulmion leveys pikseleiss‰
     * @param korkeus Suorakulmion korkeus pikseleiss‰
     * @return true jos on kulmion sis‰ll‰, false jos ei ole
     */
    public boolean pisteKulmiossa(int x1, int y1, int x2, int y2, int leveys, int korkeus){
        if( x1 > x2&&x1 < x2+leveys&& y1 > y2 && y1 < y2 + korkeus) return true;
        return false;
    }
    /**
     * Vapauttaa kaikki pallot jotka ovat kiinnitetty pelaajan mailaan.
     */
    public void vapautaPallot(){
        ListIterator<Pallo> i = pallot.listIterator();
        while(i.hasNext()){
            Pallo pallo = i.next();
            pallo.asetaLiimaus(false);
        }
    }
    /**
     * Arpoo tipahtaako palikasta powerup ja jos tipahtaa niin mink‰ tyyppinen
     *
     * @param p Palikka josta powerup tipahtaa
     */
    public void arvoPowerup(Palikka p){
        double arpa = Math.random()*100;
        if (arpa <= 25) {
            int tyyppi= (int)Math.round(Math.random()*6);
            lisaaPowerup(p.palautaX(),p.palautaY(),tyyppi);
        }
    }
    /**
     * Lis‰‰ peliin powerupin
     * @param x Lis‰tt‰v‰n powerupin x-koordinaatti
     * @param y Lis‰tt‰v‰n powerupin y-koordinaatti
     * @param tyyppi Lis‰tt‰v‰n powerupin tyyppi
     */
    public void lisaaPowerup(int x, int y, int tyyppi){
        Powerup powerup = new Powerup(tyyppi,x,y,powerupkuvat[tyyppi]);
        powerupit.add (powerup);
        
    }
    /**
     * Piirt‰‰ kaikki peliin lis‰tyt powerupit
     * @param g Graphics-pinta johon powerup-piirret‰‰n
     */
    public void piirraPowerup(Graphics g){
        ListIterator<Powerup> i = powerupit.listIterator();
        while(i.hasNext()){
            Powerup pow = i.next();
            pow.piirra(g);
        }
    }
    /**
     * Poistaa powerupin pelist‰. Poiston j‰lkeen on viel‰ kutsuttava poistaPowerupit-metodia jotta powerup lopullisesti
     * poistuisi pelist‰.
     * @param pow Powerup joka halutaan poistaa
     * @see #poistaPowerupit
     */
    public void poistaPowerup (Powerup pow){
         //lis‰‰ powerupin poistolistaan
        poistopowerupit.add(pow);
    }
    /**
     * Poistaa lopullisesti kaikki poistettavaksi m‰‰r‰tyt powerupit pelist‰
     */
    public void poistaPowerupit (){
         //poistaa pelist‰ poistolistassa olevat powerupit
        ListIterator<Powerup> i = poistopowerupit.listIterator();
        while(i.hasNext()){
            Powerup pow = i.next();
            powerupit.remove(pow);
        }
        poistopowerupit.clear();     
    }
    /**
     * Asettaa kaikille pelist‰ lˆytyville palloille vauhdin joka m‰‰ritell‰‰n metodin parametriss‰
     * @param vauhti Vauhti joka palloille halutaan asettaa
     */
    public void nopeutaPallot (double vauhti){
        ListIterator<Pallo> i = pallot.listIterator();
        while (i.hasNext()){
            Pallo pallo = i.next();
            pallo.asetaVauhti(vauhti);
        }
    }
    /**
     * Poistaa lopullisesti kaikki poistettavaksi m‰‰r‰tyt pallot pelist‰
     */
    public void poistaPallot(){
        //poistaa pelist‰ poistolistassa olevatpallot
        ListIterator<Pallo> i = poistopallot.listIterator();
        while(i.hasNext()){
            Pallo pallo = i.next();
            pallot.remove(pallo);
        }
        poistopallot.clear();        
    }
    /**
     * M‰‰r‰‰ pallon poistettavaksi pelist‰. Pallo ei poistu lopullisesti ennenkuin metodia poistaPallot
     * on kutsuttu. Jos kaikki pallot ovat karanneet pelaajalta, pelaaja menett‰‰ el‰m‰n
     * @param pallo Poistettava pallo
     * @see #poistaPallot
     */
    public void poistaPallo(Pallo pallo){
        //lis‰‰ pallon poistolistaan
        poistopallot.add(pallo);
        this.pallomaara--;
        if (this.pallomaara == 0){
            this.elamat--;
            
        }
    }
    /**
     * Metodi jakaa kaikki pelist‰ lˆytyv‰t pallot ns. kahtia. Todellisuudessa luo jokaista palloa kohden uuden
     * pallon ja asettaa sille jonkin satunnaisen suunnan
     */
    public void jaaPallot(){
        ArrayList templista = (ArrayList)this.pallot.clone();
        ListIterator i = templista.listIterator();
        while(i.hasNext()){
            Pallo pallo = (Pallo)i.next();
            double suunta = 0;
            while(suunta < 20 || suunta > 340||(suunta > 160 && suunta < 200)) suunta = (int)(Math.random()*360);
            this.lisaaPallo(new Pallo(pallo.palautaX(), pallo.palautaY(),this.pallokuva,suunta,this.palloNopeus));
        }
        
    }
    /**
     * Metodi k‰skee peli‰ ampumaan mailasta. Ampuminen tapahtuu varsinaisesti vasta luoAmmukset-metodissa.
     * T‰t‰ metodia k‰ytet‰‰n jotta ampuminen olisi turvallisempaa useampis‰ikeisess‰ ohjelmassa
     * @see #luoAmmukset
     */
    public void ammu(){
        long odotusaika = 100;
        if (this.maila.palautaOminaisuus()==6){
            this.halutaanAmpua = true;
        }
    }
    /**
     * M‰‰r‰‰ ammuksen poistettavaksi pelist‰. Panos ei poistu lopullisesti ennenkuin metodia poistaAmmukset
     * on kutsuttu
     * @param panos Poistettava ammus
     * @see #poistaAmmukset
     */
    public void poistaAmmus(Ammus panos){
        //lis‰‰ ammus poistolistaan
        poistoammukset.add(panos);  
    }
    /**
     * Poistaa lopullisesti kaikki pelist‰ poistettavaksi m‰‰r‰tyt ammukset.
     */
    public void poistaAmmukset(){
        //poistaa pelist‰ poistolistassa olevatpallot
        ListIterator<Ammus> panos = poistoammukset.listIterator();
        while(panos.hasNext()){
            Ammus ammus = panos.next();
            ammukset.remove(ammus);
        }
        poistoammukset.clear();        
    }
    /**
     * Lataa palikkalistan tiedostosta. T‰m‰n voi k‰sitt‰‰ pelikent‰n lataamisena. Entinen kentt‰ h‰vi‰‰
     * t‰t‰ metodia kutsuttaessa. Jos lataus ei onnistu parametrin‰ syˆtetyst‰ polusta, yritt‰‰ metodi viel‰
     * itse ladata kent‰n pelikansion juuresta, mutta t‰m‰ ei toimi netiss‰.
     * @param polku Ladattavan kent‰n polku
     */
    public void lataaUusiKentta(String polku){
        
        try{
            URL lahde = new URL(polku);
            System.out.println("avataan tiedostovirta");
            errorMessage = "avataan tiedostovirta";
            InputStream virta = lahde.openStream();
            System.out.println("tiedostovirta avattu");
            errorMessage = "tiedostovirta avattu";
            ObjectInputStream objIn = new ObjectInputStream(virta);
            System.out.println("luetaan objekti");
            errorMessage = "luetaan objekti";
            palikat = ((Palikkalista)objIn.readObject()).palautaPalikat();
            errorMessage = "objekti luettu";
            objIn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
            try{
                FileInputStream fileIn = new FileInputStream("kentta"+kentta);
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
                System.out.println("luetaan objekti");
                this.palikat = ((Palikkalista)objIn.readObject()).palautaPalikat();
                objIn.close();
            }catch(Exception ee){
                System.out.println(ee.getMessage());
            }
        }
        
    }
    /**
     * Palauttaa lukum‰‰r‰n pelaajan el‰mist‰
     * @return Lukum‰‰r‰ pelaajan el‰mist‰
     */
    public int palautaElamat(){
        return this.elamat;
    }
    /**
     * Palauttaa lukum‰‰r‰n pelaajan pisteist‰
     * @return Lukum‰‰r‰ pelaajan pisteist‰
     */
     
    public int palautaPisteet(){
        return this.pisteet;
    }
    /**
     * P‰ivitt‰‰ kaikki pelist‰ lˆytyv‰t pallot pelilogiikan mukaan. Liikuttaa palloja ja hoitaa tˆrm‰yksentunnistuksen
     */
    public void paivitaPallot(){
        ListIterator<Pallo> i = pallot.listIterator();
        while(i.hasNext()){
            Pallo pallo = i.next();
            //pallon liikutus
            if(pallo.palautaLiimaus()==true)
                pallo.liimaLiiku(this.maila.palautaX());
            else
                pallo.liiku();
            //tˆrm‰ys seiniin
            if(pallo.palautaX() < 0 || pallo.palautaX() + pallo.palautaLeveys() > this.leveys){
                pallo.peruuta();
                pallo.kaannaX();
            }
            if(pallo.palautaY() < 0){
                pallo.peruuta();
                pallo.kaannaY();
            }
            if(pallo.palautaY() > this.korkeus){
                poistaPallo(pallo);
            }
            
            //tˆrm‰ys mailaan
            if(pallo.palautaX() < maila.palautaX()+maila.palautaLeveys() && pallo.palautaX()+pallo.palautaLeveys() > maila.palautaX()
                    && pallo.palautaY() < maila.palautaY()+maila.palautaKorkeus() && pallo.palautaY()+pallo.palautaKorkeus() > maila.palautaY()){
                //nostetaan palloa ylˆsp‰in
                pallo.aseta(pallo.palautaX(),pallo.palautaY()-(pallo.palautaY()+pallo.palautaKorkeus()-maila.palautaY()));
                //lasketaan pallolle uusi suunta
                pallo.asetaSuunta(90*((double)(pallo.palautaX()-maila.palautaX())/(double)maila.palautaLeveys())+225,this.palloNopeus);
                //nopeutetaan palloja joka kerta kun osutaan mailaa, jotta peli vaikenisi sen edetess‰
                this.asetaPalloNopeus((double)1.01);
                if (maila.palautaOminaisuus() == 0){
                    pallo.asetaLiimaus(true);
                    pallo.asetaLiimauskohta(pallo.palautaX()-maila.palautaX());
                }
            }
            //tˆrm‰ys palikkaan
            ListIterator<Palikka> n = palikat.listIterator();
            double keskiarvokulma = 0;
            int tormaysmaara = 0;
            while(n.hasNext()){
                Palikka palikka = n.next();
                //ovatko pallo ja palikka p‰‰llekk‰in
                if(pallo.palautaX() < palikka.palautaX()+palikka.palautaLeveys() && pallo.palautaX()+pallo.palautaLeveys() > palikka.palautaX()
                    && pallo.palautaY() < palikka.palautaY()+palikka.palautaKorkeus() && pallo.palautaY()+pallo.palautaKorkeus() > palikka.palautaY()){
                    //jos tapahtui niin miss‰ kulmassa
                    //lasketaan ympyr‰n pisteiden avulla tˆrm‰yskulman keskiarvo
                    
                    for(int x = 0; x < 360; x+=1){
                        if(pisteKulmiossa(pallo.palautaX()+pallo.palautaLeveys()/2+(int)Math.round(Math.cos(Math.toRadians((double)x))*pallo.palautasade()),pallo.palautaY()+pallo.palautaKorkeus()/2+(int)Math.round(Math.sin(Math.toRadians((double)x))*pallo.palautasade()), palikka.palautaX(), palikka.palautaY(),
                                palikka.palautaLeveys(), palikka.palautaKorkeus())){
                            keskiarvokulma += x;
                            tormaysmaara++;
                            
                        }
                    }
                    //k‰‰nnet‰‰n pallon suuntaa riippuen tˆrm‰yskulmasta
                    if (tormaysmaara > 0){
                        //lasketaan kulma jossa tˆrm‰ttiin keskiarvolla
                        keskiarvokulma = keskiarvokulma/(double)tormaysmaara;
                        keskiarvokulma /=(double)360;
                        keskiarvokulma *=(double)4;
                        keskiarvokulma =Math.round(keskiarvokulma);
                        //jos tˆrm‰ttiin vaakasuunnassa
                        if(keskiarvokulma == 3 || keskiarvokulma == 1){
                            pallo.peruuta();
                            pallo.kaannaY();
                        }
                        //jos tˆrm‰ttiin pystysuunnassa
                        if(keskiarvokulma == 2 || keskiarvokulma == 0 || keskiarvokulma == 4){
                            pallo.peruuta();
                            pallo.kaannaX();
                        }
                        poistaPalikka(palikka);
                    }
                    
                    
                }
            }
            //poistetaan tuhoutuneet palikat pelist‰
            poistaPalikat();                
        }
        poistaPallot();
        
        if(this.pallomaara == 0){//jos kaikki pelaajan pallot katoavat
            //alustetaan pallojen nopeus ja luodaan pelaajalle uusi pallo
            this.palloNopeus = this.ALKUVAUHTI;
            this.maila = new Maila(100,this.korkeus-15, this.mailakuva,this.leveys, this.mailavlaita, this.mailaolaita);
            Pallo pallo = new Pallo(this.maila.palautaKeskipiste(),this.maila.palautaY()-10,this.pallokuva,270+(int)Math.random()*90-45,this.palloNopeus);
            pallo.asetaLiimaus(true);
            pallo.asetaLiimauskohta(this.maila.palautaLeveys()/2);
            
            this.lisaaPallo(pallo);
            
        }
    }
    /**
     * P‰ivitt‰‰ kaikki pelist‰ lˆytyv‰t powerUpit pelilogiikan mukaan. Liikuttaa niit‰
     * ja hoitaa myˆskin tˆrm‰yksentarkistuksen mailan kanssa. Laittaa t‰yt‰ntˆˆn myˆs poweruppien
     * antaman vaikutuksen jos pelaaja sai powerupin ker‰tty‰
     */
    public void paivitaPowerUpit(){
        ListIterator<Powerup> x = powerupit.listIterator();
        while(x.hasNext()){
            Powerup pow = x.next();
            pow.liikuta();
            //jos powerup karkasi ruudulta
            if (pow.palautaY() > this.korkeus) this.poistaPowerup(pow);
            //jos powerup osui mailaan
            if(pow.palautaX() < maila.palautaX()+maila.palautaLeveys() && pow.palautaX()+pow.palautaLeveys() > maila.palautaX()
                    && pow.palautaY() < maila.palautaY()+maila.palautaKorkeus() && pow.palautaY()+pow.palautaKorkeus() > maila.palautaY()){
                poistaPowerup(pow);
                this.pisteet+=50;
                //toteutetaan powerupin vaikutus
                switch (pow.palautaTyyppi()){
                    //0 - Pallojen liimaus mailaan
                    case 0 : maila.asetaOminaisuus(pow.palautaTyyppi()); break;
                    //1 - Pallojen hidastus
                    case 1 : asetaPalloNopeus((double)0.66); break;
                    //2 - Pallojen nopeutus
                    case 2 : asetaPalloNopeus((double)1.5); break;
                    //3 - Pallojen jako
                    case 3 : if (pallot.size() < 16) jaaPallot(); vapautaPallot(); break;
                    //4 - Mailan kasvattaminen
                    case 4 : this.maila.mailanKoko(+1); vapautaPallot(); break;
                    //5 - Mailan pienent‰minen
                    case 5 : this.maila.mailanKoko(-1); vapautaPallot(); break;
                    //6 - Ampuminen
                    case 6 : maila.asetaOminaisuus(pow.palautaTyyppi()); vapautaPallot(); break;
                }
            }
        }
        poistaPowerupit();
    }
    /**
     * Asettaa pelin pallojen liikkumisnopeuden. Liikkumisnopeus annetaan kertoimella, eli metodilla
     * voi hidastaa tai nopeuttaa pallojen kulkua.
     * @param kerroin Kerroin jolla pallojen nopeutta muutetaan
     */
    public void asetaPalloNopeus(double kerroin){
        this.palloNopeus *= kerroin;
        if (this.palloNopeus < this.ALKUVAUHTI/2)this.palloNopeus = this.ALKUVAUHTI/2;
        if (this.palloNopeus > this.ALKUVAUHTI*2)this.palloNopeus = this.ALKUVAUHTI*2;
        nopeutaPallot(palloNopeus);
    }
    /**
     * Paivittaa kaikki pelist‰ lˆytyv‰t ammukset pelilogiikan mukaan. Liikuttaa ammuksia ja hoitaa tˆrm‰ykset
     * palikoihin.
     */
    public void paivitaAmmukset(){
        ListIterator<Ammus> a = ammukset.listIterator();
        while(a.hasNext()){
            try{
                Ammus ammus = a.next();

                ammus.liiku();
                if(ammus.palautaY() < 0)
                    poistaAmmus(ammus);
                ListIterator<Palikka> n = palikat.listIterator();
                while(n.hasNext()){
                    Palikka palikka = n.next();
                    //jos ammus osuu johonkin palikkaan
                    if(ammus.palautaX() < palikka.palautaX()+palikka.palautaLeveys() && ammus.palautaX()+ammus.palautaLeveys() > palikka.palautaX()
                        && ammus.palautaY() < palikka.palautaY()+palikka.palautaKorkeus() && ammus.palautaY()+ammus.palautaKorkeus() > palikka.palautaY()){
                        poistaPalikka(palikka);
                        poistaAmmus(ammus);
                    }
                }
            }catch(Exception e){}    
        }
        poistaAmmukset();
        poistaPalikat();
    }
    /**
     * Ampuu mailasta kaksi ammusta jos peliin on m‰‰r‰tty ammu-metodilla ett‰ halutaan ampua
     * @see #ammu
     */
    public void luoAmmukset(){
        if (this.halutaanAmpua == true){
            this.halutaanAmpua=false;
            ammukset.add(new Ammus(maila.palautaX(),maila.palautaY(),this.ammuskuva));
            ammukset.add(new Ammus(maila.palautaX()+maila.palautaLeveys(),maila.palautaY(), this.ammuskuva));
        }
    }
    /**
     * Kertoo miss‰ tilassa peli on. Tiloja on nelj‰: 
     * 1 - aloitusruutu
     * 2 - pelitila
     * 3 - lopetusruutu
     * 4 - pause
     * @return Tila jossa peli on
     */
    public int palautaPelitila(){
        return this.pelitila;
    }
    /**
     * Asettaa pelin johonkin tilaan. Tiloja on nelj‰:
     * 1 - aloitusruutu
     * 2 - pelitila
     * 3 - lopetusruutu
     * 4 - pause
     * 
     * @param tila Pelitila joka pelille halutaan asettaa
     */
    public void asetaPelitila (int tila){
        this.pelitila = tila;
    }
    /**
     * Sijoittaa mailan keskipisteen parametrina annettuun x-koordinaattiin
     * 
     * @param x Mailan tulevan keskipisteen x-koordinaatti
     */
    public void sijoitaMaila (int x){
        this.maila.asetaKeskipiste(x);
    }
    /**
     * Kertoo pelille pelin juuren osoitteen
     * @param osoite URL-osoite kansioon jossa peli sijaitsee
     */
    public void asetaDocumentBase (URL osoite){
        this.documentBase = osoite;
    }
    /**
     * Asettaa pelin aloituskuvan joka n‰ytet‰‰n kun pelitila on 2
     * @param kuva Aloituskuva
     */
    public void asetaAloituskuva (Image kuva){
        this.aloituskuva = kuva;
    }
}
