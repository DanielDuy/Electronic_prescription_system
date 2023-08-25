public class HvitResept extends Resept {
    public HvitResept(Legemiddel paramReseptLegemiddel, Lege paramReseptLege, Pasient paramPasient, int paramReit) {
        super(paramReseptLegemiddel, paramReseptLege, paramPasient, paramReit);
    }

    public String farge() {
        return "Hvit";
    }

    public int prisAaBetale() {
        return reseptLegemiddel.hentPris();
    }
}
