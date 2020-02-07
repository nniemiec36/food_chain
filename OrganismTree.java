/**
 * A class that implements the ternary tree of OrganismNode objects,
 * and allows various operations like insertion, removal, printing, moving the cursor, etc.
 * This class contains references to the cursor and the root.
 *
 * @author
 * Nicole Niemiec
 * CSE 214 R08 FELIX
 * NOVEMBER 5TH, HW #5
 *
 * @version 1
 */
public class OrganismTree {

    private OrganismNode root;
    private OrganismNode cursor;

    /**
     * An accessor method that returns the root of the tree.
     * @return
     *      Returns the root node of the tree.
     */
    public OrganismNode getRoot() {
        return root;
    }

    /**
     * An accessor method that returns the current cursor.
     * @return
     *      Returns the node at the cursor.
     */
    public OrganismNode getCursor() {
        return cursor;
    }

    /**
     * Constructor that sets the apexPredator.
     * @param apexPredator
     */
    public OrganismTree(OrganismNode apexPredator){

        root = apexPredator;
        cursor = apexPredator;

    }

    /**
     * Resets the cursor to the root.
     */
    public void cursorReset(){

        cursor = root;
    }

    /**
     * Method to move the cursor to a certain name underneath the children.
     * @param name
     *      Name of cursor to be set to.
     * @throws IllegalArgumentException
     *      Throws an IllegalArgumentException if the name doesn't match the name of one of the children.
     */
    public void moveCursor(String name) throws IllegalArgumentException{

        if(cursor.getLeft() == null && cursor.getMiddle() == null && cursor.getRight() == null)
            throw new IllegalArgumentException("Children are null.");
        if(cursor.getLeft() != null && cursor.getLeft().getName().equals(name))
            cursor = cursor.getLeft();
        else if(cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name))
            cursor = cursor.getMiddle();
        else if(cursor.getRight() != null && cursor.getRight().getName().equals(name))
            cursor = cursor.getRight();
        else
            throw new IllegalArgumentException("ERROR: This prey does not exist for this predator.");


    }

    /**
     * A method that returns a String indicating the organism at cursor and all its possible prey.
     * @return
     *      A String consisting of the organism at cursor and all its possible prey
     * @throws IsPlantException
     *      Throws an IsPlantException if the current cursor is a plant.
     */
    public String listPrey() throws IsPlantException{

        String prey = "";

        if(cursor.isPlant())
            throw new IsPlantException("Plants cannot have prey.");

        System.out.print(cursor.getName() + "--> ");

        if(cursor.getLeft() != null) {
            prey += cursor.getLeft().getName();
            if(cursor.getMiddle() != null){
                prey += ", ";
            }
        }

        if(cursor.getMiddle() != null){
            prey += cursor.getMiddle().getName();
            if(cursor.getRight() != null){
                prey += ", ";
            }
        }

        if(cursor.getRight() != null){
            prey += cursor.getRight().getName();
        }

        return prey;
    }

    /**
     * A method which returns a String containing the path of organisms that lead
     * from the apex predator to the cursor.
     * @return
     *      A String containing the food chain from the apex predator to the cursor.
     */
    public String listFoodChain(){

        OrganismNode[] path = new OrganismNode[1000];
        boolean found = false;
//        OrganismNode[] finalPath = new OrganismNode[1000];

        try{
            path = cursor.findFoodPath(path, root, cursor, 0);
        }catch(Exception ex)
        {
            System.out.print("");
        }


        int count = 0;

        String pathString = "";

        while(path[count] != null && !found){

            if(path[count].getName().equals(cursor.getName()))
                found = true;
            pathString += path[count].getName();
            if(path[count + 1] != null && !found)
                pathString += " -> ";
            count++;
        }

    return pathString;

    }

    /**
     * Prints a layered, indented tree by performing a preorder traversal
     * starting at the current cursor node.
     */
    public void printOrganismTree(){

        cursor.preorder(0, cursor);

    }

    /**
     * A method that returns all the leaf nodes only if they're plants.
     * @return
     *      Returns all the plants in the leaf nodes.
     */
    public String listAllPlants(){

        if(cursor == null)
            return "";

        if(cursor.isPlant() && cursor.getRight() == null
                && cursor.getLeft() == null && cursor.getMiddle() == null) {
            return cursor.getName() + ", ";
        }

        return listAllPlants(cursor.getLeft()) +
                listAllPlants(cursor.getMiddle()) +
                listAllPlants(cursor.getRight());
    }

    /**
     * Helper method to listAllPlants.
     * @param root
     *      Staring node to search.
     * @return
     *      Returns all the leaf nodes which are plants.
     */
    public String listAllPlants(OrganismNode root){

        if(root == null)
            return "";
        if(root.isPlant() && root.getRight() == null
                && root.getLeft() == null && root.getMiddle() == null) {
            return root.getName() + ", ";
        }

        return listAllPlants(root.getLeft()) + listAllPlants(root.getMiddle())
                + listAllPlants(root.getRight());

    }
    /**
     * A method that adds an Animal Child to the current node at the cursor.
     * @param name
     *      The name of the animal being added.
     * @param isHerbivore
     *      Describes if the animal is a herbivore or not.
     * @param isCarnivore
     *      Describes if the animal is a carnivore or not.
     * @throws IllegalArgumentException
     *      Throws an IllegalArgumentException if the child's name is the same as it's soon to be sibling.
     * @throws PositionNotAvailableException
     *      Throws a PositionNotAvailableException if there is no room for the new child.
     * @throws DietMismatchException
     *      Throws a DietMismatchException if the child does not correspond with the parent's diet.
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore)
    throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException {

        if(cursor.getLeft().getName().equals(name) || cursor.getMiddle().getName().equals(name)
                || cursor.getRight().getName().equals(name))
            throw new IllegalArgumentException("ERROR: This prey already exists for this predator.");
        if(cursor.getLeft() != null && cursor.getRight() != null & cursor.getMiddle() != null)
            throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.");
        if(!cursor.isCarnivore())
            throw new DietMismatchException("Parent eats plants.");

        OrganismNode newAnimal = new OrganismNode(name, isHerbivore, isCarnivore);

        try{
            cursor.addPrey(newAnimal);
        } catch(IsPlantException ex){
            System.out.println("Plants cannot have prey.");
        } catch(DietMismatchException ex){
            System.out.println("Does not correspond to the correct diet.");
        }

    }

    /**
     * A method that adds a Plant Child to the current node at the cursor.
     * @param name
     *      The name of the plant being added.
     * @throws IllegalArgumentException
     *      Thrown if the child's name is the same as its soon to be sibling.
     * @throws PositionNotAvailableException
     *      Thrown if there is no position available for the new child.
     * @throws DietMismatchException
     *      Thrown if the child does not correspond with its parent's diet.
     */
    public void addPlantChild(String name) throws IllegalArgumentException,
            PositionNotAvailableException, DietMismatchException {

        //System.out.println(cursor.getName());

        if((cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) ||
                (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name))
                || (cursor.getRight() != null && cursor.getRight().getName().equals(name)))
            throw new IllegalArgumentException("ERROR: This prey already exists for this predator.");
        if(cursor.getLeft() != null && cursor.getRight() != null & cursor.getMiddle() != null){
                throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.");
        }
        if(!cursor.isHerbivore())
            throw new DietMismatchException("Parent is carnivore.");


        OrganismNode newPlant = new OrganismNode(name, true);
        System.out.println(cursor.getName());

        if(cursor.getLeft() == null) {
            System.out.println(cursor.getLeft());
            cursor.setLeft(newPlant);
            System.out.println(cursor.getLeft());
        }
        else if(cursor.getMiddle() == null)
            cursor.setMiddle(newPlant);
        else if(cursor.getRight() == null)
            cursor.setRight(newPlant);

    }

    /**
     * Mutator method to set the cursor to another node.
     * @param cursor
     *      Cursor to be set to.
     */
    public void setCursor(OrganismNode cursor) {
        this.cursor = cursor;
    }

    /**
     * Method to remove a child that matches the given name at the current cursor level.
     * @param name
     *      Name of child to remove.
     * @throws IllegalArgumentException
     *      Throws an IllegalArgumentException if the name does not match any of the cursor's children.
     */
    public void removeChild(String name) throws IllegalArgumentException {

        if(cursor.getRight().getName().equals(name))
            cursor.setRight(null);
        else if(cursor.getMiddle().getName().equals(name)) {
            cursor.setMiddle(null);
            if(cursor.getRight() != null){
                /*if(cursor.getLeft() == null){
                    cursor.setLeft(cursor.getRight());
                    cursor.setRight(null);
                }*/
                cursor.setMiddle(cursor.getRight());
                cursor.setRight(null);
            }
        }
        else if(cursor.getLeft().getName().equals(name)) {
            cursor.setLeft(null);
            if(cursor.getMiddle() != null){
                cursor.setLeft(cursor.getMiddle());
                cursor.setMiddle(null);
                if(cursor.getRight() != null){
                    cursor.setMiddle(cursor.getRight());
                    cursor.setRight(null);
                }
            }
        }
        else
            throw new IllegalArgumentException(name +
                    " does not match any of the current predator's direct prey.");

    }
}
