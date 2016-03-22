package com.o2hlink.activ8.server.local;

import java.util.List;

import com.o2hlink.activ8.client.entity.Comment;
import com.o2hlink.activ8.client.entity.HasComments;

/**
 * @author Miguel Angel Hernandez
 **/
public interface CommentLocal {
	/**
	 * 
	 **/
	public Comment addComment(HasComments provider, Comment comment);
	/**
	 * 
	 **/
	public List<Comment> getComments(HasComments provider);
	/**
	 * 
	 **/
	public void removeComment(HasComments provider, Comment comment);
}
