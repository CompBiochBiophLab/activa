package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Comment;
import com.o2hlink.activ8.client.entity.HasComments;
import com.o2hlink.activ8.client.entity.LogRecord;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Creates a new comment on a {@link HasComments} object
 * @author Miguel Angel Hernandez
 **/
public class CommentRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3725810701487788916L;
	/**
	 * 
	 **/
	private HasComments provider;
	/**
	 * 
	 **/
	private Comment comment;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CommentRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public CommentRemoveAction(HasComments provider, Comment comment){
		setProvider(provider);
		setComment(comment);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasComments provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="logRecord", type=LogRecord.class),
		@XmlElement(name="user", type=User.class)
	})
	public HasComments getProvider() {
		return provider;
	}
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