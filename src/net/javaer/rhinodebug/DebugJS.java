package net.javaer.rhinodebug;

import java.io.FileReader;

import org.eclipse.wst.jsdt.debug.rhino.debugger.RhinoDebugger;
import org.eclipse.wst.jsdt.debug.rhino.debugger.shell.DebugShell;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class DebugJS {

	private static final String FILENAME = "D:\\eclipse\\workspace\\rhino\\test1.js";

	public static void main(String[] args) throws Exception {
		// localDebug() ;
		remoteDebug();
	}

	private static void remoteDebug() throws Exception {
		ContextFactory factory = new ContextFactory();
		String rhino = "transport=socket,suspend=true,address=9000,trace=true";
		RhinoDebugger debugger = new RhinoDebugger(rhino);
		debugger.start();
		factory.addListener(debugger);

		// Create context and run.
		Context context = factory.enterContext();
		Scriptable scope = context.initStandardObjects();

		// inject java to js
		Object jsOut = Context.javaToJS(System.out, scope);
		ScriptableObject.putProperty(scope, "out", jsOut);

		FileReader fileReader = new FileReader(FILENAME);
		//the third param of method evaluateReader should be a absolute path, 
		//or it may cause the breakpoint can not be detected by rhino debugger
		Object exec = context.evaluateReader(scope, fileReader, FILENAME, 0,null);
		System.out.println(FILENAME +" execute result:"+exec);
		debugger.stop();
		java.lang.System.out.println("debugger stopped!");

	}

	private static void localDebug() throws Exception {
		String[] args = { "-f", FILENAME,
				"-port", "9010", "-trace", "true", "-suspend", "true" };
		// org.mozilla.javascript.tools.shell.Main.main(args);
		DebugShell.main(args);
	}

	
}
