package com.example.migreeni;

import java.util.ArrayList;
import java.util.List;

class Merkinta_lista {
    private static final Merkinta_lista ourInstance = new Merkinta_lista();

    static Merkinta_lista getInstance() {
        return ourInstance;
    }

    private List<Uusi_merkinta> merkinnat;

    private Merkinta_lista() {

        merkinnat = new ArrayList<>();

    }

    public ArrayList<Uusi_merkinta> getMerkinnat(){
        return (ArrayList<Uusi_merkinta>) merkinnat;
    }
}
