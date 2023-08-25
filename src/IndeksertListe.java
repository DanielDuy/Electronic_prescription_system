class IndeksertListe<E> extends Lenkeliste<E> {

    public void leggTil(int pos, E x) {
        if (pos == 0) {

            if (storrelse == 0) {
                hode = new Node(x);
                storrelse += 1;

            } else if (storrelse == 1) {
                hale = hode;
                hode = new Node(x);
                hode.settNesteNode(hale);
                hale.settForrigeNode(hode);
                storrelse += 1;

            } else {
                Node nyNode = new Node(x);
                nyNode.settNesteNode(hode);
                hode.settForrigeNode(nyNode);
                hode = nyNode;
                storrelse += 1;
            }

        } else if (pos == storrelse) {

            if (storrelse == 1) {
                hale = new Node(x);
                hode.settNesteNode(hale);
                hale.settForrigeNode(hode);
                storrelse += 1;

            } else {
                Node nyNode = new Node(x);
                hale.settNesteNode(nyNode);
                nyNode.settForrigeNode(hale);
                hale = nyNode;
                storrelse += 1;
            }

        } else if (0 < pos && pos < storrelse){

            Node nyNode = new Node(x);
            Node tempNode = hode;
            Node tempForrigeNode;
            for (int i = 0; i <= pos; i++) {
                if (i == pos) {
                    tempForrigeNode = tempNode.forrigeNode;
                    tempForrigeNode.settNesteNode(nyNode);
                    nyNode.settForrigeNode(tempForrigeNode);
                    nyNode.settNesteNode(tempNode);
                    tempNode.settForrigeNode(nyNode);
                    storrelse += 1;
                } else {
                    tempNode = tempNode.nesteNode;
                }
            }

        } else {

            throw new UgyldigListeindeks(pos);

        }
    }

    public void sett(int pos, E x) {
    if (pos == 0) {

            Node nyNode = new Node(x);

            if (stoerrelse() == 0 || stoerrelse() == 1) {
                hode = nyNode;
            } else if (stoerrelse() == 2) {
                hode = nyNode;
                hode.settNesteNode(hale);
                hale.settForrigeNode(hode);
            } else {
                Node nesteNode = hode.nesteNode;
                hode = nyNode;
                hode.settNesteNode(nesteNode);
                nesteNode.settForrigeNode(hode);
            }

        } else if (pos == storrelse-1) {

            Node nyNode = new Node(x);

            if (stoerrelse() == 2) {
                hale = nyNode;
                hode.settNesteNode(hale);
                hale.settForrigeNode(hode);
            } else {
                Node forrigeNode = hale.forrigeNode;
                hale = nyNode;
                hale.settForrigeNode(forrigeNode);
                forrigeNode.settNesteNode(hale);
            }

        } else if ((0 < pos) && (pos < stoerrelse()-1)) {

            Node nyNode = new Node(x);

            if (stoerrelse() == 3) {
                hode.settNesteNode(nyNode);
                nyNode.settForrigeNode(hode);
                nyNode.settNesteNode(hale);
                hale.settForrigeNode(nyNode);
            } else {


                Node tempNode = hode;
                for (int i = 0; i <= pos; i++) {
                    if (i == pos) {
                        nyNode.settForrigeNode(tempNode.forrigeNode);
                        tempNode.forrigeNode.settNesteNode(nyNode);
                        nyNode.settNesteNode(tempNode.nesteNode);
                        tempNode.nesteNode.settForrigeNode(nyNode);
                        tempNode.settNesteNode(null);
                        tempNode.settForrigeNode(null);
                    } else {
                        tempNode = tempNode.nesteNode;
                    }
                }


            }

        } else {

            throw new UgyldigListeindeks(pos);

        }
    }

    public E hent(int pos) {

        Node tempNode = hode;

        if (0 <= pos && pos < stoerrelse()) {

            for (int i = 0; i <= pos; i++) {
                if (i == pos) {
                    return tempNode.returnerInnhold();
                } else {
                    tempNode = tempNode.nesteNode;
                }
            }

        } else {

            throw new UgyldigListeindeks(pos);

        }

        return null;
    }

    public E fjern(int pos) {

        Node tempNode = hode;
        E tempInnhold;

        if (stoerrelse() == 1 && 0 <= pos && pos < stoerrelse()) {
            tempInnhold = hode.returnerInnhold();
            hode = null;
            storrelse = storrelse - 1;
            return tempInnhold;


        } else if (stoerrelse() == 2 && 0 <= pos && pos < stoerrelse()) {
            if (pos == 0) {
                tempInnhold = hode.returnerInnhold();
                hode.settNesteNode(null);
                hale.settForrigeNode(null);
                hode = hale;
                storrelse = storrelse - 1;
                return tempInnhold;
            } else if (pos == 1) {
                tempInnhold = hale.returnerInnhold();
                hode.settNesteNode(null);
                hale.settForrigeNode(null);
                hale = null;
                storrelse = storrelse - 1;
                return tempInnhold;
            }


        } else if (0 <= pos && pos < stoerrelse()) {
            if (pos == 0) {
                Node tempNode2 = hode;
                tempNode = hode.nesteNode;
                hode.nesteNode.settForrigeNode(null);
                hode.settNesteNode(null);
                hode = tempNode;
                storrelse = storrelse - 1;
                return tempNode2.returnerInnhold();

            } else if (pos == stoerrelse()-1) {
                Node tempNode2 = hale;
                tempNode = hale.forrigeNode;
                tempNode.settNesteNode(null);
                hale.settForrigeNode(null);
                hale = tempNode;
                storrelse = storrelse - 1;
                return tempNode2.returnerInnhold();

            } else {
                for (int i = 0; i <= pos; i++) {
                    if (i == pos) {
                        tempNode.forrigeNode.settNesteNode(tempNode.nesteNode);
                        tempNode.nesteNode.settForrigeNode(tempNode.forrigeNode);
                        storrelse = storrelse - 1;
                        return tempNode.returnerInnhold();
                    } else {
                        tempNode = tempNode.nesteNode;
                    }
                }
            }

        } else {

            throw new UgyldigListeindeks(pos);

        }

        return null;
    }

}
