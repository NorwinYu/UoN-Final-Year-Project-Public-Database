package edu.mum.library.dataaccess.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import edu.mum.library.common.ReflectionHelper;
import edu.mum.library.dataaccess.base.BaseDao;

public class PersistanceManager {

	public static final String OUTPUT_DIR = // System.getProperty("user.dir") +
			"library.bin";
	private static Database library = (Database) readDatabase();

	public static Database getLibrary() {
		return library;
	}

	public static void saveDatabase() {
		try {

			// Store Serialized User Object in File
			FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_DIR);
			ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);
			output.writeObject(getLibrary());

			output.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Database readDatabase() {
		try {

			// Read from the stored file
			FileInputStream fileInputStream = new FileInputStream(new File(OUTPUT_DIR));
			ObjectInputStream input = new ObjectInputStream(fileInputStream);
			Object result = input.readObject();
			input.close();

			return (Database)result;

		} catch (FileNotFoundException e) {
//			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return new Database();
	}

	public static <T, ID> List<T> getEntityAllData(BaseDao<T, ID> dao) {
		List<T> allData = ReflectionHelper.readField(getLibrary(), dao.getTableName());
		return allData;
	}


}
