/*
 * Palikkalista.java
 *
 * Created on 26. marraskuuta 2006, 16:24
 *
 * 
 */

package parkanoid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Palikoiden tallennukeen ja lataamisen helpottamiseksi tarkoitettu luokka
 * @author Toni Paloniemi
 */
public class Palikkalista implements Serializable{
    private ArrayList<Palikka> palikat;
    private int palikkamaara;
    /** Luo uuden instanssin Palikkalistasta */
    public Palikkalista() {
        palikat = new ArrayList<Palikka>();
        palikkamaara = 0;
    }
    /**
     * Palautta listan palikoista ArrayListin‰
     * @return Palikkalista ArrayList-muodossa
     */
    public ArrayList<Palikka> palautaPalikat(){
        return palikat;
    }
    /**
     * Tuo listan palikoista palikkalistaan. H‰vitt‰‰ vanhat palikat Palikkalista-oliossa
     * @param palikat Tuotavat palikat
     */
    public void asetaPalikat(ArrayList<Palikka> palikat){
        this.palikat = palikat; 
    }
    /**
     * Lisaa palikan listaan. Jos listasta lˆytyy jo palikka samalta kohdalta, ei lis‰‰ listaan
     * @param palikka Palikka jota yritet‰‰n lis‰t‰ listaan
     * @return Palauttaa false jos lis‰‰minen listaan ei onnistunut, muutoin true
     */
    public boolean lisaaPalikka(Palikka palikka){
        boolean loytyiko = false;
        ListIterator<Palikka> i = this.palautaPalikat().listIterator();
        while(i.hasNext()){
            Palikka palikka2 = i.next();
            if(palikka2.palautaX() == palikka.palautaX()&&palikka2.palautaY() == palikka.palautaY()){
                loytyiko = true;
            }
            
        }
        if(loytyiko)return false;
        else{
            palikat.add(palikka);
            return true;
        }
    }
    /**
     * Lisaa palikan listaan jos siell‰ ei ole jo samassa koordinaateissa palikkaa. Poistaa siell‰ olevan palikan
     * jos sielt‰ lˆytyi samasta kohdasta palikka
     * @param palikka Palikka jota yritet‰‰n lis‰t‰ listaan
     */
    public void lisaaPalikka2(Palikka palikka){
        boolean loytyiko = false;
        Palikka poistopalikka = null;
        ListIterator<Palikka> i = this.palautaPalikat().listIterator();
        while(i.hasNext()){
            Palikka palikka2 = i.next();
            if(palikka2.palautaX() == palikka.palautaX()&&palikka2.palautaY() == palikka.palautaY()){
                poistopalikka = palikka2;
                loytyiko = true;
                break;
            }
            
        }
        
        if(loytyiko){
            palikat.remove(poistopalikka);
            palikkamaara--;
        }else{
            palikat.add(palikka);
            palikkamaara++;
        }
            
    }
    /**
     * Palauttaa palikoiden m‰‰r‰n palikkalistassa
     * @return Palikoiden m‰‰r‰ palikkalistassa
     */
    public int palikkamaara(){
        return this.palikkamaara;
    }
}
