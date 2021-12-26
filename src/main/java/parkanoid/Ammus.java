/*
 * Ammus.java
 *
 * Luotu 14. marraskuuta 2006, 15:16
 */

package parkanoid;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Luokka pelaajan ammuksille
 * @author Toni Paloniemi, Severi Vidn‰s
 * @version 1.0
 */
public class Ammus extends Objekti {
    private int vauhtiy;
    public static int OLETUSVAUHTI = -6;
    private boolean poistetaanko;
    public static final int AMMUSLEVEYS = 3;
    public static final int AMMUSKORKEUS = 5;
    /** 
     * Ammus luokan konstruktori
     * @param x Ammuksen x-akselin koordinaatti
     * @param y Ammuksen y-akselin koordinaatti
     * @param kuva Ammukselle piirrett‰v‰ kuva
     */
    public Ammus(int x,int y,Image kuva) {
        super(x,y,kuva);
        this.vauhtiy = this.OLETUSVAUHTI;
        this.poistetaanko = false;
        this.asetaLeveys(AMMUSLEVEYS);
        this.asetaKorkeus(AMMUSKORKEUS);
    }
    /**
     * Metodi ammuksen liikuttamiseen. Metodi asettaa ammukselle uuden sijainnin
     * y akselilta aina kun sit‰ kutsutaan.
     */
    public void liiku(){
        this.asetaY(this.palautaY()+this.vauhtiy);
    }
    /**
     * Metodilla piirret‰‰n ammus.
     * @param g Graphics luokan olio jota tarvitaan piirtoon.
     */
    public void piirra(Graphics g){
        g.drawImage(super.palautaKuva(),this.palautaX(), this.palautaY(), null);        
    }
    /**
     * Metodi palauttaa ammuksen poistotilan
     * @return poistetaanko true tai false
     */
    public boolean palautaPoistetaanko(){
        return this.poistetaanko;
    }
    /**
     * Metodilla asetetaan ammuksen poistotila
     * @param poistetaanko true tai false
     */
    public void asetaPoistetaanko(boolean poistetaanko){
        this.poistetaanko = poistetaanko;
    }
}
