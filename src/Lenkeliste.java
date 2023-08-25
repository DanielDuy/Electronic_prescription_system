import java.util.Iterator;

abstract class Lenkeliste<E> implements Liste<E> {

    class LenkelisteIterator implements Iterator {

        Node tempNode = hode;

        @Override
        public boolean hasNext(){
            return tempNode.nesteNode != null;
        }

        @Override
        public E next() {
            if (tempNode == hode) {
                tempNode = tempNode.nesteNode;
                return hode.returnerInnhold();
            } else {
                tempNode = tempNode.nesteNode;
                return tempNode.returnerInnhold();
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LenkelisteIterator();
    }

    class Node {

        private E innhold;
        public Node nesteNode;
        public Node forrigeNode;

        public Node(E paramInnhold) {
            innhold = paramInnhold;
        }

        public E returnerInnhold() {
            return innhold;
        }

        public void settNesteNode(Node paramNesteNode) {
            nesteNode = paramNesteNode;
        }

        public void settForrigeNode(Node paramForrigeNode) {
            forrigeNode = paramForrigeNode;
        }

    }

    public Node hode;
    public Node hale;
    public int storrelse = 0;

    public int stoerrelse () {
        return storrelse;
    }

    public void leggTil (E x){

        if (storrelse == 0) {
            hode = new Node(x);
            storrelse += 1;

        } else if (storrelse == 1) {
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

    }

    public E hent () {
        return hode.innhold;
    }

    public E fjern () {

        if (stoerrelse() == 0) {
            throw new UgyldigListeindeks(-1);

        } else if (stoerrelse() == 1) {
            E returNodeInnhold = hent();
            hode = null;
            storrelse = storrelse-1;
            return returNodeInnhold;

        } else {
            Node hodeNesteNode = hode.nesteNode;
            E returNodeInnhold = hent();
            hode.settNesteNode(null);
            hodeNesteNode.settForrigeNode(null);
            hode = hodeNesteNode;
            storrelse = storrelse-1;
            return returNodeInnhold;
        }

    }

    public String toString() {
        String returString = "{";
        Node tempNode = hode;

        for (int i = 0; i < stoerrelse(); i++) {
            returString += tempNode.returnerInnhold();
            tempNode = tempNode.nesteNode;
            if (i != stoerrelse()-1) {
                returString += ", ";
            }
        }
        returString += "}";
        return returString;
    }

}