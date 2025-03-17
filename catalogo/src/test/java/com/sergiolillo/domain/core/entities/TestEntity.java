package com.sergiolillo.domain.core.entities;

import jakarta.validation.constraints.NotNull;

public class TestEntity<E> extends AbstractEntity<E> {
	 	@NotNull
	 	private String name;

	    public TestEntity(String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
}
