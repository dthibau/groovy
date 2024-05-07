package org.formation;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

public class MainGroovyShell {

	public static void main(String[] args) throws CompilationFailedException, IOException {

		if ( args.length != 2) {
			System.out.println("Usage java -jar <executable> <dir> <pattern>");
			System.exit(0);
		}
	
		MainGroovyShell me = new MainGroovyShell();

		me.execute(args[0], args[1]);

	}

	public void execute(String dir, String regexp) throws CompilationFailedException, IOException {

		Binding binding = new Binding();
		binding.setProperty("dirname",dir);
		binding.setProperty("pattern",regexp);
		
		GroovyShell shell = new GroovyShell(new GroovyClassLoader(),binding);
		File file = new File("./src/org/formation/MainScript.groovy");

		shell.evaluate(file);
	}


}
