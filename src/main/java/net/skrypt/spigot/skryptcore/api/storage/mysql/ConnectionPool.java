package net.skrypt.spigot.skryptcore.api.storage.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.skrypt.spigot.skryptcore.api.chat.ChatAPI;
import net.skrypt.spigot.skryptcore.api.enums.MessageType;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Skrypt on 25.02.2016.
 */
class ConnectionPool {

	private Plugin plugin;

	private HikariDataSource dataSource;

	protected String host;
	protected int port;
	protected String database;
	protected String username;
	protected String password;

	protected int min;
	protected int max;
	protected long timeout;

	protected boolean autoDisable;

	protected ConnectionPool(Plugin plugin) {
		this.plugin = plugin;
		this.autoDisable = true;
	}

	protected void setup() {
		try {
			HikariConfig hc = new HikariConfig();
			hc.setJdbcUrl("jdbc:mysql://" + host + ":" + Integer.toString(port) + "/" + database);
			hc.setDriverClassName("com.mysql.jdbc.Driver");
			hc.setUsername(username);
			hc.setPassword(password);
			hc.setMinimumIdle(min);
			hc.setMaximumPoolSize(max);
			hc.setConnectionTimeout(timeout);
			dataSource = new HikariDataSource(hc);
		} catch (Exception e) {
			e.printStackTrace();
			ChatAPI.sendMessage(MessageType.CONSOLE, "Some data wasn't passed or was incorrect in order to set-up MySQLAPI connection.");
			if (autoDisable) {
				ChatAPI.sendMessage(MessageType.CONSOLE, "Disabling plugin " + this.plugin.getName() + " now ...");
				plugin.getPluginLoader().disablePlugin(plugin);
			}
		}
	}

	protected Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	protected void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void closePool() {
		if (dataSource != null && !dataSource.isClosed()) {
			dataSource.close();
		}
	}

}
