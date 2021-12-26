/*
 * Maila.java
 *
 * Luotu 30. syyskuuta 2006, 10:20
 */

package parkanoid;
import java.awt.*;
/**
 * Luokka pelaajan mailalle
 * @author Toni Paloniemi, Severi Vidn‰s
 * @version 1.0
 */
public class Maila extends Objekti{
    private int mailaleveys;
    private int korkeus;
    private int kenttaleveys;
    private int ominaisuus;
    public static final int MAILANLEVEYS = 50;
    public static final int MAILANKORKEUS = 10;
    private Image mailavlaita;
    private Image mailaolaita;
    
    /**
     * Mailan konstruktori
     * @param x Mailan x-akselin koordinaatti
     * @param y Mailan y-akselin koordinaatti
     * @param kenttaleveys Pelikent‰n x-akselin kokonaisleveys
     * @param vasen Mailan vasemman p‰‰dyn kuva
     * @param oikea Mailan oikean p‰‰dyn kuva
     */
    public Maila(int x, int y, Image kuva, int kenttaleveys, Image vasen, Image oikea) {
        super(x,y,kuva);
        super.asetaX(x);
        super.asetaY(y);
        this.mailaleveys = 2;
        this.korkeus = 10;
        this.mailavlaita = vasen;
        this.mailaolaita = oikea;
        super.asetaLeveys(this.mailaleveys*MAILANLEVEYS);
        super.asetaKorkeus(MAILANKORKEUS);
        this.ominaisuus = -1;
        this.kenttaleveys = kenttaleveys;

    }
    /**
     * Metodilla piirret‰‰n maila.
     * @param g Graphics luokan olio jota tarvitaan piirtoon.
     */
    public void piirra(Graphics g){
        for (int i=0; i<this.mailaleveys; i++) g.drawImage(super.palautaKuva(),palautaX()+i*50,palautaY(),null);
        g.drawImage(this.mailavlaita,palautaX()-5,palautaY(),null);
        g.drawImage(this.mailaolaita,palautaX()+this.mailaleveys*MAILANLEVEYS,palautaY(),null);
    }
    /**
     * Metodi mailan keskipisteen asettamiseksi.
     * @param x Mailan x-koordinaatti kutsumishetkell‰
     */
    public void asetaKeskipiste(int x){
        int mailax = x-super.palautaLeveys()/2;
        if(mailax < 0)mailax = 0;
        if(mailax + super.palautaLeveys()> kenttaleveys)mailax = kenttaleveys - super.palautaLeveys();
        super.asetaX(mailax);
    }
    /**
     * Metodi palauttaa mailan keskipisteen x-akselilla
     * @return Mailan keskipiste x-akselilta <code>int</code> arvona.
     */
    public int palautaKeskipiste(){
        return super.palautaX() + super.palautaLeveys()/2;
    }
    /**
     * Metodilla asetetaan mailalle ominaisuus p‰‰lle.
     * @param ominaisuus Mailalle asetettava ominaisuus <code>int</code> lukuna.
     */
    public void asetaOminaisuus(int ominaisuus){
        this.ominaisuus = ominaisuus;
    }
    /**
     * Metodi palauttaa mailan ominaisuuden.
     * @return Mailan ominaisuus <code>int</code> lukuna.
     */
    public int palautaOminaisuus(){
        return this.ominaisuus;
    }
    /**
     * Metodilla muutetaan mailan kokoa. Mailan koko muuttuu 50 * muutos -pikselin verran muutoksen m‰‰r‰‰m‰‰n suuntaan.
     * Muutosta ei tapahtu mik‰li mailasta on tulossa liian suuri tai liian pieni.
     * @param muutos Muutoksen m‰‰r‰ <code>int</code> lukuna
     */
    public void mailanKoko(int muutos){
        int vanhakeskipiste = this.palautaKeskipiste();
        if (this.mailaleveys + muutos >= 1 && this.mailaleveys + muutos <= 4){
            this.mailaleveys += muutos;
            super.asetaLeveys(this.mailaleveys*50);
            this.asetaKeskipiste(vanhakeskipiste);
        }
    }
    /**
     * Metodilla asetetaan mailan oikean laidan kuva.
     * @param kuva Kuva mailan oikeasta laidasta
     */
    public void asetaOikeamailakuva(Image kuva){
        this.mailaolaita = kuva;
    }
    /**
     * Metodilla asetetaan mailan vasemman laidan kuva.
     * @param kuva Kuva mailan vasemmasta laidasta
     */
    public void asetaVasenmailakuva(Image kuva){
        this.mailavlaita = kuva;
    }
}
