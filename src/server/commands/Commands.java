package server.commands;

import java.util.HashMap;
import server.Server;

public abstract class Commands {

	protected HashMap<String, Commands> cmds = new HashMap<String, Commands>();
	protected Server server;
	private boolean inited = false;

	public Commands() {

	}
	
	public void init(Server server) {
		this.server = server;
		registerCommands();
		inited = true;
	}

	public void registerCommands() {
	}

	public Commands getCommand(String cmd) {
		return cmds.get(cmd);
	}

	public abstract void execute(String[] args);

	public boolean hasBeenInit() {
	    return inited;
    }
	
	public static void checkIfCommand(String text, Server server) {
		text = text.substring(1);
		String oldText = text.split(" ")[0];
		Commands cmds;
		Class<?> cls;
		try {
			text = text.toLowerCase();
			text = Character.toString(text.charAt(0)).toUpperCase() + text.substring(1);
			String classString = text.split(" ")[0];
			System.out.println(text);
			cls = Class.forName("com.thecherno.chernochat.server.commands." + classString + "Command");
			cmds = (Commands) cls.newInstance();
			if (cmds.hasBeenInit() == false) {
				cmds.init(server);
			}
			String command = text.split(" ")[0];
			String args = text.substring(command.length() + 1, text.length());
			cmds.execute(args.split(" "));
			System.out.println(args);
		} catch (ClassNotFoundException e) {
			System.out.println("The command " + oldText + " was not found. Type /help for more information");
		} catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
	}
}
