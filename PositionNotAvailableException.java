
/**
 * An exception class when all the references -- left, middle, and right -- are all full.
 *
 * @author
 * Nicole Niemiec
 * #112039349
 * nicole.niemiec@stonybrook.edu
 * CSE 214 R08 FELIX
 * NOVEMBER 5TH, HW #5
 *
 * @version 1
 */
public class PositionNotAvailableException extends Exception {

    private String note = "";

    /**
     * Constructor that creates a PositionNotAvailableException with one parameter.
     * @param note
     *      Message to print out.
     */
    public PositionNotAvailableException(String note){

        this.note = note;

    }
}
