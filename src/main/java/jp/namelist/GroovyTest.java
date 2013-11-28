package jp.namelist;

import groovy.lang.Closure;
import groovy.lang.ExpandoMetaClass;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

public class GroovyTest {

	public static void main(String[] args) throws CompilationFailedException, IOException {
		
		GroovyShell shell = new GroovyShell();
		File groovy = new File("test.groovy"); 
		Object obj = shell.evaluate(groovy);
		System.out.println(obj.getClass());
		
		
	}
}
