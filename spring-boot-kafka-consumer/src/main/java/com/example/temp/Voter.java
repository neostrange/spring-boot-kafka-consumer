package com.example.temp;

import com.example.model.Ipojo;

public class Voter implements Ipojo {

	private String name;

	private int age;

	public Voter() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
