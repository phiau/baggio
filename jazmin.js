/*
 * jazmin boot script template.variable '$' or 'jazmin' support function to control
 * jazmin server. 
 */
$.setLogLevel('ALL');
$.setLogFile('./log/'+$.getServerName()+".log",true);
//$.disableConsoleLog();

var msg = new MessageServer();
msg.setPort(3002);
$.addServer(msg);
$.addServer(new ConsoleServer());

$.loadApplication('./instance/default/BaggioMessageApplication.jar');
