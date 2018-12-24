import java.util.*;
import java.io.*;
import java.util.stream.Collectors;


public class SmallParismony{
    public static void main(String[] args) throws Exception{

        Scanner s = new Scanner(System.in);
        String input = s.next();
        NewickTreeReader ntReader = new NewickTreeReader(input);
        
        TreeNode root = ntReader.readTree();
        
        recursiveUpward(root);

        //initialize root node, always pick the first one in the preference
        int length = root.getPreferences().size();
        String indexLabel = root.getLabel();
        String rootLabel = "";
        for(int i = 0; i < length; i++){
            rootLabel = rootLabel + root.getPreferences().get(i).iterator().next();
        }
        root.setLabel(rootLabel);        
        recursiveDownward(root);
        //at last, print root label
        System.out.println(indexLabel + ":" + root.getLabel());

        s.close();
    }


    public static void recursiveDownward(TreeNode cur){
        int length = cur.getPreferences().size();
        if(cur.getChildren().size()!=0){
            TreeNode leftChild = cur.getChildren().get(0);
            TreeNode rightChild = cur.getChildren().get(1);

                String indexLabelLeft = leftChild.getLabel();
                String indexLabelRight = rightChild.getLabel();
                String leftLabel = "";
                String rightLabel = "";

                    for(int i = 0; i < length; i++){
                        char parentChar = cur.getLabel().charAt(i);
                        if(leftChild.getPreferences().get(i).contains(parentChar)){
                            leftLabel = leftLabel + parentChar;
                        }else{
                            leftLabel = leftLabel + leftChild.getPreferences().get(i).iterator().next();
                        }
                        if(rightChild.getPreferences().get(i).contains(parentChar)){
                            rightLabel = rightLabel + parentChar;
                        }else{
                            rightLabel = rightLabel + rightChild.getPreferences().get(i).iterator().next();
                        }
                    }

                    //now update the labels for both children
                    leftChild.setLabel(leftLabel);
                    rightChild.setLabel(rightLabel);

                    //recursively call downward function on both children
                    recursiveDownward(leftChild);
                    if(leftChild.getChildren().size()!=0){
                        System.out.println(indexLabelLeft + ":" + leftLabel);
                    }
                    recursiveDownward(rightChild);
                    if(rightChild.getChildren().size()!=0){
                        System.out.println(indexLabelRight + ":" +rightLabel);
                    }
            
        }
    }
    
    /**
	 *	This method create a deep copy of the set object
	 *	@param		Set object   The set object you want to copy
	 */
    public static<T> Set<T> clone(Set<T> original) {
        Set<T> copy = original.stream().collect(Collectors.toSet());
        return copy;
    }

    public static List<Set<Character>> recursiveUpward(TreeNode cur){
        if(cur.getPreferences() == null){
            //if the current tree node has no preference set yet.
            //we recursively merge the two preference sets from its children

            if(cur.getChildren().size()!=0){ //internal node, not leaf
          
                List<Set<Character>> resultPref = new ArrayList<Set<Character>>();
                TreeNode leftChild = cur.getChildren().get(0);
                TreeNode rightChild = cur.getChildren().get(1);
                //getting the preference of both children
                List<Set<Character>> prefLeft = recursiveUpward(leftChild);
                List<Set<Character>> prefRight = recursiveUpward(rightChild);
               
                for(int i = 0; i < prefLeft.size(); i++){
 
                    Set<Character> temp = new TreeSet<Character>();
                    Set<Character> prefL = new TreeSet<Character>();
                    Set<Character> prefR = new TreeSet<Character>();
                    // Set<Character> intersection = new TreeSet<Character>();


                    prefL = clone(prefLeft.get(i));
                    prefR = clone(prefRight.get(i));
                    temp = clone(prefL);
                    
                    temp.retainAll(prefR);
                    //check if the intersection set is empty or not 
                    if(temp.size() == 0){ 
                        //if the intersection is empty, then we union children preference
                        temp = clone(prefL);
                        temp.addAll(prefR);
                    }

                    resultPref.add(temp);
                }
                cur.setPreferences(resultPref);
               
                return resultPref;
            }else{
                //if the node is leaf node, we simply make its label into a preference list
                List<Set<Character>> pref = new ArrayList<Set<Character>>();
                String lable = cur.getLabel();  
                for(int i = 0; i < lable.length();i++){
                    //make each character in the leaf node as a set (containing one character)
                    Set<Character> temp = new TreeSet<Character>();
                    temp.add(lable.charAt(i));
                    pref.add(temp);
                }
                cur.setPreferences(pref);
                return pref;
            }
        }
        return null ;
    }
}

//WARNING!!!!! The following code is provided by Prof.Yip.


class NewickTreeReader
{
	/**
	 *	<pre>
	 *	CSCI3220 2018-19 First Term Assignment 4
	 *	This class defines a reader for data in Newick Tree file format.
	 *	</pre>
	 *
	 *	@author		Kevin Yuk-Lap Yip
	 *	@version	1.0 (19 November 2018)
	 *
	 *	<pre>
	 *	Change History:
	 *	1.0	- Initial version
	 *	</pre>
	 */

	/*----------------------------------------------------------------------
	 Class constants
	----------------------------------------------------------------------*/

	// Constants for the different sates
	protected static final char NODE_START	= 'S';	// Beginning of a node
	protected static final char LABEL	= 'L';	// Label of a node
	protected static final char COLON	= 'C';	// Colon
	protected static final char DIST	= 'D';	// Distance to the parent node
	protected static final char NODE_END	= 'E';	// End of node
	protected static final char SEMI_COLON	= 'F';	// Semi-colon

	/*----------------------------------------------------------------------
	 Class variables
	----------------------------------------------------------------------*/

	/**
	 *	The underlying tokenizer.
	 */
	protected StringTokenizer t = null;


	/*----------------------------------------------------------------------
	 Public methods
	----------------------------------------------------------------------*/

	/**
	 *	Constructor: create a new reader that reads from a string in
	 *	Newick format.
	 *	@param		s			The string
	 */
	public NewickTreeReader(String s)
	{
		// A strong tokenizer that recognizes all special symbols and
		// white spaces as delimiters, and returns both delimiters and
		// non-delimiters as tokens
		this.t = new StringTokenizer(s, "(,):; \t", true);
	}

	/**
	 *	Read the tree. (Stop at the first semi-colon, ignore everything
	 *	after it.)
	 *	@return					The tree
	 */
	public TreeNode readTree()
	{
		char state = NODE_START;	// Current state, initialized to NODE_START
		TreeNode currNode = null;	// Always point to the node that has been created and is still active
						// (i.e., may still have something to do with it)

		// Read the tokens until encountering the first semi-colon
		while (state != SEMI_COLON)
		{
			String token = t.nextToken();
			if (token == null)		// No more tokens
				throw new RuntimeException("Unexpected end of data.");

			switch (state)
			{
				case NODE_START:	// Expecting a new node to be defined next
					if (token.equals("("))		// The node is an internal node
					{
						TreeNode newNode = new TreeNode();
						newNode.setParent(currNode);
						if (currNode != null)
							currNode.addChild(newNode);
						currNode = newNode;
//						state = NODE_START;	// These commented lines are just for completeness
					}
					else if (token.equals(")"))	// The node is a leaf node without label or distance, and no more siblings
					{
						TreeNode newNode = new TreeNode();
						newNode.setParent(currNode);
						if (currNode != null)
							currNode.addChild(newNode);
						else			// Multiple roots: not allowed
							reportFormatError(state, token);
//						currNode = currNode;
						state = NODE_END;
					}
					else if (token.equals(","))	// The node is a leaf node without label or distance, and a sibling is coming
					{
						TreeNode newNode = new TreeNode();
						newNode.setParent(currNode);
						if (currNode != null)
							currNode.addChild(newNode);
						else			// Multiple roots: not allowed
							reportFormatError(state, token);
//						currNode = currNode;
//						state = NODE_START;
					}
					else if (token.equals(":"))	// The node is a leaf node without label but with a distance
					{
						TreeNode newNode = new TreeNode();
						newNode.setParent(currNode);
						if (currNode != null)
							currNode.addChild(newNode);
						currNode = newNode;
						state = COLON;
					}
					else if (!isDelimiter(token))	// The node is a leaf node with a label (not known whether with a distance yet)
					{
						TreeNode newNode = new TreeNode();
						newNode.setParent(currNode);
						if (currNode != null)
							currNode.addChild(newNode);
						newNode.setLabel(token);
						currNode = newNode;
						state = LABEL;
					}
					else
						reportFormatError(state, token);
					break;
				case LABEL:		// Just read a node label
					if (token.equals(")"))		// Ending the parent node, which should be an internal node
					{
						currNode = currNode.getParent();
						if (currNode == null)	// Closing too many brackets
							reportFormatError(state, token);
						else
							state = NODE_END;
					}
					else if (token.equals(","))	// Going to start a sibling. Pass the control back to the parent first
					{
						currNode = currNode.getParent();
						if (currNode == null)	// Multiple roots: not allowed
							reportFormatError(state, token);
						else
							state = NODE_START;
					}
					else if (token.equals(":"))	// Going to receive the distance
						state = COLON;
					else if (token.equals(";"))	// No more nodes. Let the final check to determine if the tree is complete
						state = SEMI_COLON;
					else
						reportFormatError(state, token);
					break;
				case COLON:		// Just read a colon
					if (!isDelimiter(token))	// The distance: check if it is a valid number
					{
						try
						{
							double dist = Double.parseDouble(token);
							currNode.setDistToParent(dist);
							state = DIST;
						}
						catch (NumberFormatException nfe)
						{
							reportFormatError(state, token);
						}
					}
					else
						reportFormatError(state, token);
					break;
				case DIST:		// Just read the distance
					if (token.equals(")"))		// Ending the parent node, which should be an internal node
					{
						currNode = currNode.getParent();
						if (currNode == null)	// Closing too many brackets
							reportFormatError(state, token);
						else
							state = NODE_END;
					}
					else if (token.equals(","))
					{
						currNode = currNode.getParent();
						if (currNode == null)	// Multiple roots: not allowed
							reportFormatError(state, token);
						else
							state = NODE_START;
					}
					else if (token.equals(";"))
						state = SEMI_COLON;
					else
						reportFormatError(state, token);
					break;
				case NODE_END:		// Just read the close bracket of an internal node, may have the label and/or the distance coming
					if (token.equals(")"))	// Nothing comes, and ending the parent node
					{
						currNode = currNode.getParent();
						if (currNode == null)	// Closing too many brackets
							reportFormatError(state, token);
						else
							state = NODE_END;
					}
					else if (token.equals(","))	// Nothing comes, and is going to have a sibling node: just pass the control back to the parent
					{
						currNode = currNode.getParent();
						if (currNode == null)	// Multiple roots: not allowed
							reportFormatError(state, token);
						else
							state = NODE_START;
					}
					else if (token.equals(":"))	// No label, but expect a distance to come next
						state = COLON;
					else if (token.equals(";"))	// Nothing comes, and no more things to read
						state = SEMI_COLON;
					else if (!isDelimiter(token))	// A label comes
					{
						currNode.setLabel(token);
						state = LABEL;
					}
					else
						reportFormatError(state, token);
					break;
				default:		// Should never happen
					throw new RuntimeException("Unknown state.");
			}
		}

		if (currNode == null)
			throw new RuntimeException("More close brackets then open brackets");
		else if (currNode.getParent() != null)
			throw new RuntimeException("More open brackets then close brackets");
		return currNode;
	}


	/*----------------------------------------------------------------------
	 Protected methods
	----------------------------------------------------------------------*/

	/**
	 *	Check if the input token is a delimiter.
	 *	@param		token			The token
	 *	@return					True if the token is a delimiter,
	 *						false otherwise.
	 */
	public boolean isDelimiter(String token)
	{
		return token.equals("(") ||
		       token.equals(")") ||
		       token.equals(",") ||
		       token.equals(":") ||
		       token.equals(";");
	}

	/**
	 *	Report a format error.
	 *	@param		state			The current state
	 *	@param		token			The token being processed
	 */
	public void reportFormatError(char state, String token)
	{
		String stateStr = null;
		if (state == NODE_START)
			stateStr = "NODE_START";
		else if (state == LABEL)
			stateStr = "LABEL";
		else if (state == COLON)
			stateStr = "COLON";
		else if (state == DIST)
			stateStr = "DIST";
		else if (state == NODE_END)
			stateStr = "NODE_END";
		else if (state == SEMI_COLON)
			stateStr = "SEMI_COLON";

		throw new RuntimeException("Unexpected token [" + token + "] encountered in the " + stateStr + " state.");
	}
}

class TreeNode
{
	/**
	 *	<pre>
	 *	CSCI3220 2018-19 First Term Assignment 4
	 *	This is a class for storing the information of a tree node.
	 *	</pre>
	 *
	 *	@author		Kevin Yuk-Lap Yip
	 *	@version	1.0 (19 November 2018)
	 *
	 *	<pre>
	 *	Change History:
	 *	1.0	- Initial version
	 *	</pre>
	 */

	/*----------------------------------------------------------------------
	 Instance variables
	----------------------------------------------------------------------*/

	/**
	 *	The label of this node
	 */
	protected String label = "";

	/**
	 *	The input/inferred sequence of this node in the downward phase
	 */
	protected String sequence = "";

	/**
	 *	The preferences of this node in the upward phase
	 */
	protected List<Set<Character>> preferences = null;

	/**
	 *	The parent node, null for root node
	 */
	protected TreeNode parent = null;

	/**
	 *	Distance from the parent, NaN for root node
	 *	(Not required for this assignment)
	 */
	protected double distToParent = Double.NaN;

	/**
	 *	The list of children, empty for leaf nodes
	 */
	protected List<TreeNode> children = new ArrayList<TreeNode>();


	/*----------------------------------------------------------------------
	 Public methods
	----------------------------------------------------------------------*/

	/**
	 *	This method gets the label of this node.
	 *	@return					The label of this node
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 *	This method gets the input/inferred sequence of this node.
	 *	@return					The sequence of this node
	 */
	public String getSequence()
	{
		return sequence;
	}

	/**
	 *	This method gets the sequence preferences of this node in the
	 *	upward phase.
	 *	@return					The preferences of this node
	 */
	public List<Set<Character>> getPreferences()
	{
		return preferences;
	}

	/**
	 *	This method gets the parent of this node.
	 *	@return					The parent of this node
	 */
	public TreeNode getParent()
	{
		return parent;
	}

	/**
	 *	This method gets the distance between this node and its parent.
	 *	(Not required for this assignment)
	 *	@return					The distance
	 */
	public double getDistToParent()
	{
		return distToParent;
	}

	/**
	 *	This method gets the list of chldren.
	 *	@return					The list of children
	 */
	public List<TreeNode> getChildren()
	{
//		return Collections.unmodifiableList(children);
		return children;
	}

	/**
	 *	This method sets the label of this node.
	 *	@param		label			The label of this node
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 *	This method sets the input/inferred sequence of this node.
	 *	@param		sequence		The sequence of this node
	 */
	public void setSequence(String sequence)
	{
		this.sequence = sequence;
	}

	/**
	 *	This method sets the sequence preferences of this node in the
	 *	upward phase.
	 *	@param		preferences		The preferences of this node
	 */
	public void setPreferences(List<Set<Character>> preferences)
	{
		this.preferences = preferences;
	}

	/**
	 *	This method sets the parent of this node.
	 *	@param		parent			The parent of this node
	 */
	public void setParent(TreeNode parent)
	{
		this.parent = parent;
	}

	/**
	 *	This method sets the distance between this node and its parent.
	 *	(Not required for this assignment)
	 *	@param		distToParent		The distance
	 */
	public void setDistToParent(double distToParent)
	{
		this.distToParent = distToParent;
	}

	/**
	 *	This method adds a child to this node.
	 *	@param		child			The child
	 */
	public void addChild(TreeNode child)
	{
		this.children.add(child);
	}
}
