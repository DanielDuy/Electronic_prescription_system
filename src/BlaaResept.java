public class BlaaResept extends Resept {
    // alle blå resept minus 75% av orginal pris, bruk (int)Math.round()
    public BlaaResept(Legemiddel paramReseptLegemiddel, Lege paramReseptLege, Pasient paramPasient, int paramReit) {
        super(paramReseptLegemiddel, paramReseptLege, paramPasient, paramReit);
    }

    public String farge() {
        return "Blaa";
    }

    public int prisAaBetale() {
        return (int) Math.round(reseptLegemiddel.hentPris() * 0.25);
    }
}
