package net.skrypt.spigot.skryptcore.api.storage.mysql;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>Handles connections abd storage between the server and a MySQL database.<br />
 * This API can NOT be used in static reference, it must be instantiated ONCE.<br />
 * I recommend instantiating the MySQL API in your main class inside the onEnable method.</p>
 *
 * <p>Please forgive me the uncreative class name, I haven't found a better solution.</p>
 *
 * <p>Created by Skrypt on 25.02.2016.</p>
 */
public class MySQLAPI {

	private Plugin plugin;
	private ConnectionPool cp;

	/**
	 * <p>Instantiates a new MySQL handler.<br />
	 * I recommend instantiating it once inside your onEnable method in your plugin's main class.</p>
	 * <p><b>DO NOT</b> paste your mysql login information inside your plugin manually if you
	 * are thinking of releasing your plugin to the public. Users can
	 * de-compile your plugin and see your username.<br />
	 * Instead, use the provided flatfile API and retrieve the login credentials from a configurations file.</p>
	 * @param plugin Instance of your main class
	 */
	public MySQLAPI(Plugin plugin) {
		this.plugin = plugin;
		this.cp = new ConnectionPool(plugin);
	}

	/**
	 * <p>Sets the host with the default port 3306.</p>
	 * @param host Host of your MySQL server (IP or domain)
	 */
	public void setHost(String host) {
		setHost(host, 3306);
	}

	/**
	 * <p>Sets the host and port.</p>
	 * @param host Post of your MySQL server (IP or domain)
	 * @param port Port of your MySQL server (Can be excluded if default)
	 */
	public void setHost(String host, int port) {
		this.cp.host = host;
		this.cp.port = port;
	}

	/**
	 * <p>Sets the database name.</p>
	 * @param database Name of your MySQL database
	 */
	public void setDatabase(String database) {
		this.cp.database = database;
	}

	/**
	 * <p>Sets the username.</p>
	 * @param username Login to your MySQL database
	 */
	public void setUsername(String username) {
		this.cp.username = username;
	}

	/**
	 * <p>Sets the password.</p>
	 * @param password Login to your MySQL database
	 */
	public void setPassword(String password) {
		this.cp.password = password;
	}

	/**
	 * <p>Sets the minimal amount of ready connection in the pool.<br /
	 * The higher this value, the more connections you can make at once.</p>
	 * @param min The minimal amount of ready connections
	 */
	public void setMinimalConnections(int min) {
		this.cp.min = min;
	}

	/**
	 * <p>Sets the maximal amount of ready connection in the pool.<br /
	 * The higher this value, the more connections you can make at once.<br /
	 * Obviously has to be higher than the minimum amount.</p>
	 * @param max The maximal amount of ready connections
	 */
	public void setMaximalConnections(int max) {
		this.cp.max = max;
	}

	/**
	 * <p>Sets the time in milliseconds until the connection between the
	 * server and the mysql database times out.</p>
	 * @param timeout Time in milliseconds until timeout
	 */
	public void setTimeout(long timeout) {
		this.cp.timeout = timeout;
	}

	/**
	 * <p>Sets if the plugin should automatically disable if an
	 * error occurs during the initial setup of the mysql handler.</p>
	 * @param autoDisable Plugin auto-disable on error (Defaults to true)
	 */
	public void setAutoDisable(boolean autoDisable) {
		this.cp.autoDisable = autoDisable;
	}

	/**
	 * <p>Tries to setup the mysql handler.<br>
	 * Requires mysql credentials set.</p>
	 * <p>For full documentation with examples please visit pub.skrypt.net</p>
	 */
	public void setup() {
		this.cp.setup();
	}

	/**
	 * <p>Prepares a mysql statement to work with.</p>
	 * @param query MySQL query to prepare
	 * @return PreparedStatement - Required for future use
	 */
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			Connection conn = cp.getConnection();
			ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return ps;
		}
	}

	/**
	 * <p>Executes an update on the mysql database.</p>
	 * <p>Used for queries like INSERT or UPDATE which don't return any data.</p>
	 * @param ps Prepared statement that we created before
	 */
	public void update(PreparedStatement ps) {
		Connection conn = null;
		try {
			conn = cp.getConnection();
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, null);
		}
	}

	/**
	 * <p>Executes a query on the mysql database.</p>
	 * <p>Used for queries like SELECT or COUNT, which return data.</p>
	 * @param ps Prepared statement that we created before
	 * @return ResultSet - required to retrieve returned data
	 */
	public ResultSet query(PreparedStatement ps) {
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = cp.getConnection();
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, null, null);
			return rs;
		}
	}

	/**
	 * <p>Closes unneeded resources.</p>
	 * <p>Don't forget to call this method every time you don't need any
	 * resource (Connection, PreparedStatement, ResultSet) anymore to
	 * avoid memory leaks.</p>
	 * <p>Use null on place of resources which you don't have currently.</p>
	 * @param conn Safely closes connections and returns them to the connection pool
	 * @param ps Close prepared statements after execution
	 * @param rs Close ResultSets after retrieving required data
	 */
	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		cp.close(conn, ps, rs);
	}

	/**
	 * <p>Disconnect from the mysql database and close the connection pool
	 * and all it's connections.</p>
	 * <p>To avoid memory leaks, always run this method in your onDisable method.</p>
	 */
	public void disconnect() {
		cp.closePool();
	}
}
