import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Legesystem {

    static IndeksertListe<Pasient> pasientListe = new IndeksertListe<>();
    static IndeksertListe<Lege> legeListe = new IndeksertListe<>();
    static IndeksertListe<Legemiddel> legemiddelListe = new IndeksertListe<>();
    static IndeksertListe<Resept> reseptListe = new IndeksertListe<>();

    static int antUtskrevneReseptVanedan = 0;
    static int antUtskrevneReseptNarko = 0;



    public static void lesObjekterFraFil(String filnavn) {
        String data;
        int dataType = 0;
        try {
            File nyFil = new File(filnavn);
            Scanner filLeser = new Scanner(nyFil);
            while (filLeser.hasNextLine()) {
                data = filLeser.nextLine();
                if (data.contains("#")) {
                    dataType += 1;
                    data = filLeser.nextLine();
                }
                if (dataType == 1) {
                    try {
                        opprettNyPasient(data);
                    } catch (Exception ignored) {}
                } else if (dataType == 2) {
                    try {
                        opprettNyLegemiddel(data);
                    } catch (Exception ignored) {}
                } else if (dataType == 3) {
                    try {
                        opprettNyLege(data);
                    } catch (Exception ignored) {}
                } else if (dataType == 4) {
                    try {
                        opprettNyResept(data);
                    } catch (Exception ignored) {}
                }
            }
            dataType = 0;
            filLeser.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke fil med dette filnavnet!");
            e.printStackTrace();
        }


    }


    public static void opprettNyPasient(String paramData) {
        IndeksertListe<String> data = new IndeksertListe<>();
        StringBuilder stringBygger = new StringBuilder();
        for (int i = 0; i < paramData.length(); i++) {
            if (Objects.equals(paramData.charAt(i), ',')) {
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else if (i == paramData.length()-1) {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
            }
        }
        Pasient nyPasient = new Pasient(data.hent(0), data.hent(1));
        pasientListe.leggTil(nyPasient);
    }


    public static void opprettNyLegemiddel(String paramData) {
        IndeksertListe<String> data = new IndeksertListe<>();
        StringBuilder stringBygger = new StringBuilder();
        for (int i = 0; i < paramData.length(); i++) {

            if (Objects.equals(paramData.charAt(i), ',')) {
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else if (i == paramData.length()-1) {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
            }
        }
        if (Objects.equals(data.hent(1), "vanlig")) {
            VanligLegemiddel nyVanligLegemiddel = new VanligLegemiddel(data.hent(0), Integer.parseInt(data.hent(2)), Double.parseDouble(data.hent(3)));
            legemiddelListe.leggTil(nyVanligLegemiddel);
        } else if (Objects.equals(data.hent(1), "vanedannende")) { // navn,type,pris,virkestoff,[styrke]
            VanedannedeLegemiddel nyVandedannedeLegemiddel = new VanedannedeLegemiddel(data.hent(0), Integer.parseInt(data.hent(2)), Double.parseDouble(data.hent(2)), Integer.parseInt(data.hent(4)));
            legemiddelListe.leggTil(nyVandedannedeLegemiddel);
        } else if (Objects.equals(data.hent(1), "narkotisk")) {
            NarkotiskLegemiddel nyNarkotiskLegemiddel = new NarkotiskLegemiddel(data.hent(0), Integer.parseInt(data.hent(2)), Double.parseDouble(data.hent(3)), Integer.parseInt(data.hent(4)));
            legemiddelListe.leggTil(nyNarkotiskLegemiddel);
        } else {
            System.out.println("Feil format, prøv på nytt!");
        }
    }


    public static void opprettNyLege(String paramData) { // (navn,kontrollid / 0 hvis vanlig lege)
        IndeksertListe<String> data = new IndeksertListe<>();
        StringBuilder stringBygger = new StringBuilder();
        for (int i = 0; i < paramData.length(); i++) {
            if (Objects.equals(paramData.charAt(i), ',')) {
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else if (i == paramData.length()-1) {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
            }
        }
        if (Objects.equals(data.hent(1), "0")) {
            Lege nyLege = new Lege(data.hent(0));
            legeListe.leggTil(nyLege);
        } else {
            Spesialist nySpesialist = new Spesialist(data.hent(0), data.hent(1));
            legeListe.leggTil(nySpesialist);
        }
    }


    public static void opprettNyResept(String paramData) throws UlovligUtskrift { // (legemiddelNummer,legeNavn,pasientID,type,[reit])
        IndeksertListe<String> data = new IndeksertListe<>();

        StringBuilder stringBygger = new StringBuilder();
        for (int i = 0; i < paramData.length(); i++) {
            if (Objects.equals(paramData.charAt(i), ',')) {
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else if (i == paramData.length()-1) {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
                data.leggTil(stringBygger.toString());
                stringBygger = new StringBuilder();
            } else {
                stringBygger.append(String.valueOf(paramData.charAt(i)));
            }
        }

        Lege tempLege = legeListe.hent(0);
        Pasient tempPasient = pasientListe.hent(0);

        for (int i = 0; i < legeListe.stoerrelse(); i++) {
            if (Objects.equals(legeListe.hent(i).hentNavn(), data.hent(1))) {
                tempLege = legeListe.hent(i);
                break;
            }
        }

        for (int i = 0; i < pasientListe.stoerrelse(); i++) {
            if (pasientListe.hent(i).id == Integer.parseInt(data.hent(2))) {
                tempPasient = pasientListe.hent(i);
                break;
            }
        }

        if (legemiddelListe.hent(Integer.parseInt(data.hent(0))) instanceof VanedannedeLegemiddel) {
            antUtskrevneReseptVanedan += 1;
        } else if (legemiddelListe.hent(Integer.parseInt(data.hent(0))) instanceof NarkotiskLegemiddel) {
            antUtskrevneReseptNarko += 1;
        }

        switch (data.hent(3)) {
            case "hvit" ->
                    reseptListe.leggTil(tempLege.skrivHvitResept(legemiddelListe.hent(Integer.parseInt(data.hent(0))), tempPasient, Integer.parseInt(data.hent(4))));
            case "militaer" ->
                    reseptListe.leggTil(tempLege.skrivMilResept(legemiddelListe.hent(Integer.parseInt(data.hent(0))), tempPasient));
            case "p" ->
                    reseptListe.leggTil(tempLege.skrivPResept(legemiddelListe.hent(Integer.parseInt(data.hent(0))), tempPasient, Integer.parseInt(data.hent(4))));
            case "blaa" ->
                    reseptListe.leggTil(tempLege.skrivBlaaResept(legemiddelListe.hent(Integer.parseInt(data.hent(0))), tempPasient, Integer.parseInt(data.hent(4))));
        }
    }


    public static void main(String [] args) throws UlovligUtskrift {
        System.out.println("Tast inn: a, for å avslutte. s, for å vise alle elementene. t, for å legge til enten ny pasient, legemiddel, lege eller resept. b, for å bruke en resept. k, for å skrive ut statistikken.");
        System.out.print("Tast inn her: ");


        lesObjekterFraFil("legedata.txt");


        Scanner brukerInput = new Scanner(System.in);
        String input = brukerInput.nextLine();


        while (!Objects.equals(input, "a")) {


            if (Objects.equals(input, "s")) { // Vise alle elementer.


                System.out.println("\nPasienter: ");
                for (int i = 0; i < pasientListe.stoerrelse(); i++) {
                    Pasient p = pasientListe.hent(i);
                    System.out.println("- " + p.id + " , " + p.navn + " , " + p.fodselsnummer);
                }


                IndeksertListe<Lege> tempLegeListe = new IndeksertListe<>();
                for (int i = 0; i < legeListe.stoerrelse(); i++) {
                    tempLegeListe.leggTil(legeListe.hent(i));
                }


                for (int k = 0; k < tempLegeListe.stoerrelse() -1 ; k++) {
                    for (int l = 0; l < tempLegeListe.stoerrelse() - k - 1; l++) {
                        if (tempLegeListe.hent(l).compareTo(tempLegeListe.hent(l+1)) > 0) {
                            Lege tempLege = tempLegeListe.hent(l);
                            tempLegeListe.sett(l, tempLegeListe.hent(l+1));
                            tempLegeListe.sett(l+1, tempLege);
                        }
                    }
                }


                System.out.println("\nLeger: ");
                for (int k = 0; k < tempLegeListe.stoerrelse(); k++) {
                    Lege l = tempLegeListe.hent(k);
                    System.out.println("- "+l.legeNavn);
                }


                System.out.println("\nLegemidler: ");
                for (int i = 0; i < legemiddelListe.stoerrelse(); i++) {
                    Legemiddel l = legemiddelListe.hent(i);
                    System.out.println("- " + l.id + " , " + l.navn + " , " + l.pris + " , " + l.antallMgVirkestoff);
                }

                System.out.println("\nResepter: ");
                for (int i = 0; i < reseptListe.stoerrelse(); i++) {
                    Resept r = reseptListe.hent(i);
                    System.out.println("- " + r.id + " , " + r.reseptLegemiddel.navn + " , " + r.reseptLege.legeNavn + " , " + r.pasient.navn + " , " + r.reit);
                }


                System.out.println();
            }


            else if (Objects.equals(input, "t")) { // Legge til enten ny pasient, legemiddel, lege eller resept.


                System.out.println("\nTast inn, p for ny pasient, l for ny legemiddel, e for ny lege, r for ny resept.");
                System.out.print("Tast inn her: ");
                input = brukerInput.nextLine();


                if (Objects.equals(input, "p")) { // Ny pasient.


                    System.out.print("\nTast inn navnet for pasienten: ");
                    String nyPasintNavn = brukerInput.nextLine();


                    System.out.print("\nTast inn fødselsnummeret for pasienten: ");
                    String nyPasintFNr = brukerInput.nextLine();


                    try {
                        Long.parseLong(nyPasintFNr);
                        opprettNyPasient(nyPasintNavn+","+nyPasintFNr);
                        System.out.println("Vellykket opprettelse av en ny pasient!");
                    } catch (Exception e) {
                        System.out.println("Feil format, prøv på nytt!");
                    }


                } else if (Objects.equals(input, "l")) { // Ny legemiddel.


                    System.out.print("\nHva er navnet til legemidlet: ");
                    String nyLegemiddelNavn = brukerInput.nextLine();


                    System.out.print("\nHva er prisen for legemidlet: ");
                    String nyLegemiddelPris = brukerInput.nextLine();


                    System.out.print("\nHva er mengden virkestoff i mg for legemidlet: ");
                    String nyLegemiddelVirkestoff = brukerInput.nextLine();


                    System.out.println("Hva slags type er legemidlet?");
                    System.out.println("- 1, Vanlig\n- 2, Vanedannende\n- 3, Narkotisk");
                    System.out.print("\nTast inn et av nummer valgene her:");
                    String nyLegemiddelType = brukerInput.nextLine();
                    boolean gyldigType = false;


                    if (Objects.equals(nyLegemiddelType, "1")) {
                        nyLegemiddelType = "vanlig";
                        gyldigType = true;
                    } else if (Objects.equals(nyLegemiddelType, "2")) {
                        nyLegemiddelType = "vanedannende";
                        gyldigType = true;
                    } else if (Objects.equals(nyLegemiddelType, "3")) {
                        nyLegemiddelType = "narkotisk";
                        gyldigType = true;
                    } else {
                        System.out.println("Ugylid valg, prøv på nytt!");
                        gyldigType = false;
                    }


                    if (gyldigType) {
                        try {
                            Integer.parseInt(nyLegemiddelPris);
                            Double.parseDouble(nyLegemiddelVirkestoff);


                            if (nyLegemiddelType.equals("vanedannende")) {
                                System.out.print("\nTast inn styrke vanedannende for legemidlet: ");
                                String nyLegemiddelStyrke = brukerInput.nextLine();
                                opprettNyLegemiddel(nyLegemiddelNavn+","+nyLegemiddelType+","+nyLegemiddelPris+","+nyLegemiddelVirkestoff+","+nyLegemiddelStyrke);
                            } else if (nyLegemiddelType.equals("narkotisk")){
                                System.out.print("\nTast inn styrke narkotisk for legemidlet: ");
                                String nyLegemiddelStyrke = brukerInput.nextLine();
                                opprettNyLegemiddel(nyLegemiddelNavn+","+nyLegemiddelType+","+nyLegemiddelPris+","+nyLegemiddelVirkestoff+","+nyLegemiddelStyrke);
                            } else {
                                opprettNyLegemiddel(nyLegemiddelNavn+","+nyLegemiddelType+","+nyLegemiddelPris+","+nyLegemiddelVirkestoff);
                            }
                            System.out.println("Vellykket opprettelse av en ny legemiddel!");


                        } catch (Exception e) {
                            System.out.println("Feil format, prøv på nytt!");
                        }
                    }


                } else if (Objects.equals(input, "e")) { // Ny lege.


                    System.out.print("\nTast inn navnet til legen: ");
                    String nyLegeNavn = brukerInput.nextLine();


                    System.out.println("Er legen en spesialist? Tast inn \"Ja\" eller \"Nei\": ");
                    String nyLegeErSpesialist = brukerInput.nextLine();


                    String nyLegeKontrollkode = "0";
                    if (Objects.equals(nyLegeErSpesialist, "Ja") || Objects.equals(nyLegeErSpesialist, "ja")) {
                        System.out.print("\nTast inn kontrollkoden til spesialisten: ");
                        nyLegeKontrollkode = brukerInput.nextLine();
                    }


                    try {
                        Long.parseLong(nyLegeKontrollkode);
                        opprettNyLege("Dr. "+nyLegeNavn+","+nyLegeKontrollkode);
                        System.out.println("Vellykket opprettelse av en ny lege!");
                    } catch (Exception e) {
                        System.out.println("Feil format, prøv på nytt!");
                    }


                } else if (Objects.equals(input, "r")) { // Ny resept.


                    System.out.println("\nLegemidler: ");
                    for (int i = 0; i < legemiddelListe.stoerrelse(); i++) {
                        Legemiddel l = legemiddelListe.hent(i);
                        System.out.println("- " + l.id + " , " + l.navn + " , " + l.pris + " , " + l.antallMgVirkestoff);
                    }


                    System.out.print("Tast inn nummeret til legemidlet du vil opprette med resepten: ");
                    String nyReseptLegemiddel = brukerInput.nextLine();


                    System.out.println("\nLeger: ");
                    for (int k = 0; k < legeListe.stoerrelse(); k++) {
                        Lege l = legeListe.hent(k);
                        System.out.println("-"+k+" "+l.legeNavn);
                    }


                    System.out.print("\nTast inn nummeret til legen som skal utskrive resepten her: ");
                    String nyReseptLege = brukerInput.nextLine();


                    System.out.println("\nPasienter: ");
                    for (int i = 0; i < pasientListe.stoerrelse(); i++) {
                        Pasient p = pasientListe.hent(i);
                        System.out.println("- " + p.id + " , " + p.navn + " , " + p.fodselsnummer);
                    }


                    System.out.print("\nTast inn pasientID-en til den som skal få resepten her: ");
                    String nyReseptPasient = brukerInput.nextLine();


                    System.out.println("Hva slags type er resepten? Velg en av de 4 valgene av å taste inn nummeret til venstre for typen: \n1 - Hvit resept \n2 - Blaa resept \n3 - P resept \n4 - Militaer resept");
                    System.out.print("Tast inn valget ditt her: ");
                    String nyReseptType = brukerInput.nextLine();


                    if (Objects.equals(nyReseptType, "1")) {
                        nyReseptType = "hvit";
                    } else if (Objects.equals(nyReseptType, "2")) {
                        nyReseptType = "blaa";
                    } else if (Objects.equals(nyReseptType, "3")) {
                        nyReseptType = "p";
                    } else if (Objects.equals(nyReseptType, "4")) {
                        nyReseptType = "militaer";
                    }


                    String nyReseptReit = "";
                    if (!Objects.equals(nyReseptType, "militaer")) {
                        System.out.print("\nTast inn antall reit for resepten: ");
                        nyReseptReit = brukerInput.nextLine();
                    }


                    try {
                        for (int k = 0; k < legeListe.stoerrelse(); k++) {
                            Lege l = legeListe.hent(k);
                            if (Integer.parseInt(nyReseptLege) == k) {
                                nyReseptLege = l.legeNavn;
                            }
                        }
                        if (!Objects.equals(nyReseptType, "militaer")) {
                            opprettNyResept(nyReseptLegemiddel+","+nyReseptLege+","+nyReseptPasient+","+nyReseptType+","+nyReseptReit);
                        } else {
                            opprettNyResept(nyReseptLegemiddel+","+nyReseptLege+","+nyReseptPasient+","+nyReseptType);
                        }
                        System.out.println("Vellykket opprettelse av en ny resept!");
                    } catch (Exception e) {
                        System.out.println("Feil format, prøv på nytt!");
                    }
                } else {
                    System.out.println("Ugyldig input, prøv på nytt!");
                }
            }

            else if (Objects.equals(input, "b")) { // Bruke en resept.


                for (int i = 0; i < pasientListe.stoerrelse(); i++) {
                    Pasient p = pasientListe.hent(i);
                    System.out.println("- "+p.id + " " + p.navn + " " + p.fodselsnummer);
                }


                System.out.println("\nHvilken pasient vil du se resepter for?");
                System.out.print("Tast inn her: ");
                input = brukerInput.nextLine();


                while (Integer.parseInt(input) < 0 || pasientListe.stoerrelse()-1 < Integer.parseInt(input)) {
                    System.out.println("\nUgyldig valg, prøv på nytt.");
                    System.out.println("\nHvilken pasient vil du se resepter for?");
                    System.out.print("Tast inn her: ");
                    input = brukerInput.nextLine();
                }


                Pasient tempPasient = pasientListe.hent(Integer.parseInt(input));


                for (int i = 0; i < tempPasient.pasientensResepter.stoerrelse(); i++) {
                    Resept r = tempPasient.pasientensResepter.hent(i);
                    System.out.println(i + ", " + r.reseptLegemiddel.navn + ", " + r.reit + " reit");
                }


                System.out.println("\nHvilken resept vil du bruke?");
                System.out.print("Tast inn her: ");
                input = brukerInput.nextLine();


                while (Integer.parseInt(input) < 0 || tempPasient.pasientensResepter.stoerrelse()-1 < Integer.parseInt(input)) {
                    System.out.println("\nUgyldig valg, prøv på nytt.");
                    System.out.println("\nHvilken resept vil du bruke?");
                    System.out.print("Tast inn her: ");
                    input = brukerInput.nextLine();
                }


                for (int i = 0; i < tempPasient.pasientensResepter.stoerrelse(); i++) {
                    if (Integer.parseInt(input) == i) {
                        tempPasient.pasientensResepter.hent(i).bruk();
                        System.out.println(tempPasient.pasientensResepter.hent(i).hentReit() + " gjenværende reit.");
                        break;
                    }
                }


            } else if (Objects.equals(input, "k")) { // Vise statistikken.


                System.out.println("\nStatistikk over vanedannede og narkotiske resepter:");
                System.out.println("Antall utskrevne respter av vanedannende legemidler: "+antUtskrevneReseptVanedan);
                System.out.println("Antall utskrevne respter av narkotiske legemidler: "+antUtskrevneReseptNarko);


                IndeksertListe<Lege> tempLegeListe = new IndeksertListe<>();


                for (int i = 0; i < legeListe.stoerrelse(); i++) {
                    int antUtskrevneNarkoResept = 0;
                    if (legeListe.hent(i).utskrevneResepter.stoerrelse() != 0) {
                        for (int k = 0; k < legeListe.hent(i).utskrevneResepter.stoerrelse(); k++) {
                            if (legeListe.hent(i).utskrevneResepter.hent(k).reseptLegemiddel instanceof NarkotiskLegemiddel) {
                                antUtskrevneNarkoResept += 1;
                            }
                        }
                    }
                    if (antUtskrevneNarkoResept > 0) {
                        tempLegeListe.leggTil(legeListe.hent(i));
                    }
                }


                if (tempLegeListe.stoerrelse() > 1) {
                    for (int k = 0; k < tempLegeListe.stoerrelse()-1 ; k++) {
                        for (int l = 0; l < tempLegeListe.stoerrelse() - k - 1; l++) {
                            if (tempLegeListe.hent(l).compareTo(tempLegeListe.hent(l+1)) > 0) {
                                Lege tempLege = tempLegeListe.hent(l);
                                tempLegeListe.sett(l, tempLegeListe.hent(l+1));
                                tempLegeListe.sett(l+1, tempLege);
                            }
                        }
                    }
                }


                System.out.println("\nLeger som har utskrevet resepter av narkotiske legemidler: ");
                for (int k = 0; k < tempLegeListe.stoerrelse(); k++) {
                    int antUtskrevneNarkoResept = 0;
                        for (int i = 0; i < tempLegeListe.hent(k).utskrevneResepter.stoerrelse(); i++) {
                            if (tempLegeListe.hent(k).utskrevneResepter.hent(i).reseptLegemiddel instanceof NarkotiskLegemiddel) {
                                antUtskrevneNarkoResept += 1;
                            }
                    }
                    System.out.println("- Navn: "+tempLegeListe.hent(k).hentNavn()+", antall: "+antUtskrevneNarkoResept);
                }


                System.out.println("\nPasienter som har gyldig resept på narkotiske legemidler:  ");
                for (int k = 0; k < pasientListe.stoerrelse(); k++) {


                    int antallGyldigeNarkoResept = 0;
                    for (int i = 0; i < pasientListe.hent(k).pasientensResepter.stoerrelse(); i++) {
                        Resept s = pasientListe.hent(k).pasientensResepter.hent(i);
                        if (s.reseptLegemiddel instanceof NarkotiskLegemiddel) {
                            if (s.reit > 0) {
                                antallGyldigeNarkoResept += 1;
                            }
                        }
                    }

                    if (antallGyldigeNarkoResept > 0) {
                        System.out.println("- Navn: "+pasientListe.hent(k).navn + ", Antall gyldige resepter: "+antallGyldigeNarkoResept);
                    }
                }
            }

            else {
                System.out.println("\nUgyldig input, prøv på nytt!");
            }

            System.out.println("\nTast inn: a, for å avslutte. s, for å vise alle elementene. t, for å legge til enten ny pasient, legemiddel, lege eller resept. b, for å bruke en resept. k, for å skrive ut statistikken.");
            System.out.print("Tast inn her: ");
            input = brukerInput.nextLine();
        }
    }
}