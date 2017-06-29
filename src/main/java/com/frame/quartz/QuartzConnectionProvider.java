package com.frame.quartz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.quartz.utils.ConnectionProvider;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;

public class QuartzConnectionProvider implements ConnectionProvider {
	//private Logger logger = LoggerFactory.getLogger(getClass());
	private DruidPlugin druidPlugin;
	private static final String DB_CONFIG="config.txt";
	
	public Connection getConnection() throws SQLException {
		return this.druidPlugin.getDataSource().getConnection();
	}

	public void initialize() throws SQLException {
		PropKit.use(DB_CONFIG);
		Properties properties = PropKit.getProp().getProperties();
        druidPlugin = new DruidPlugin(
                properties.getProperty("jdbcUrl"),
                properties.getProperty("user"),
                properties.getProperty("password"),
                properties.getProperty("jdbcDriverClass"));
        // StatFilter提供JDBC层的统计信息
        druidPlugin.addFilter(new StatFilter());
        // WallFilter的功能是防御SQL注入攻击
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        druidPlugin.addFilter(wallFilter);
        druidPlugin.start();
	}

	public void shutdown() throws SQLException {
		druidPlugin.stop();
	}
}