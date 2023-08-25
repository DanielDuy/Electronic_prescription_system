abstract public class Legemiddel {
    public final String navn;
    public final int id;
    public int pris;
    public final double antallMgVirkestoff;
    private static int teller = 0;

    public Legemiddel(String paramNavn, int paramPris, double paramAntallMgVirkestoff) {
        this.navn = paramNavn;
        this.id = teller;
        teller = teller + 1;
        this.pris = paramPris;
        this.antallMgVirkestoff = paramAntallMgVirkestoff;
    }

    public String hentNavn() {
        return navn;
    }

    public int hentPris() {
        return pris;
    }

    public void settNyPris(int paramNyPris) {
        pris = paramNyPris;
    }
}
