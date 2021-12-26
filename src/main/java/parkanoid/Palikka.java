/*
 * Palikka.java
 *
 * Luotu 24. lokakuuta 2006, 10:11
 */

package parkanoid;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

/**
 * Luokka pelikenttien palikoille
 * @author Toni Paloniemi, Severi Vidn‰s
 * @version 1.0
 */
public class Palikka implements Serializable{
    private int tyyppi;
    public static final int PALIKKALEVEYS = 30;
    public static final int PALIKKAKORKEUS = 15;
    private int x;
    private int y;
    private int leveys;
    private int korkeus;
    /**
     * Palikan konstruktori. Asetetaan palikka tiettyyn pisteeseen ja asetetaan sille tyyppi joka m‰‰ritt‰‰ palikan v‰rin ja kest‰vyyden.
     * @param x Luotavan palikan x-akselin koordinaatti.
     * @param y Luotavan palikan y-akselin koordinaatti.
     * @param tyyppi Luotavan palikan tyyppi.
     */
    public Palikka(int x, int y , int tyyppi) {
        this.x = x;
        this.y = y;
        this.tyyppi = tyyppi;
        asetaLeveys(PALIKKALEVEYS);
        asetaKorkeus(PALIKKAKORKEUS);
    }
    /**
     * Metodi palikan piirt‰miseksi.
     * @param g Graphics luokan olio jota tarvitaan piirt‰miseen
     * @param kuva Kuva piirrett‰v‰st‰ palikasta.
     */
    public void piirra(Graphics g, Image kuva){
        g.drawImage(kuva,this.palautaX(), this.palautaY(), null);     
    }
    /**
     * Metodi palikan x-akselin koordinaatin asettamiseksi
     * @param x Asetettava koordinaatti <code>int</code> lukuna.
     */
     public void asetaX(int x){
        this.x = x;
    }
     /**
     * Metodi palikan y-akselin koordinaatin asettamiseksi
     * @param y Asetettava koordinaatti <code>int</code> lukuna.
     */
    public void asetaY(int y){
        this.y = y;
    }
    /**
     * Metodi palikan x-akselin koordinaatin palauttamiseksi.
     * @return Palikan x koordinaatti <code>int</code>:n‰.
     */
    public int palautaX(){
        return this.x;
    }
    /**
     * Metodi palikan y-akselin koordinaatin palauttamiseksi.
     * @return Palikan y koordinaatti <code>int</code>:n‰.
     */
    public int palautaY(){
        return this.y;
        
    } 
    /**
     * Metodi palauttaa palikan korkeuden pikselein‰.
     * @return Palikan korkeus <code>int</code>:n‰.
     */
    public int palautaKorkeus() {
        return korkeus;
    }
    /**
     * Metodi palauttaa palikan leveyden pikselein‰.
     * @return Palikan leveys <code>int</code>:n‰.
     */
    public int palautaLeveys() {
        return leveys;
    }
    /**
     * Metodilla asetetaan palikan leveys pikselein‰.
     * @param leveys Asetettava leveys <code>int</code>:n‰.
     */
    public void asetaLeveys(int leveys){
        this.leveys = leveys;
    }
    /**
     * Metodilla asetetaan palikan korkeus pikselein‰.
     * @param korkeus Asetettava korkeus <code>int</code>:n‰.
     */
    public void asetaKorkeus(int korkeus){
        this.korkeus = korkeus;
    }
    /**
     * Metodi palauttaa palikan tyypin.
     * @return Palikan tyyppi <code>int</code>:n‰.
     */
    public int palautaTyyppi(){
        return this.tyyppi;
    }
     /**
     * Metodilla asetetaan palikan tyyppi.
     * @param tyyppi Asetettava tyyppi <code>int</code>:n‰.
     */
    public void asetaTyyppi(int tyyppi){
        this.tyyppi = tyyppi;
    }
}
