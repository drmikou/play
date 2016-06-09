package common;

import java.util.ArrayList;
import java.util.Date;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This Object represent a discussion for a document.
 * @author pelo
 *
 */
/*
 * Why ?
 * -----
 * 
 * It is just a container for a json object on client side.
 * Implementing a Java class allow us to represent a discussion by a scala template.
 * (which is called by Ajax, explation of the mecanism bellow)
 * 
 * 
 * How does it works ?
 * -------------------
 * 
 * TODO
 */
public class DocumentDiscussion {

	public String discussionCreator;
	public Date timeStamp;
	public String status;

	public String subject;
	public ArrayList<Comment> commentList = new ArrayList<Comment>();
	
	public static class Comment {

		public String speakerLogin;
		public Date postDateTimeStamp;

		public String content;
	}

	// To migrate in Unit test
	public static void main(String[] args) {
		DocumentDiscussion d = new DocumentDiscussion();
		d.discussionCreator = "ttoto";
		d.status = "open";
		d.timeStamp = new Date();

		Comment c1 = new Comment();
		c1.postDateTimeStamp = new Date();
		c1.speakerLogin = "tata";
		c1.content = "azerty";
		d.commentList.add(c1);

		Comment c2 = new Comment();
		c2.postDateTimeStamp = new Date();
		c2.speakerLogin = "titi";
		c2.content = "qsdfgh";
		d.commentList.add(c2);

		JsonNode js = Json.toJson(d);
		System.out.println(js);

		DocumentDiscussion d2 = Json.fromJson(js, DocumentDiscussion.class);
		System.out.println(Json.toJson(d2));

	}
}
