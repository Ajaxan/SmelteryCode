package com.atlasmc.atlasassorted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WebsiteSignUpListener implements CommandExecutor {
	JavaPlugin plugin;
	String ip = "localhost";

	public WebsiteSignUpListener(JavaPlugin plugin) {
		this.plugin = plugin;
		plugin.getLogger().info("help");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("website")){
			if(args.length == 0 && sender instanceof Player) {
				try {
					Player player = (Player) sender;
					// Sets the authenticator that will be used by the networking code
					// when a proxy or an HTTP server asks for authentication.
					Authenticator.setDefault(new CustomAuthenticator());
					URL url = new URL("http://"+ip+"/api/connect?username="+sender.getName() + "&uuid=" + player.getUniqueId());

					// read text returned by server
					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

					String line;
					while ((line = in.readLine()) != null) {
						sender.sendMessage(ChatColor.GOLD + line);
					}
					in.close();
				}
				catch (MalformedURLException e) {
					System.out.println("Malformed URL: " + e.getMessage());
				}
				catch (IOException e) {
					System.out.println("I/O Error: " + e.getMessage());
				}
				return true;
			}
		}
		return false;
	}
}
