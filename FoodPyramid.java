/**
 * A driver class that also contains an OrganismTree member variable that serves as the
 * tree for the user to interact with.
 *
 * @author
 * Nicole Niemiec
 * CSE 214 R08 FELIX
 * NOVEMBER 5TH, HW #5
 *
 * @version 1
 */
import java.util.Scanner;

public class FoodPyramid {

    private OrganismTree tree;

    private static Scanner input = new Scanner(System.in);
    public static FoodPyramid pyramid;

    /**
     * Default constructor for a FoodPyramid object.
     */
    public FoodPyramid(){

    }

    /**
     * Constructor for a FoodPyramid object that takes in one parameter to declare the root.
     * @param root
     *      The root of the tree.
     */
    public FoodPyramid(OrganismNode root){

        tree = new OrganismTree(root);
    }

    /**
     * Accessor method for the tree variable within the FoodPyramid.
     * @return
     */
    public OrganismTree getTree() {
        return tree;
    }

    /**
     * The main driver method.
     * @param args
     *      Where the program runs.
     */
    public static void main(String[] args){

        String type = "";
        String name = "";
        OrganismNode apexPred = new OrganismNode();

        System.out.println("What is the name of the Apex Predator?");
        name = input.nextLine();
        System.out.println("Is the organism a herbivore, a carnivore, or an omnivore? (H / C / O)");
        type = input.nextLine().toUpperCase();

        boolean valid = false;

        do{

        switch (type) {
            case "O":
                apexPred = new OrganismNode(name, true, true);
                valid = true;
                break;
            case "C":
                apexPred = new OrganismNode(name, false, true);
                valid = true;
                break;
            case "H":
                apexPred = new OrganismNode(name, true, false);
                valid = true;
                break;
            default:
                valid = false;
                System.out.println("Invalid argument.");
                System.out.println("Is the organism a herbivore, a carnivore, or an omnivore? (H / C / O)");
                type = input.nextLine().toUpperCase();

            }
        } while(!valid);


        pyramid = new FoodPyramid(apexPred);

        main_menu();

    }

    public static void main_menu(){

        String command = "";

        do {

            System.out.println("Main Menu:");
            System.out.println("(PC) \t Create New Plant Child");
            System.out.println("(AC) \t Create New Animal Child");
            System.out.println("(RC) \t Remove Child");
            System.out.println("(P) \t Print Out Cursor's Prey");
            System.out.println("(C) \t Print Out Food Chain");
            System.out.println("(F) \t Print Out Food Pyramid At Cursor");
            System.out.println("(LP) \t List All Plants Supporting Cursor");
            System.out.println("(R) \t Reset Cursor to Root");
            System.out.println("(M) \t Move Cursor to Child");
            System.out.println("(Q) \t Quit");

            System.out.println("Please enter a command: ");
            command = input.nextLine().toUpperCase();

            switch (command){
                case "PC": main_newPlantChild();
                    break;
                case "AC": main_newAnimalChild();
                    break;
                case "RC": main_removeChild();
                    break;
                case "P": main_printCursorPrey();
                    break;
                case "C": main_printFoodChain();
                    break;
                case "F": main_printChainAtCursor();
                    break;
                case "LP": main_listPlants();
                    break;
                case "R": main_resetCursor();
                    break;
                case "M": main_moveCursor();
                    break;
                case "Q": System.out.println("Program terminating....");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }


        }while(!command.equalsIgnoreCase("Q"));

    }

    public static void main_newPlantChild(){

        if(pyramid.getTree().getCursor().isPlant()){
            System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators.");
        }
        else {

            String name = "";
            System.out.println("What is the name of the organism?: ");
            name = input.nextLine();

            try {
                pyramid.getTree().addPlantChild(name);
                System.out.println(name + " has successfully been added as prey for the " +
                        pyramid.getTree().getCursor().getName());
            } catch (PositionNotAvailableException ex) {
                System.out.println("Full at cursor.");
            } catch (IllegalArgumentException ex) {
                System.out.println("Siblings cannot have the same name.");
            } catch (DietMismatchException ex) {
                System.out.println("Does not correspond to the correct diet.");
            }
        }

    }

    public static void main_newAnimalChild(){

        if(pyramid.getTree().getCursor().isPlant()){
            System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator.");
        }
        else {

            String name = "";
            String type = "";
            OrganismNode newAnimal;

            System.out.println("What is the name of the organism?: ");
            name = input.nextLine();
            System.out.println("Is the organism a herbivore, a carnivore, or an omnivore? (H / C / O)");
            type = input.nextLine().toUpperCase();

            switch (type) {
                case "O":
                    newAnimal = new OrganismNode(name, true, true);
                    break;
                case "C":
                    newAnimal = new OrganismNode(name, false, true);
                    break;
                case "H":
                    newAnimal = new OrganismNode(name, true, false);
                    break;
                default:
                    newAnimal = new OrganismNode();
            }

            try {
                pyramid.getTree().getCursor().addPrey(newAnimal);
                System.out.println(newAnimal.getName() + "has successfully been added as prey for the "
                        + pyramid.getTree().getCursor().getName());
            } catch (PositionNotAvailableException ex) {
                System.out.println("Full at cursor.");
            } catch (IsPlantException ex) {
                System.out.println("Plants cannot have prey.");
            } catch (DietMismatchException ex) {
                System.out.println("Does not correspond to the correct diet.");
            }

        }

    }

    public static void main_removeChild(){

        String name = "";
        System.out.println("What is the name of the organism to be removed?: ");
        name = input.nextLine();

        try {
            //pyramid.getTree().moveCursor(name);
            pyramid.getTree().removeChild(name);
            System.out.println("A(n) " + name + " has been successfully removed as prey for the bald eagle.");
        }catch(IllegalArgumentException ex){
            System.out.println("ERROR: This prey does not exist for this predator.");
        }

    }

    public static void main_printCursorPrey(){

        try{
            System.out.println(pyramid.getTree().listPrey());
        } catch (IsPlantException ex){
            System.out.println("Plants cannot have prey. No prey to list.");
        }

    }

    public static void main_printFoodChain(){

        System.out.println(pyramid.getTree().listFoodChain());

    }

    public static void main_printChainAtCursor(){

        pyramid.getTree().printOrganismTree();

    }

    public static void main_listPlants(){

        OrganismNode currentCursor = pyramid.getTree().getCursor();
        pyramid.getTree().cursorReset();
        String plants = pyramid.getTree().listAllPlants();
        plants = plants.replaceAll(", $", "");
        System.out.println(plants);

        pyramid.getTree().setCursor(currentCursor);

    }

    public static void main_resetCursor(){

        pyramid.getTree().cursorReset();
        System.out.println("Cursor successfully reset to root!");

    }

    public static void main_moveCursor(){

        String name = "";

        System.out.println("Move to?: ");
        name = input.nextLine();

        try{
            pyramid.getTree().moveCursor(name);
            System.out.println("Cursor successfully moved to " + name + "!");
        } catch(IllegalArgumentException ex){
            System.out.println("No child exists with such name.");
        }

    }
}
