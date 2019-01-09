package com.rolandopalermo.facturacion.ec.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;

public class DateUtils {

	private static final Logger logger = Logger.getLogger(DateUtils.class);

	/**
	 * Utility classes should not have a public constructor.
	 */
	private DateUtils() {
	}

	public static String convertirGreggorianToDDMMYYYY(String fecha) throws VeronicaException {
		try {
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			date = sdf.parse(fecha);
			DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			fecha = formatoFecha.format(date);
			return fecha;
		} catch (ParseException e) {
			logger.error("convertirGreggorianToDDMMYYYY", e);
			throw new VeronicaException(e.getMessage());
		}
	}

	public static String convertirTimestampToDate(Timestamp timestamp) {
		Date date = new Date(timestamp.getTime());
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatoFecha.format(date);
	}

	public static Date getFechaFromStringddMMyyyy(String fecha) throws VeronicaException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(fecha);
			return date;
		} catch (ParseException e) {
			logger.error("getFechaFromStringddMMyyyy", e);
			throw new VeronicaException(e.getMessage());
		}
	}

}