/**
 * An exception class that is thrown when the prey does not match the
 * diet of its parent node.
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

public class DietMismatchException extends Exception {

    private String note = "";

    /**
     * Constructor that creates a DietMismatchException with one parameter.
     * @param note
     *      Message to print out.
     */
    public DietMismatchException(String note){

        this.note = note;

    }
}
