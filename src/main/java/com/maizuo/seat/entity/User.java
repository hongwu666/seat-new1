package com.maizuo.seat.entity;

public class User extends DefaultSystemRedisMode<User> {
	private int id;
	private String sex;
	private String name;
	private String age;

	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String getListKey() {
		return sex;
	}

	@Override
	public String getObjKey() {
		return String.valueOf(id);
	}

	public int getId() {
		return id;
	}

	public String getSex() {
		return sex;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
