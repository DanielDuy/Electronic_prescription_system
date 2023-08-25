public class Lege extends IndeksertListe<Resept> implements Comparable<Lege>{
    public final String legeNavn;
    IndeksertListe<Resept> utskrevneResepter = new IndeksertListe<>();
    public int antUtskrevneNarkoResept;

    public Lege(String paramNavn) {
        legeNavn = paramNavn;
    }

    public String hentNavn() {
        return legeNavn;
    }

    @Override
    public int compareTo(Lege annenLege) {
        return legeNavn.compareTo(annenLege.hentNavn());
    }

    public IndeksertListe<Resept> hentUtskrevneResepter() {
        return utskrevneResepter;
    }

    public Resept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        if (legemiddel instanceof NarkotiskLegemiddel) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        HvitResept nyHvitResept = new HvitResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(nyHvitResept);
        return nyHvitResept;
    }

    public MilResept skrivMilResept (Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
        if (legemiddel instanceof NarkotiskLegemiddel) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        MilResept nyMilResept = new MilResept(legemiddel, this, pasient);
        utskrevneResepter.leggTil(nyMilResept);
        return nyMilResept;
    }

    public PResept skrivPResept (Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        if (legemiddel instanceof NarkotiskLegemiddel) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        PResept nyPResept = new PResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(nyPResept);
        return nyPResept;
    }

    public BlaaResept skrivBlaaResept (Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        if (legemiddel instanceof NarkotiskLegemiddel) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        BlaaResept nyBlaaResept = new BlaaResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(nyBlaaResept);
        return nyBlaaResept;
    }

}
