package com.xieyue.jwt.common;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wx.dbm.dao.DaoException;
import com.wx.dbm.DbmFactory;
import com.wx.dbm.param.DbmParam;

/**
 * 数据库特性.
 * 
 * @author Charles
 * 
 */
public class DbUtil {
	/**
	 * 参数分隔符：" ∮ "
	 */
	public static final String PARAM_SEP = " ∮ ";

	/**
	 */
	protected static DateFormat dateFormatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String formatFullDate(Date date) {
		if (date == null) {
			return null;
		}
		return dateFormatFull.format(date);
	}

	public static Date parseFullDate(String date) {
		try {
			return dateFormatFull.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date toDate(String date) {
		try {
			return dateFormatFull.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Timestamp toTimestamp(String date) {
		try {
			return new Timestamp(dateFormatFull.parse(date).getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormatFull.format(date);
	}

	/**
	 * 根据函数参数值的个数，组成参数sql, 设置调用sql.
	 * 
	 * @param param
	 * @param name
	 * @throws SQLException
	 */
	public static void setSQLByValues(DbmParam param, String name) throws DaoException {
		String sql = "";
		try {
			Object[] values = param.getSqlParamValues();

			if (values.length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < values.length; i++) {
					sb.append(",?");
				}
				sql = sb.substring(1);
			}

			param.setSql(DbmFactory.getDbUtils().getProcedureSql(name, sql));
		} catch (SQLException e) {
			throw new DaoException("sql error:" + name, e);
		}
	}

}
