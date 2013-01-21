This is a rhinodebugger demo, it provide functions to remote/local debug javascript.

You can import the project to eclipse,change the static FILENAME path in the java file, then run the project follow the steps:

1. Start the web project
2. Set breakpoints in test1.js
3. Right click test1.js, select Debug As---Debug Configuration---Remote Javascript
3.1. In the "Connect" tab, Select Connector "Mozilla Rhino - Attach Connector", use the default host:localhost, default port:9000 
3.2. In the "Source" tab, Remove the "Default", then add your current project where javascript files place in as source.
3.3. Click "Debug".


Environment:
java 6
eclipse 3.5

Dependences:
org.eclipse.wst.jsdt.debug.rhino.debugger_1.0.301.v201208171701.jar
org.eclipse.wst.jsdt.debug.transport_1.0.101.v201208171701.jar
js.jar


Contact Me: henrypoter@126.com





