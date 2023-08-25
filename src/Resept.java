abstract public class Resept {
    public final int id;
    public final Legemiddel reseptLegemiddel;
    public final Lege reseptLege;
    public final Pasient pasient;
    public int reit;
    private static int teller = 0;

    public Resept(Legemiddel paramReseptLegemiddel, Lege paramReseptLege, Pasient paramPasient, int paramReit) {
        id = teller;
        teller += 1;
        this.reseptLegemiddel = paramReseptLegemiddel;
        this.reseptLege = paramReseptLege;
        this.pasient = paramPasient;
        this.reit = paramReit;
        pasient.leggTilResept(this);
    }

    public int hentId() {
        return id;
    }

    public Legemiddel hentLegemiddel() {
        return reseptLegemiddel;
    }

    public Lege hentLege() {
        return reseptLege;
    }

    public Pasient hentPasient() {
        return pasient;
    }

    public int hentReit() {
        return reit;
    }

    public boolean bruk() {
        if (reit > 0) {
            reit = reit - 1;
            return true;
        }
        return false;
    }

    abstract public String farge();

    abstract public int prisAaBetale();
}