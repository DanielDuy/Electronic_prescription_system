public class VanedannedeLegemiddel extends Legemiddel{
    int antallStyrkeVandedannede;
    public VanedannedeLegemiddel(String paramNavn, int paramPris, double paramAntallMgVirkestoff, int paramASVandedannede) {
        super(paramNavn, paramPris, paramAntallMgVirkestoff);
        this.antallStyrkeVandedannede = paramASVandedannede;
    }
}
