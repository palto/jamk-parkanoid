/*
 * Objekti.java
 *
 * Luotu 30. syyskuuta 2006, 11:14
 */

package parkanoid;

import java.awt.Image;

/**
 * Luokka peliobjekteille
 * @author Toni Paloniemi, Severi Vidnäs
 * @version 1.0
 */
public class Objekti {
    
    private int x;
    private int y;
    private int leveys;
    private int korkeus;
    private Image kuva;
    /**
     * Objekti luokan konstruktori
     * @param x Objektin x-akselin koordinaatti
     * @param y Objektin y-akselin koordinaatti
     * @param kuva Kuva objektista
     */
    public Objekti(int x, int y, Image kuva){
        this.asetaY(y);
        this.asetaX(x);
        this.asetaKuva(kuva);
        this.leveys = this.kuva.getWidth(null);
        this.korkeus = this.kuva.getHeight(null);
        
    }
    /**
     * Metodi objektin x-akselin koordinaatin asettamiseksi
     * @param x Asetettava koordinaatti <code>int</code> lukuna.
     */
    public void asetaX(int x){
        this.x = x;
    }
    /**
     * Metodi objektin y-akselin koordinaatin asettamiseksi
     * @param y Asetettava koordinaatti <code>int</code> lukuna.
     */
    public void asetaY(int y){
        this.y = y;
    }
    /**
     * Metodi objektin kuvan asettamiseksi
     * @param kuva Asetettava kuva.
     */
    public void asetaKuva(Image kuva) {
        this.kuva = kuva;
    }
    /**
     * Metodi objektin kuvan palauttamiseksi
     * @return Palauttaa objektin kuvan<code>Image</code>:na.
     */
    public Image palautaKuva(){
        return this.kuva;
    }
    /**
     * Metodi objektin x-akselin koordinaatin palauttamiseksi.
     * @return Objektin x koordinaatti <code>int</code>:nä.
     */
    public int palautaX(){
        return this.x;
    }
    /**
     * Metodi objektin y-akselin koordinaatin palauttamiseksi.
     * @return Objektin y koordinaatti <code>int</code>:nä.
     */
    public int palautaY(){
        return this.y;
        
    } 
    /**
     * Metodi palauttaa objektin korkeuden pikseleinä.
     * @return Objektin korkeus <code>int</code>:nä.
     */
    public int palautaKorkeus() {
        return korkeus;
    }
    /**
     * Metodi palauttaa objektin leveyden pikseleinä.
     * @return Objektin leveys <code>int</code>:nä.
     */
    public int palautaLeveys() {
        return leveys;
    }
    /**
     * Metodilla asetetaan objektin leveys pikseleinä.
     * @param leveys Asetettava leveys <code>int</code>:nä.
     */
    public void asetaLeveys(int leveys){
        this.leveys = leveys;
    }
    /**
     * Metodilla asetetaan objektin korkeus pikseleinä.
     * @param korkeus Asetettava korkeus <code>int</code>:nä.
     */
    public void asetaKorkeus(int korkeus){
        this.korkeus = korkeus;
    }
}
