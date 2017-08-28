/*
 * jazmin boot script template.variable '$' or 'jazmin' support function to control
 * jazmin server. 
 */
$.setLogLevel('ALL');
$.setLogFile('./log/'+$.getServerName()+".log",true);
//$.disableConsoleLog();

$.addServer(new RpcServer());
$.addServer(new ConsoleServer());

var db = new C3p0ConnectionDriver();
db.setUser("baggio");
db.setDriverClass("com.mysql.jdbc.Driver");
db.setPassword("baggio");
db.setUrl("jdbc:mysql://127.0.0.1:3306/baggio?user=baggio&password=baggio&useUnicode=true&characterEncoding=UTF8");
db.setInitialPoolSize(5);
db.setMaxPoolSize(10);
db.setMinPoolSize(5);
db.setStatSql(true)
$.addDriver(db);

$.loadApplication('./instance/rpc/BaggioRpcApplication.jar');
