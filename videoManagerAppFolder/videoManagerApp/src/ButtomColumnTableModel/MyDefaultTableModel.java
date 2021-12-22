package ButtomColumnTableModel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import JazzLibraryClassies.VideoDatabaseFeeder;

/**
*
* @author nick
*/
public class MyDefaultTableModel extends DefaultTableModel{
	//kai auto einai to custo JTableModel pou xrisimopoio , gia na  balo ta koumpia pou thelo mesa

	public MyDefaultTableModel(Object[][] data, Object[] columnNames) {
        setDataVector(data, columnNames);
    }
	
	public VideoDatabaseFeeder getVideoAtRow(int row) {
        Vector<Object> rowVector = dataVector.elementAt(row);

        VideoDatabaseFeeder selectedRowVideo = new VideoDatabaseFeeder();
        selectedRowVideo.setArtist_name((String) rowVector.elementAt(0));
        selectedRowVideo.setArtist_instrument((String) rowVector.elementAt(1));
        selectedRowVideo.setVideo_link((String) rowVector.elementAt(2));
        selectedRowVideo.setVideo_name((String) rowVector.elementAt(3));
        selectedRowVideo.setVideo_duration((String) rowVector.elementAt(4));
        selectedRowVideo.setVideo_type((String) rowVector.elementAt(5));
        selectedRowVideo.setVideo_id((String) rowVector.elementAt(6));
        
        return selectedRowVideo;
    }
	
	public int getIndexOf(int row) {
        @SuppressWarnings("unchecked")
        Vector<Object> rowVector = dataVector.elementAt(row);

        int rowIndex = dataVector.indexOf(rowVector);
        
        return rowIndex;
    }
}