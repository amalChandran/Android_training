package com.example.jacksonpojo;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int age = 23;
	private String name = "amal";
	private List<String> messages = new ArrayList<String>() {
		{
			add("hi");
			add("how");
			add("are u.");
		}
	};
 
	//getter and setter methods
 
	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + ", " +
				"messages=" + messages + "]";
	}
	

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
