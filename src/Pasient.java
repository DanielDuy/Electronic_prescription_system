class Pasient {
    private static int idSetter = 0;
    final int id;
    String navn;
    String fodselsnummer;
    IndeksertListe<Resept> pasientensResepter = new IndeksertListe<>();


    public Pasient(String paramNavn, String paramFodselsnummer) {
        id = idSetter;
        idSetter += 1;
        navn = paramNavn;
        fodselsnummer = paramFodselsnummer;
    }

    public void leggTilResept(Resept paramResept) {
        pasientensResepter.leggTil(paramResept);
    }

}
