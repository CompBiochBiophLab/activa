package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Comment;

/**
 * A comment
 * @author Miguel Angel Hernandez
 **/
public class CommentResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 105770276979318641L;
	/**
	 * 
	 **/
	private Comment comment;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CommentResponse() {
		
	}
	/**
	 *
	 **/
	public CommentResponse(Comment comment){
		setComment(comment);
	}
//METHODS
	/**
	 * @param comment the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}
}
