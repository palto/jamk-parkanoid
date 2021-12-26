/*
 * Powerup.java
 *
 * Luotu 30. lokakuuta 2006, 10:53
 */

package parkanoid;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Luokka powerupeille
 * @author Toni Paloniemi, Severi Vidnäs
 * @version 1.0
 */
public class Powerup extends Objekti {
    private int tyyppi;
    private static double liikey = 4;
    public static final int POWERUPLEVEYS = 20;
    public static final int POWERUPKORKEUS = 20;
    /**
     * Powerupin konstruktori.
     * @param tyyppi Luotavan powerupin tyyppi.
     * @param x Luotavan powerupin x-akselin koordinaatti.
     * @param y Luotavan powerupin y-akselin koordinaatti.
     * @param kuva Luotavan powerupin kuva.
     */
    public Powerup(int tyyppi, int x, int y, Image kuva) {
        super(x,y,kuva);
        this.tyyppi = tyyppi;
        super.asetaLeveys(POWERUPLEVEYS);
        super.asetaKorkeus(POWERUPKORKEUS);
    }
    /**
     * Metodi powerupin piirtämiseksi.
     * @param g Graphics luokan olio jota tarvitaan piirtämiseen
     */
    public void piirra(Graphics g){
        g.drawImage(super.palautaKuva(),this.palautaX(), this.palautaY(), null);
        
    }
    /**
     * Metodi liikuttaa poweruppia alaspäin y-akselilla.
     */
    public void liikuta(){
        super.asetaY(super.palautaY()+(int)liikey);
    }
    /** 
     * Metodi palauttaa powerupin tyypin.
     * @return Tyyppi palautetaan <code>int</code>:nä.
     */
    public int palautaTyyppi (){
        return this.tyyppi;
    }
}
