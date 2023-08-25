public class MilResept extends HvitResept {
    //pris er 0 kr
    public MilResept(Legemiddel paramReseptLegemiddel, Lege paramReseptLege, Pasient paramPasient) {
        super(paramReseptLegemiddel, paramReseptLege, paramPasient, 3);
    }

    @Override
    public int prisAaBetale() {
        return 0;
    }
}
