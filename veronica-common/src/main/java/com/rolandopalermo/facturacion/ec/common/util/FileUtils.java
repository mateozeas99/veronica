package com.rolandopalermo.facturacion.ec.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;

public class FileUtils {

	private static final Logger logger = LogManager.getLogger(FileUtils.class);

	/**
	 * Utility classes should not have a public constructor.
	 */
	private FileUtils() {
	}

	public static byte[] convertirObjAXML(Object doc) throws VeronicaException {
		try {
			File temp = File.createTempFile(UUID.randomUUID().toString(), ".xml");
			String nombreArchivo = temp.getAbsolutePath();
			JaxbUtils.marshall(doc, nombreArchivo);
			Path path = Paths.get(nombreArchivo);
			byte[] data = Files.readAllBytes(path);
			if (!temp.delete()) {
				throw new VeronicaException("No se pudo eliminar el archivo temporal.");
			}
			return data;
		} catch (VeronicaException | IOException | JAXBException e) {
			logger.error("convertirObjAXML", e);
			throw new VeronicaException(e.getMessage());
		}

	}

}