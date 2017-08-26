/*
 * jazmin boot script template.variable '$' or 'jazmin' support function to control
 * jazmin server. 
 */
$.setLogLevel('ALL');
$.setLogFile('./log/'+$.getServerName()+".log",true);
//$.disableConsoleLog();

var server = new ProtobufServer();
server.setPort(2001);
$.addServer(server);
//$.addServer(new ConsoleServer());
var driver = new JazminRpcDriver();
driver.addRemoteServer("jazmin://127.0.0.1:6001/cluster/rpc");
$.addDriver(driver);

$.loadApplication('./instance/message/BaggioMessageApplication.jar');
