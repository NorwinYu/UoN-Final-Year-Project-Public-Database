package edu.mum.library.view.base;

import org.springframework.beans.factory.annotation.Autowired;

import edu.mum.library.LibraryApplication;

public abstract class BaseUIController {
	@Autowired
	protected LibraryApplication application;
}
