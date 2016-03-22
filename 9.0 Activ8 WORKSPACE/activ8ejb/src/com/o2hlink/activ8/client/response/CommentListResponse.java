package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Comment;

/**
 * @author Miguel Angel Hernandez
 **/
public class CommentListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3483795039154778751L;
	/**
	 * 
	 **/
	private List<Comment> comments;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CommentListResponse(){
		
	}
	/**
	 * 
	 **/
	public CommentListResponse(List<Comment> comments){
		setComments(comments);
	}
//METHODS
	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
}
