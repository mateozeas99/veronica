package com.rolandopalermo.facturacion.ec.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.sri.Signer;

public class SignerUtils {

	public static byte[] signXML(byte[] cotenido, String rutaCertificado, String passwordCertificado) throws IOException, VeronicaException {
		// Actividad 1.- Generar archivo temporales para el XML y su respectivo archivo
		// firmado
		String rutaArchivoXML = UUID.randomUUID().toString();
		File temp = File.createTempFile(rutaArchivoXML, ".xml");
		rutaArchivoXML = temp.getAbsolutePath();
		String rutaArchivoXMLFirmado = UUID.randomUUID().toString();
		File tempFirmado = File.createTempFile(rutaArchivoXMLFirmado, ".xml");
		rutaArchivoXMLFirmado = tempFirmado.getAbsolutePath();
		// Actividad 2.- Guardar datos en archivo xml
		try (FileOutputStream fos = new FileOutputStream(rutaArchivoXML)) {
			fos.write(cotenido);
		}
		// Actividad 3.- Firmar el archivo xml creado temporalmente
		Signer firmador = new Signer(rutaArchivoXML, rutaArchivoXMLFirmado, rutaCertificado, passwordCertificado);
		firmador.firmar();
		// 4.- Obtener el contenido del archivo XML
		Path path = Paths.get(rutaArchivoXMLFirmado);
		byte[] data = Files.readAllBytes(path);
		if (!temp.delete() || !tempFirmado.delete()) {
			throw new VeronicaException("No se pudo eliminar los archivos temporales.");
		}
		return data;
	}

}
