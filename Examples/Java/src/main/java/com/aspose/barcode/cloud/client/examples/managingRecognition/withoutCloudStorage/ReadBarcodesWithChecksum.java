package com.aspose.barcode.cloud.client.examples.managingRecognition.withoutCloudStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.aspose.barcode.api.BarcodeApi;
import com.aspose.barcode.cloud.client.examples.managingRecognition.cloudStorage.ReadBarcodeFromAsposeCloudStorage;
import com.aspose.barcode.model.Barcode;
import com.aspose.barcode.model.BarcodeReader;
import com.aspose.barcode.model.BarcodeResponseList;
import com.aspose.barcode.model.BinarizationHints;
import com.aspose.barcode.model.ChecksumValidation;
import com.aspose.storage.api.StorageApi;

/**
 * This sample code allows you to read barcode using Aspose BarCode Cloud API.
 * You just need to use Aspose.Cells for Cloud API in any language of your
 * choice.
 * 
 * @author Farooq Sheikh
 */
public class ReadBarcodesWithChecksum {

	public static void main(String[] args) {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		// App SID
		String appSID = "";
		// App Key
		String apiKey = "";
		// output folder
		String outFolder = "c:\\temp\\";

		InputStream inputStream = ReadBarcodeFromAsposeCloudStorage.class
				.getClassLoader().getResourceAsStream(propFileName);
		try {
			if (inputStream != null) {
				prop.load(inputStream);
				appSID = prop.getProperty("app_sid");
				apiKey = prop.getProperty("api_key");
				outFolder = prop.getProperty("out_folder");

			} else {
				System.out.println("property file '" + propFileName
						+ "' not found in the classpath");
				System.exit(1);
			}
		} catch (IOException ioe) {
			System.out.println("property file '" + propFileName
					+ "' not found in the classpath");
			ioe.printStackTrace();
			System.exit(1);
		}

		// ExStart:1

		try {

			// Instantiate Aspose Storage Cloud API SDK
			StorageApi storageApi = new StorageApi(apiKey, appSID, true);

			// Instantiate Aspose BarCode Cloud API SDK
			BarcodeApi barcodeApi = new BarcodeApi(apiKey, appSID, true);

			// set input file name
			String name = "sample-barcode.jpeg";
			
			//The barcode type.
			//If this parameter is empty, autodetection of all supported types is used.
			String type = "";
			
			String folder = "";

			BarcodeReader body = new BarcodeReader();

			// Set if FNC symbol stripping should be performed. 
			body.setStripFNC(true);
			
			// Set mode for checksum validation during recognition
			body.setChecksumValidation(ChecksumValidation.ON);
			
			// Set special mode of barcode binarization
			body.setBinarizationHints(BinarizationHints.ComplexBackground);

			// upload files to aspose cloud storage
			storageApi.PutCreate(name, "", "", new File(
					ReadBarcodesWithChecksum.class.getResource("/" + name)
							.toURI()));

			// invoke Aspose.BarCode Cloud SDK API to recognition of a barcode
			// from file on server with parameters in body
			BarcodeResponseList apiResponse = barcodeApi
					.PutBarcodeRecognizeFromBody(name, type, folder, body);

			if (apiResponse != null && apiResponse.getStatus().equals("OK")) {

				for (Barcode barcode : apiResponse.getBarcodes()) {
					System.out.println("Codetext: " + barcode.getBarcodeValue()
							+ "\nType: " + barcode.getBarcodeType());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// ExEnd:1
	}

}
