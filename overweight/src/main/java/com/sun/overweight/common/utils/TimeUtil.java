package com.sun.overweight.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 */
public class TimeUtil
{
	public static String getCurrentDate()
	{
		return getCurrentTime("yyyy-MM-dd");
	}

	/**
	 * 获得当前时间
	 * 
	 * @return 当前时间
	 */
	public static String getCurrentTime()
	{
		return getCurrentTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取当前时间
	 * 
	 * @param format
	 *            ：时间格式
	 * @return 当前时间
	 */
	public static String getCurrentTime(String format)
	{
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat(format);

		return simpleDataFormat.format(new Date());
	}

	public static String[] getDateArray(String startDate, String endDate)
	{
		List<String> dateList = new ArrayList<String>();

		try
		{
			SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date startTime = simpleDataFormat.parse(startDate);
			Date endTime = simpleDataFormat.parse(endDate);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startTime);

			while (calendar.getTime().compareTo(endTime) <= 0)
			{
				dateList.add(simpleDataFormat.format(calendar.getTime()));

				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return dateList.toArray(new String[0]);
	}

	/**
	 * 计算某月的天
	 */
	public static String[] getDayArray(String dateStr)
	{
		List<String> dayList = new ArrayList<String>();

		SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM");
		String currentDateStr = simpleDataFormat.format(new Date());

		if (currentDateStr.compareTo(dateStr) > 0)
		{
			try
			{
				Date date = simpleDataFormat.parse(dateStr);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				for (int index = 1; index <= dayCount; index++)
				{
					dayList.add(String.format("%1$02d", index));
				}
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Calendar calendar = Calendar.getInstance();

			int currentHour = calendar.get(Calendar.DAY_OF_MONTH);
			for (int index = 1; index <= currentHour; index++)
			{
				dayList.add(String.format("%1$02d", index));
			}
		}

		return dayList.toArray(new String[0]);
	}

	/**
	 * 计算某月有多少天
	 */
	public static int getDayCount(String dateStr)
	{
		try
		{
			SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date date = simpleDataFormat.parse(dateStr);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 计算某天的小时
	 */
	public static String[] getHourArray(String dateStr)
	{
		List<String> monthList = new ArrayList<String>();

		SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateStr = simpleDataFormat.format(new Date());

		if (currentDateStr.compareTo(dateStr) > 0)
		{
			for (int index = 1; index <= 24; index++)
			{
				monthList.add(String.format("%1$02d", index));
			}
		}
		else
		{
			Calendar calendar = Calendar.getInstance();

			int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
			for (int index = 1; index <= currentHour; index++)
			{
				monthList.add(String.format("%1$02d", index));
			}
		}

		return monthList.toArray(new String[0]);
	}

	public static Date getLastMonthTime()
	{
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MONTH, 1);

		return calendar.getTime();
	}

	public static Date getLastYearTime()
	{
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, -3);

		return calendar.getTime();
	}

	/**
	 * 计算某年的月份
	 */
	public static String[] getMonthArray(String year)
	{
		List<String> monthList = new ArrayList<String>();

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if (Integer.valueOf(year) == currentYear)
		{
			int currentMonth = calendar.get(Calendar.MONTH) + 1;
			for (int index = 1; index <= currentMonth; index++)
			{
				monthList.add(String.format("%1$02d", index));
			}
		}
		else if (Integer.valueOf(year) < currentYear)
		{
			for (int index = 1; index <= 12; index++)
			{
				monthList.add(String.format("%1$02d", index));
			}
		}

		return monthList.toArray(new String[0]);
	}

	public static String[] getMonthArray(String startMonthStr, String endMonthStr)
	{
		int startMonth = Integer.valueOf(startMonthStr);
		int endMonth = Integer.valueOf(endMonthStr);

		List<String> monthList = new ArrayList<String>();
		if (startMonth != 0 && endMonth != 0)
		{
			while (startMonth <= endMonth)
			{
				monthList.add(String.valueOf(String.format("%1$02d", startMonth)));
				startMonth++;
			}
		}
		return monthList.toArray(new String[0]);
	}

	public static Date getPreMonthTime()
	{
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MONTH, -1);

		return calendar.getTime();
	}

	public static Date getPreWeekTime()
	{
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, -7);

		return calendar.getTime();
	}

	public static int getDateSpace(String startDate, String endDate)
	{
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(toDate(startDate, "yyyy-MM-dd"));
		startCalendar.set(Calendar.HOUR_OF_DAY, 0);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(toDate(endDate, "yyyy-MM-dd"));
		endCalendar.set(Calendar.HOUR_OF_DAY, 0);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		
		// 得到两个日期相差的天数
		int days = ((int) (endCalendar.getTime().getTime() / 1000) - (int) (startCalendar.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	public static String[] getSeasonArray(String year)
	{
		List<String> seasonList = new ArrayList<String>();

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if (Integer.valueOf(year) == currentYear)
		{
			int currentMonth = calendar.get(Calendar.MONTH) + 1;
			if (currentMonth > 0)
			{
				seasonList.add("1");
			}
			if (currentMonth > 3)
			{
				seasonList.add("2");
			}
			if (currentMonth > 6)
			{
				seasonList.add("3");
			}
			if (currentMonth > 9)
			{
				seasonList.add("4");
			}
		}
		else if (Integer.valueOf(year) < currentYear)
		{
			for (int index = 0; index < 4; index++)
			{
				seasonList.add(String.valueOf(index + 1));
			}
		}

		return seasonList.toArray(new String[0]);
	}

	public static Date getTime(Date date, int beforePresent)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, beforePresent);

		return calendar.getTime();
	}

	public static Date getTime(int min)
	{
		return new Date(new Date().getTime() + min * 1000);
	}

	public static String[] getTimePeriodArray(String date)
	{
		List<String> timePeriodList = new ArrayList<String>();

		if (StringUtil.isNotEmpty(date))
		{
			String currentDate = getCurrentTime("yyyy-MM-dd");

			if (date.compareTo(currentDate) < 0)
			{
				for (int index = 0; index < 24; index++)
				{
					timePeriodList.add(String.format("%1$02d", index));
				}
			}
			else
			{
				Calendar calendar = Calendar.getInstance();
				int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

				for (int index = 0; index < currentHour + 1; index++)
				{
					timePeriodList.add(String.format("%1$02d", index));
				}
			}
		}

		return timePeriodList.toArray(new String[0]);
	}

	public static String[] getYearArray(String startYearStr, String endYearStr)
	{
		int startYear = Integer.valueOf(startYearStr);
		int endYear = Integer.valueOf(endYearStr);

		List<String> yearList = new ArrayList<String>();
		if (startYear != 0 && endYear != 0)
		{
			while (startYear <= endYear)
			{
				yearList.add(String.valueOf(startYear));
				startYear++;
			}
		}
		return yearList.toArray(new String[0]);
	}


	/**
	 * 将Date字符串形式转化为Date类型
	 * 
	 * @param dateStr
	 *            ：Date字符串形式
	 * @return 如果转化成功，返回Date类型；否则，返回null。
	 */
	public static Date toDate(String dateStr)
	{
		return toDate(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date字符串形式转化为Date类型
	 * 
	 * @param dateStr
	 *            ：Date字符串形式
	 * @param format
	 *            ：格式化字符串
	 * @return 如果转化成功，返回Date类型；否则，返回null。
	 */
	public static Date toDate(String dateStr, String format)
	{
		Date date = null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		try
		{
			if (StringUtil.isNotEmpty(dateStr))
			{
				date = simpleDateFormat.parse(dateStr);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 将日期转化为日期字符串
	 * 
	 * @param date
	 *            ：日期对象
	 * @return 格式化后的日期字符串
	 */
	public static String toString(Date date)
	{
		return toString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期转化为指定格式的字符串
	 * 
	 * @param date
	 *            ：日期对象
	 * @param format
	 *            ：时间格式
	 * @return 格式化后的日期字符串
	 */
	public static String toString(Date date, String format)
	{
		String dateString = "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		try
		{
			if (date != null)
			{
				dateString = simpleDateFormat.format(date);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return dateString;
	}
}
