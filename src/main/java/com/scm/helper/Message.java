package com.scm.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Message {
   public String contents;
   @Builder.Default
   public MessageType type=MessageType.blue;
public String getContents() {
	return contents;
}
public void setContents(String contents) {
	this.contents = contents;
}
public MessageType getType() {
	return type;
}
public void setType(MessageType type) {
	this.type = type;
}
public Message(String contents, MessageType type) {
	super();
	this.contents = contents;
	this.type = type;
}
public Message() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "Message [contents=" + contents + ", type=" + type + "]";
}
   
   
}
