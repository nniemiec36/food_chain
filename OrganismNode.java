/**
 * A class that acts as a single node in the tree, and this node represents either a
 * plant or an animal. It contains three references for three children nodes
 * which will act as prey. It also contains a name variable for the species name,
 * and a number of boolean values to identify the node's diet.
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

public class OrganismNode {
    private String name;
    private boolean isPlant;
    private boolean isHerbivore;
    private boolean isCarnivore;
    private OrganismNode left;
    private OrganismNode middle;
    private OrganismNode right;

    /**
     * A default constructor for an OrganismNode object.
     */
    public OrganismNode(){
    }

    /**
     * A constructor for an OrganismNode object that takes in three parameters.
     * @param name
     *      The name of the species.
     * @param isHerbivore
     *      Whether the species is a herbivore.
     * @param isCarnivore
     *      Whether the species is a carnivore.
     */
    public OrganismNode(String name, boolean isHerbivore, boolean isCarnivore){

        this.name = name;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;

    }

    /**
     * A constructor for an OrganismNode object that takes in two parameters.
     * @param name
     *      The name of the species.
     * @param isPlant
     *      Whether the species is a plant or not.
     */
    public OrganismNode(String name, boolean isPlant){

        this.name = name;
        this.isPlant = isPlant;

    }

    /**
     * Mutator method for setting the left node reference of the current node.
     * @param left
     *     Left node to be set as the reference.
     */
    public void setLeft(OrganismNode left) {
        this.left = left;
    }

    /**
     * Mutator method for setting the middle node reference of the current node.
     * @param middle
     *     Middle node to be set as the reference.
     */
    public void setMiddle(OrganismNode middle) {
        this.middle = middle;
    }

    /**
     * Mutator method for setting the right node reference of the current node.
     * @param right
     *     Right node to be set as the reference.
     */
    public void setRight(OrganismNode right) {
        this.right = right;
    }

    /**
     * Accessor method for returning the name of the species.
     * @return
     *      The String value of the name of the species.
     */
    public String getName() {

        return name;
    }

    /**
     * Returns true if the node is a plant, returns false if not.
     * @return
     *      Returns boolean value of whether the node is a plant or not.
     */
    public boolean isPlant() {

        return isPlant;
    }

    /**
     * Returns true if the node is a herbivore, returns false if not.
     * @return
     *      Returns boolean value of whether the node is a herbivore or not.
     */
    public boolean isHerbivore() {

        return isHerbivore;
    }

    /**
     * Returns true if the node is a carnivore, returns false if not.
     * @return
     *      Returns boolean value of whether the node is a carnivore or not.
     */
    public boolean isCarnivore() {

        return isCarnivore;
    }

    /**
     * Accessor method for returning the left child of the species.
     * @return
     *      The left child node of the cursor.
     */
    public OrganismNode getLeft() {

        return left;
    }

    /**
     * Accessor method for returning the middle child of the species.
     * @return
     *      The middle child node of the cursor.
     */
    public OrganismNode getMiddle() {

        return middle;
    }

    /**
     * Accessor method for returning the right child of the species.
     * @return
     *      The right child node of the cursor.
     */
    public OrganismNode getRight() {

        return right;
    }

    /**
     * A helper method to print out the path from root to targeted cursor.
     * @param path
     *      Path stored in an OrganismNode array/
     * @param root
     *      Root of the tree.
     * @param cursor
     *      Targeted cursor node.
     * @param count
     *      Index of the OrganismNode.
     * @return
     *      Returns an array of OrganismNodes that correspond to the correct path.
     * @throws Exception
     *      Throws an Exception to stop the recursion when the targeted path is found.
     */
    public OrganismNode[] findFoodPath(OrganismNode[] path, OrganismNode root, OrganismNode cursor, int count) throws Exception{

        if(root != null) {
            path[count] = root;
            if(root.getLeft() != null)
                path =  findFoodPath(path, root.getLeft(), cursor, count + 1);
            if(root.getMiddle() != null)
               path =  findFoodPath(path, root.getMiddle(), cursor, count + 1);
            if(root.getRight() != null)
               path =  findFoodPath(path, root.getRight(), cursor, count + 1);
            if (root.getName().equals(cursor.getName())){
                throw new Exception("abc");
            }
        }

            return path;
    }

    /**
     * Helper method to print out the current tree for printOrganismTree method.
     * @param spaceNumber
     *      Number of spaces to print out.
     * @param root
     *      Root of the tree.
     */
    public void preorder(int spaceNumber, OrganismNode root){
        String spaces = "";
        for(int i = 0; i < spaceNumber; i++){
            spaces += "\t";
        }
        if(root.isPlant()){
            System.out.println(spaces + "- " + root.getName());
        }
        else {
            System.out.println(spaces + "|- " + root.getName());
        }
        if(root.getLeft() != null)
            preorder(spaceNumber + 1, root.getLeft());
        if(root.getMiddle() != null)
            preorder(spaceNumber + 1, root.getMiddle());
        if(root.getRight() != null)
            preorder(spaceNumber + 1, root.getRight());
    }

    /**
     * Method that adds a child node to the current node to represent its prey.
     * @param preyNode
     *      The prey to be added.
     * @throws PositionNotAvailableException
     *      Throws a PositionNotAvailableException when all the child nodes are full.
     * @throws IsPlantException
     *      Throws a IsPlantException if the current node is a Plant.
     * @throws DietMismatchException
     *      Throws a DietMismatchException if the prey does not match the current
     *      node's diet.
     */
    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException,
            DietMismatchException {

        if(preyNode.isPlant())
            throw new IsPlantException("Prey cannot be a plant.");

        if(left != null && right != null & middle != null)
            throw new PositionNotAvailableException("No available position");

        if(!this.isCarnivore())
            throw new DietMismatchException("Predator is a herbivore.");

        if(left == null){
            left = preyNode;
        }
        else if(middle == null){
            middle = preyNode;
        }
        else if(right == null){
            right = preyNode;
        }

    }
}
