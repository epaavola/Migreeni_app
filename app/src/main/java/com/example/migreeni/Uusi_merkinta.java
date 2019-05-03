package com.example.migreeni;

public class Uusi_merkinta {


    String paivamaara;
    String aika;
    String laake;
    String kipu;
    String lisatiedot;

    public Uusi_merkinta(String pvm, String aika, String laake, String kipu, String lisatiedot) {
        this.paivamaara = pvm;
        this.aika = aika;
        this.laake = laake;
        this.kipu = kipu;
        this.lisatiedot = lisatiedot;
    }

    public String getPaivamaara(){
        return this.paivamaara;
    }
    public String getAika(){
        return this.aika;
    }
    public String getLaake(){
        return this.laake;
    }
    public String getLisatiedot(){
        return this.lisatiedot;
    }
    public String getKipu(){
        return this.kipu;
    }

    public String toString() {
        return this.paivamaara;
    }
}
