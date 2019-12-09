package raulcastilla215alu.matrix;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import raulcastilla215alu.mytools.IOModule;

/**
 * Lee un archivo CSV y almacena la informaci�n
 * en una matriz.
 * 
 * @author Ra�l Castilla Bravo.
 */
public class Matrix {

	/*
	 * Atributos privados
	 */
	protected ArrayList<ArrayList<String>> matrix;
	
	/**
	 * Constructor. Genera una matriz vac�a.
	 */
	public Matrix() {
		this.matrix = new ArrayList<>();
	}
	
	/**
	 * Constructor. Crea la matriz y la inicializa con la informaci�n
	 * del archivo CSV de la ruta pasada por par�metro.
	 * 
	 * @param path Ruta a un archivo CSV.
	 */
	public Matrix(String path) {
		this.matrix = new ArrayList<>();
		
		readCSV(path, ',');
	}
	
	/**
	 * Constructor. Crea la matriz y la inicializa con la informaci�n
	 * del archivo CSV de la ruta pasada por par�metro. Si removeHeader
	 * es true, se elimina la cabecera del csv.
	 * 
	 * @param path Ruta a un archivo CSV.
	 * @param sep Caracter de separaci�n del fichero csv.
	 */
	public Matrix(String path, char sep) {
		this.matrix = new ArrayList<>();
		
		readCSV(path, sep);
	}
	
	public void toCSV(String path) {
		IOModule.write(path, this.toString(), false);
	}
	
	/**
	 * Lee el archivo CSV de la ruta pasada por par�metro y lo
	 * carga en la matriz de la clase.
	 * 
	 * @param path Ruta a un archivo CSV.
	 */
	@SuppressWarnings("deprecation")
	protected void readCSV(String path, char sep) {
		
		// Variables locales
		Reader reader = null;
		CSVReader csvReader = null;
		
		try {
			// Lectura de archivo csv
			reader = Files.newBufferedReader(Paths.get(path));
			csvReader = new CSVReader(reader, sep);
			List<String[]> records = csvReader.readAll();
			
			// Por cada fila le�da...
			ArrayList<String> aux_row = null;
			for(int i = 0; i < records.size(); i++) {
				aux_row = new ArrayList<>();
				String[] row = records.get(i);
				
				// Por cada columna de la fila...
				for(int j = 0; j < row.length; j++) {
					aux_row.add(row[j]);
				}
				
				// Inclu�mos la nueva fila en la matriz
				matrix.add(aux_row);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(csvReader != null) {
				try {
					csvReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * A�ade una nueva fila a la matriz.
	 * @param newRow Fila a a�adir.
	 */
	protected void addRow(ArrayList<String> row) {
		try {
			if(!this.matrix.isEmpty() && this.matrix.get(0).size() != row.size()) {
				throw new SizeException("The length of the row must agree with the width of the matrix");
			}
			
			@SuppressWarnings("unchecked")
			ArrayList<String> newRow = (ArrayList<String>) row.clone();
				
			this.matrix.add(newRow);
			
		} catch(SizeException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A�ade una nueva fila en la matriz en la posici�n 
	 * indicada por par�metro desplazando hacia abajo
	 * la fila de dicha posici�n.
	 * 
	 * @param index Posici�n en la que a�adir la fila.
	 * @param row Fila a a�adir.
	 */
	protected void addRow(int index, ArrayList<String> row) {
		try {
			if(!this.matrix.isEmpty() && this.matrix.get(0).size() != row.size()) {
				throw new SizeException("The length of the row must agree with the width of the matrix");
			}
			
			@SuppressWarnings("unchecked")
			ArrayList<String> newRow = (ArrayList<String>) row.clone();
				
			this.matrix.add(index, newRow);
			
		} catch(SizeException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A�ade una nueva columna a la matriz.
	 * @param newColumn Columna a a�adir.
	 */
	protected void addColumn(ArrayList<String> column) {
		
		try {
			if(!matrix.isEmpty() && matrix.size() != column.size()) {
				throw new SizeException("The length of the column must agree with the height of the matrix");
			}
			
			@SuppressWarnings("unchecked")
			ArrayList<String> newColumn = (ArrayList<String>)column.clone();
			
			if(matrix.isEmpty()) {
				for(int indexRow = 0; indexRow < newColumn.size(); indexRow++) {
					matrix.add(new ArrayList<>());
				}
			}
			
			for(int indexRow = 0; indexRow < newColumn.size(); indexRow++) {
				matrix.get(indexRow).add(newColumn.get(indexRow));
			}
				
		} catch (SizeException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A�ade una nueva columna a la matriz en la posici�n indicada
	 * por par�metro desplazando a la derecha la columna de dicha posici�n.
	 * 
	 * @param index �ndice de la columna a a�adir.
	 * @param newColumn Columna a a�adir.
	 */
	protected void addColumn(int index, ArrayList<String> column) {
		
		try {
			if(!matrix.isEmpty() && matrix.size() != column.size()) {
				throw new SizeException("The length of the column must agree with the height of the matrix");
			}
			
			@SuppressWarnings("unchecked")
			ArrayList<String> newColumn = (ArrayList<String>)column.clone();
			
			if(matrix.isEmpty()) {
				for(int indexRow = 0; indexRow < newColumn.size(); indexRow++) {
					matrix.add(index, new ArrayList<>());
				}
			}
			
			for(int indexRow = 0; indexRow < newColumn.size(); indexRow++) {
				matrix.get(indexRow).add(index, newColumn.get(indexRow));
			}
				
		} catch (SizeException ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Elimina la fila indicada en el �ndice.
	 * @param indexRow �ndice de la fila a eliminar.
	 */
	protected void removeRow(int indexRow) {
		this.matrix.remove(indexRow);
	}
	
	/**
	 * Elimina la columna indicada en el �ndice.
	 * @param indexColumn �ndice de la columna a eliminar.
	 */
	protected void removeColumn(int indexColumn) {
		for(int indexRow = 0; indexRow < matrix.size(); indexRow++) {
			this.matrix.get(indexRow).remove(indexColumn);
		}
	}
		
	/**
	 * Establece el valor de una celda concreta
	 * 
	 * @param row �ndice de la fila a modificar.
	 * @param column �ndice de la columna a modificar.
	 * @param element elemento a introducir en la celda
	 */
	protected void set(int row, int column, String element) {
		this.matrix.get(row).set(column, element);
	}
	
	/**
	 * Devuelve el valor almacenado en la fila y columna
	 * indicados.
	 * 
	 * @param row Fila a indexar. 
	 * @param column Columna a indexar.
	 * @return Valor situado en la fila y columna indicadas.
	 */
	protected String get(int row, int column) {
		return this.matrix.get(row).get(column);
	}
	
	/**
	 * Devuelve el ancho de la matriz.
	 * @return Ancho de la matriz.
	 */
	protected int getWidth() {
		if(matrix.isEmpty()) {
			return 0;
		}
		return matrix.get(0).size();
	}
	
	/**
	 * Devuelve el alto de la matriz.
	 * @return Alto de la matriz.
	 */
	protected int getHeight() {
		if(matrix.isEmpty()) {
			return 0;
		} 
		return matrix.size();
	}
	
	/**
	 * Devuelve la fila indicada en el �ndice.
	 * 
	 * @param row �ndice de fila.
	 * @return Fila indicada en el �ndice.
	 */
	@SuppressWarnings("unchecked")
	protected ArrayList<String> getRow(int row){
		if(matrix.isEmpty()) {
			return null;
		}
		return (ArrayList<String>)matrix.get(row).clone();
	}
	
	/**
	 * Devuelve la columna indicada en el �ndice.
	 * 
	 * @param column �ndice de columna.
	 * @return Columna indicada en el �ndice.
	 */
	protected ArrayList<String> getColumn(int column){
		if(matrix.isEmpty()) {
			return null;
		}
		
		ArrayList<String> aux = new ArrayList<>();
		for(int indexRow = 0; indexRow < matrix.size(); indexRow++) {
			aux.add(matrix.get(indexRow).get(column));
		}
		return aux;
	}
		
	/**
	 * Devuelve la matriz de datos como matriz de n�meros
	 * reales. 
	 * 
	 * @return Devuelve la matriz de datos como matriz de n�meros
	 * reales. 
	 */
	protected double[][] castStr2Double(){
		if(matrix.isEmpty()) return null;
		
		double[][] doubleMatrix = new double[matrix.size()][matrix.get(0).size()];
		
		for(int indexRow = 0; indexRow < matrix.size(); indexRow++) {
			for(int indexColumn = 0; indexColumn < matrix.get(0).size(); indexColumn++) {
				doubleMatrix[indexRow][indexColumn] = Double.parseDouble(matrix.get(indexRow).get(indexColumn));
			}
		}
		return doubleMatrix;
	}
	
	/**
	 * Convierte un array de strings en un array de valores num�ricos reales.
	 * @param array Array de strings.
	 * @return Array de valores num�ricos reales.
	 */
	protected static double[] castStr2Double(ArrayList<String> array) {
		double[] doubleArray = new double[array.size()];
		
		for(int i = 0; i < array.size(); i++) {
			doubleArray[i] = Double.parseDouble(array.get(i));
		}
		return doubleArray;
	}
	
	/**
	 * Devuelve un String con la informaci�n almacenada en 
	 * la matriz.
	 */
	@Override
	public String toString() {
		String str = "";
		
		if(!matrix.isEmpty()) {
			for(int indexRow = 0; indexRow < matrix.size(); indexRow++) {
				for(int indexColumn = 0; indexColumn < matrix.get(indexRow).size(); indexColumn++) {
					str += matrix.get(indexRow).get(indexColumn).toString();
					if(indexColumn < matrix.get(indexRow).size() - 1) {
						 str += ",";
					}
				}
				str += "\n";
			}
		}

		return str;
	}
	
}