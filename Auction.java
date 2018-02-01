import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }

    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    highestBid.getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        int contador = 0;
        boolean coincidencia = false;
        Lot selectedLot = null;

        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            while(contador < lots.size() && coincidencia == false) {
                if(lots.get(contador).getNumber() == lotNumber) {
                    selectedLot = lots.get(contador);
                    coincidencia = true;
                }
                contador += 1;
            }
            // Include a confidence check to be sure we have the
            // right lot.
            if(selectedLot.getNumber() != lotNumber) {
                System.out.println("Internal error: Lot number " +
                    selectedLot.getNumber() +
                    " was returned instead of " +
                    lotNumber);
                // Don't return an invalid lot.
                selectedLot = null;
            }
            return selectedLot;
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
            return null;
        }
    }

    /**
     * Muestra por pantalla los detalles de todos los lotes y
     * muestra el nombre de la persona con la puja más alta para
     * cada lote y su valor.
     */
    public void close() {
        for(Lot lote : lots) {
            System.out.println(lote.toString());
            if(lote.getHighestBid() != null) {
                System.out.println("Nombre de la persona con la puja mas alta: " + lote.getHighestBid().getBidder().getName() + " - su puja fue de: " + lote.getHighestBid().getValue());
            }
            else {
                System.out.println("No hay ninguna puja realizada para este lote.");
            }
        }
    }

    /**
     * Devuelve una lista con los lotes que
     * aun no han sido vendidos.
     */
    public ArrayList<Lot> getUnsold() {
        ArrayList<Lot> lotesNoVendidos = new ArrayList<Lot>();

        for(Lot lote : lots) {
            if(lote.getHighestBid() == null) {
                lotesNoVendidos.add(lote);
            }
        }

        return lotesNoVendidos;
    }

    /**
     * Eliminar el lote con el número de
     * lote especificado.
     * @param number El número del lote que hay que eliminar.
     * @return El lote con el número dado o null si
     * no existe tal lote.
     */
    public Lot removeLot(int number) {
        int contador = 0;
        boolean coincidencia = false;
        Lot lote = null;

        while(contador < lots.size() && coincidencia == false) {
            if(lots.get(contador).getNumber() == number) {
                lote = lots.get(contador);
                lots.remove(contador);
                coincidencia = true;
            }
            contador += 1;
        }

        return lote;
    }
}
