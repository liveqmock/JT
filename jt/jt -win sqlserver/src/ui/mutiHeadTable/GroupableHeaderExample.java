package ui.mutiHeadTable;
/* (swing1.1beta3)
 *example from 
 http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html 
 *
 */

/* (swing1.1beta3)
 *
 * |-----------------------------------------------------|
 * |        |       Name      |         Language         |
 * |        |-----------------|--------------------------|
 * |  SNo.  |        |        |        |      Others     |
 * |        |   1    |    2   | Native |-----------------|
 * |        |        |        |        |   2    |   3    |  
 * |-----------------------------------------------------|
 * |        |        |        |        |        |        |
 *
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;


/**
 * @version 1.0 11/09/98
 */
public class GroupableHeaderExample extends JFrame {

  GroupableHeaderExample() {
    super( "Groupable Header Example" );

    DefaultTableModel dm = new DefaultTableModel();
    dm.setDataVector(new Object[][]{
      {"119","foo","bar","ja","ko","zh"},
      {"911","bar","foo","en","fr","pt"}},
    new Object[]{"SNo.","AAA","BBB","Native","CCC","DDD"});

    JTable table = new JTable( dm ) {
      @Override
	protected JTableHeader createDefaultTableHeader() {
          return new GroupableTableHeader(columnModel);
      }
    };
    table.getColumnModel().setColumnMargin(20);
    TableColumnModel cm = table.getColumnModel();
    
    ColumnGroup g_lang = new ColumnGroup("Language");
    ColumnGroup g_other = new ColumnGroup("Others");
    ColumnGroup g_name = new ColumnGroup("Name");
    ColumnGroup g_name1 = new ColumnGroup("Name");
    ColumnGroup g_name2 = new ColumnGroup("Name");
    g_name2.add(cm.getColumn(1));
    g_name2.add(cm.getColumn(2));
    g_name.add(g_name1);
    g_name1.add(g_name2);
    g_other.add(cm.getColumn(3));
    g_other.add(cm.getColumn(4));
    
    g_other.add(g_name);
    g_lang.add(g_other);
    
    g_lang.add(cm.getColumn(5));
    GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
    header.addColumnGroup(g_lang);
    JScrollPane scroll = new JScrollPane( table );
    getContentPane().add( scroll );
    setSize( 600, 520 );   
  }

  public static void main(String[] args) {
    GroupableHeaderExample frame = new GroupableHeaderExample();
    frame.addWindowListener( new WindowAdapter() {
      @Override
	public void windowClosing( WindowEvent e ) {
  System.exit(0);
      }
    });
    frame.setVisible(true);
  }
}

