package chess.player;

/**
 * Alliance is an enum type that is primarily used 
 * for keeping all teams in this case white and black. 
 * Players and pieces in chess belong to a team. <i> example: </i> 
 * a black player has black pieces etc...
 * </p>
 * enum type should be used only for consts and nothing else. In addition
 * you cannot add or remove elements of enum at runtime, so if you 
 * want to be able to add or remove teams at runtime consider a different 
 * approach.
 * 
 * @author sHeston
 *
 */
public enum Alliance {
	BLACK , WHITE;
	
	/**
	 * This is a private static "copy the values() array 
	 * to avoid array copying each time next() is called."
	 * <p>
	 * 
	 * @see	http://stackoverflow.com/a/17006263/7066174
	 */
	private static Alliance[] vals = values();
	
	/**
	 * Iterates to the next alliance. 
	 * </p>
	 * Alliance values are stored into the array name <i> vals </i>, 
	 * the method iterates through each value in the array. If the
	 * end of the array has been reached it will go back to 
	 * the first value in array. I have done this so you can add multiple 
	 * other alliances if you want, <b> also read header text above </b>.
	 * 
	 * @return 	the next <code>Alliance</<code>
	 * @author 	Jim Garrison
	 * @see		http://stackoverflow.com/a/17006263/7066174
	 * 
	 */
    public Alliance next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }
    
}
