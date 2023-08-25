public class Spesialist extends Lege implements Godkjenningsfritak {
    String kontrollkode;
    public Spesialist(String paramNavn, String paramKontrollkode) {
        super(paramNavn);
        kontrollkode = paramKontrollkode;
    }

    @Override
    public String hentKontrollkode() {
        return kontrollkode;
    }

    @Override
    public BlaaResept skrivBlaaResept (Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        BlaaResept nyBlaaResept = new BlaaResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(nyBlaaResept);
        if (legemiddel instanceof NarkotiskLegemiddel) {
            antUtskrevneNarkoResept += 1;
        }
        return nyBlaaResept;
    }
}
