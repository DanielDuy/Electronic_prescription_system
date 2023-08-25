class Prioritetskoe<E extends  Comparable<E>> extends Lenkeliste<E>{
    @Override
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

        if (stoerrelse() > 1) {
            Node tempNode = hale;
            Node tempForrigeNode;

            for (int i = stoerrelse()-1; 0 < i; i--) {
                if (tempNode.returnerInnhold().compareTo(tempNode.forrigeNode.returnerInnhold()) < 0) {

                    tempForrigeNode = tempNode.forrigeNode;

                    if (i == stoerrelse()-1) {
                        tempForrigeNode.settNesteNode(null);
                        hale = tempForrigeNode;

                    } else {
                        tempForrigeNode.settNesteNode(tempNode.nesteNode);
                    }

                    if (i == 1) {
                        tempNode.settForrigeNode(null);
                        hode = tempNode;
                    } else {
                        tempNode.settForrigeNode(tempForrigeNode.forrigeNode);
                    }

                    tempForrigeNode.settForrigeNode(tempNode);
                    tempNode.settNesteNode(tempForrigeNode);

                }
            }
        }



    }
}
