/**
 * An exception class that is thrown when the current node is a Plant.
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

public class IsPlantException extends Exception {

    private String note = "";

    /**
     * Constructor that creates a IsPlantException with one parameter.
     * @param note
     *      Message to print out.
     */
    public IsPlantException(String note){

        this.note = note;

    }
}
