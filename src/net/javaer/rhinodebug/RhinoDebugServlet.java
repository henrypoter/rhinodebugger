package net.javaer.rhinodebug;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.wst.jsdt.debug.rhino.debugger.RhinoDebugger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Servlet implementation class RhinoDebugServlet
 */
public class RhinoDebugServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILENAME = "D:/eclipse/workspace/rhinodebug/test1.js";
	
	// private static RhinoDebugger debugger= RhinoDebugListener.debugger;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RhinoDebugServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			
			Context context = ContextFactory.getGlobal().enterContext();
			Scriptable scope = context.initStandardObjects();

			// inject java to js
			Object jsOut = Context.javaToJS(System.out, scope);
			ScriptableObject.putProperty(scope, "out", jsOut);

			//you can use FILENAME as the param for the servlet
			FileReader fileReader = new FileReader(FILENAME);
			Object exec = context.evaluateReader(scope, fileReader, FILENAME, 0, null); 

			System.out.println(FILENAME +" execute result:"+exec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("done");
		out.flush();
		out.close();
	}
	
	/**
	 * this will call when only one time while the servlet container start
	 */
	public void init() throws ServletException{
		super.init();
		
		//get rhino js ContextFactory
		ContextFactory factory = ContextFactory.getGlobal();
		String rhino = "transport=socket,suspend=false,address=9010,trace=true";
		RhinoDebugger debugger = new RhinoDebugger(rhino);
		
		//add RhinoDebugger to ContextFactory
		factory.addListener(debugger);
		try {
			System.out.println("debugger starting...");
			debugger.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("debugger started!");
	}
}
