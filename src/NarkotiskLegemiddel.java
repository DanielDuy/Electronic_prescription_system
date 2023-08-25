public class NarkotiskLegemiddel extends Legemiddel {
    int antallStyrkeNarkotisk;
    public NarkotiskLegemiddel(String paramNavn, int paramPris, double paramAntallMgVirkestoff, int paramASNarkotisk) {
        super(paramNavn, paramPris, paramAntallMgVirkestoff);
        this.antallStyrkeNarkotisk = paramASNarkotisk;
    }
}
