class Stabel<E> extends Lenkeliste<E> {
    @Override
    public void leggTil (E x){

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

    }
}
