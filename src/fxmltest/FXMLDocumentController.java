/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;

import Reporting.TableReport;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import fxmltest.computing.BasicStatisticsProfiler;
import fxmltest.computing.BasicStatisticsService;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.ColumnProfilingStats;
import fxmltest.computing.ColumnProfilingStatsRow;
import fxmltest.computing.ProfilingScheduler;
import fxmltest.computing.TableInfo;
import fxmltest.computing.TablesFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import static javafx.concurrent.Worker.State.CANCELLED;
import static javafx.concurrent.Worker.State.FAILED;
import static javafx.concurrent.Worker.State.SUCCEEDED;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import testhierarchie.Graphics.ProgressIndicatorGraph;
import testhierarchie.Graphics.ThresholdFormGrid;

/**
 *
 * @author Ashraf
 */
public class FXMLDocumentController implements Initializable {

        
    private Label label;
    private ArrayList<TableInfo> tables;
    private int tableNumber;
    private static final ObservableList<ColumnProfilingStatsRow> data
            = FXCollections.observableArrayList();
    private int selectedCol = 1;
    private static ProfilingScheduler scheduler = new ProfilingScheduler();

    @FXML
    private TreeView tableTreeView;
    @FXML
    private static TextArea sqlArea;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Label resultTableLabel;
    @FXML
    private PieChart resultsChart;
    @FXML
    private BarChart<?, ?> resultsHisto;
    @FXML
    private Color x4;
    @FXML
    private Font x3;
    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem refreshMenu;
    @FXML
    private MenuItem menuItemProfile;
    @FXML
    private TableColumn<?, ?> columnCol;
    @FXML
    private TableColumn<?, ?> countCol;
    @FXML
    private TableColumn<?, ?> nullCol;
    @FXML
    private TableColumn<ColumnProfilingStatsRow, Integer> distinctCol;
    @FXML
    private TableColumn<?, ?> minCol;
    @FXML
    private TableColumn<?, ?> maxCol;
    @FXML
    private TableColumn<?, ?> violDistinct;
    @FXML
    private TableColumn<?, ?> violNotNull;
    @FXML
    private TableColumn<?, ?> violMin;
    @FXML
    private TableColumn<?, ?> violMax;
    @FXML
    private VBox dashboardVbox;
    private ProgressIndicatorGraph completenessProgress;
    
    
    
    
    @FXML
    private TableView tableView;
    @FXML
    private JFXSpinner spinner;
    private static BasicStatisticsProfiler profiler;
    @FXML
    private MenuItem sendMailMenu;
    private static FXMLDocumentController controller;
    
    public static FXMLDocumentController getController(){
        return controller;
    }
    private void handleButtonAction(ActionEvent event) {
        createLoginPopUp();
        label.setText("Hello World!");
    }
    
    @FXML
    private void handleNewMenuItem(ActionEvent event) {
        createLoginPopUp();
    }
    
    @FXML
    private void handleSendMailMenu(ActionEvent event) {
        //createLoginPopUp();
        //TODO
    }
    
    
    
    @FXML 
    private void refreshMenuOnAction(ActionEvent event){
        initializeTableTreeView();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stopSpinner();
        controller = this;
        initializeTableTreeView();
        initializeTable();
        sqlArea = new TextArea();
        sqlArea.setWrapText(true);
        completenessProgress = new ProgressIndicatorGraph(0, 100, 100);
        dashboardVbox.getChildren().add(completenessProgress);
        //BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(tables.get(1));
        //System.out.println(profiler.profileTableQuery());
        
    }

    
    public void initializeTableTreeView (){
        
        final TreeItem<String> treeRoot = new TreeItem<String>("Mes tables");
        ArrayList<TableInfo> tables = new ArrayList<TableInfo>();

        //tables = new TablesFactory("SYSTEM", "OracleAdmin1", "jdbc:oracle:thin:@localhost:1522:orcl").getTables();
        tables = new TablesFactory("hr", "hrpassword", "jdbc:oracle:thin:@localhost:1522:orcl").getTables();
        //tables = new TablesFactory().getTables();
        for (TableInfo tab: tables){
            TreeItem<String> treeTab = new TreeItem<String>(tab.getName());
            
            for (ColumnInfo col: tab.getColumns()){
                treeTab.getChildren().add(new TreeItem<String>(col.getName()+":"+col.getType()));
            }
            
            treeRoot.getChildren().add(treeTab);
        }
        this.getTableTreeView().setShowRoot(true);
        this.getTableTreeView().setRoot(treeRoot);
        this.tables = tables;
        tableTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem treeItem = (TreeItem)newValue;
                tableNumber = getTableNumber(treeItem.getValue().toString());
                System.out.println("Selected item is: " + treeItem.getValue());
            }
            
        });
        
     
    }
    
    private void initializeKeyboardShortcuts(){
        menuItemProfile.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
    }
    
    private void initializeTable(){
        
        columnCol.setCellValueFactory(
                new PropertyValueFactory<> ("columnName")
        );
        
        countCol.setCellValueFactory(
                new PropertyValueFactory<> ("nbLines")
        );
        
        nullCol.setCellValueFactory(
                new PropertyValueFactory<> ("nbNull")
        );
        
        distinctCol.setCellValueFactory(
                new PropertyValueFactory<> ("nbDistinct")
        );
        
        minCol.setCellValueFactory(
                new PropertyValueFactory<> ("min")
        );
        
        maxCol.setCellValueFactory(
                new PropertyValueFactory<> ("max")
        );
        
        violDistinct.setCellValueFactory(
                new PropertyValueFactory<> ("violDistinct")
        );
        
        violNotNull.setCellValueFactory(
                new PropertyValueFactory<> ("violDistinct")
        );
        
        violMax.setCellValueFactory(
                new PropertyValueFactory<> ("violMin")
        );
        
        violMax.setCellValueFactory(
                new PropertyValueFactory<> ("violMax")
        );
        
        this.tableView.setItems(getData());
        
        
//        distinctCol.setCellFactory(column -> {
//            return new TableCell<ColumnProfilingStatsRow, Integer>() {
//                @Override
//                protected void updateItem(Integer item, boolean empty) {
//                    super.updateItem(item, empty);
//                    int selectedCol = tableView.getSelectionModel().getSelectedIndex();
//                    System.out.println(selectedCol);
//                    if (!data.isEmpty())
//                        if (data.get(selectedCol).getViolDistinctFlag() == 1)
//                                setStyle("-fx-background-color: red");
//
//                }
//            };
//        });
    }
    
    private void loadTable(){
        getData().clear();
        //data.addAll(this.profiler.profilingResult());
        
        for (ColumnProfilingStats d: this.profiler.profilingResult()){
            ColumnInfo c = tables.get(tableNumber).getColumnByName(d.getColumnName());
            getData().add(c.getStats());
        }
        
        stopSpinner();
        System.out.println("Table Loaded :3 !");
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedCol = tableView.getSelectionModel().getSelectedIndex();
                System.out.println(getData().get(selectedCol));
                loadChart();
            }
        });
        loadChart();
        
    }
    
    private void loadChart(){
        int selected = this.tableView.getSelectionModel().getSelectedIndex();

        ObservableList<PieChart.Data> pieChartData;
        String columnName;
        int nullCount = getData().get(this.selectedCol).getNbNull();
        int count = getData().get(this.selectedCol).getNbLines();

        pieChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("Null", (float) (nullCount)/count),
               new PieChart.Data("Not null", (float) (count-nullCount)/count ));
        System.out.print("Null: ");
        System.out.println(((float) nullCount)/count);
        System.out.print("Not Null: ");
        System.out.println((float) (count - nullCount)/count);
        //System.out.println("Not Null = "+ (count - nullCount)/count);
         
        
        this.resultsChart.setData(pieChartData);
        this.resultsChart.setTitle(getData().get(this.selectedCol).getColumnName());
        
    }
    
    
    private int getTableNumber(String tableName){
        for (int i = 0; i < tables.size(); i++)
            if (tables.get(i).getName().equals(tableName))
                    return i;
        return -1;
    }
    
    private void createLoginPopUp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            //Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.setTitle("New connexion");
//            stage.show();
            StackPane root = (StackPane) FXMLTest.getRoot();
            StackPane content;
            content = (StackPane) FXMLLoader.load(getClass().getResource("login.fxml"));
            content.setId("jfx-dialog-layout");
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void onContextMenuRequested(ContextMenuEvent event){
        TreeItem<String> ti;
        System.out.println(event.getSource());
        System.out.println(event.getSource().getClass());
        System.out.println(event.getSource().toString());
    }
    
    
    @FXML
    private void onMenuClick(ActionEvent event){
        System.out.println(event.getSource().toString());
        if (tableNumber>= 0){
            startSpinner();
            TableInfo table = tables.get(tableNumber);

            // We create a new Service that handles the profiling thread
            final Service<Void> calculateService = 
                        new BasicStatisticsService(table);
            
            //We      
            calculateService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
            switch (newValue) {
                case FAILED:
                case CANCELLED:
                case SUCCEEDED:
                    loadTable();
                    new TableReport(table);

                    completenessProgress.setProgress(getOverallCompleteness());
                break;
            }
        });

        calculateService.start();
        
        } else{
            System.out.println(tableNumber);
        }
    }
    

    
   
//    @FXML
//    private void handleTreeClick(ActionEvent event){
//        BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(tables.get(1));
//        System.out.println("Menu clicked");
//        System.out.println(profiler.profileTableQuery());
//    }
    
    @FXML
    private void setThreshold(ActionEvent event){
        // When the context menu "set threshold" is pressed
        if (tableNumber >= 0){
            StackPane root = (StackPane) FXMLTest.getRoot();
            StackPane content = new ThresholdFormGrid(tables.get(tableNumber));
            JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
            dialog.show();
        }
    }
    
    @FXML
    private void scheduleMenuAction(ActionEvent event){
        // When the context menu add to schedule is pressed
        if (tableNumber>= 0){
            TableInfo table = tables.get(tableNumber);

            // We create a new Service that handles the profiling thread
            final BasicStatisticsService calculateService = 
                        new BasicStatisticsService(table);
            
            //We      
            calculateService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
            switch (newValue) {
                case FAILED:
                case CANCELLED:
                case SUCCEEDED:
                    loadTable();
                    new TableReport(table);
                    completenessProgress.setProgress(getOverallCompleteness());
                break;
            }
        });
            scheduler.addTask(calculateService);
        } else{
            System.out.println(tableNumber);
        }
    }
    
    @FXML
    private void startScheduler(ActionEvent event){
        scheduler.start();
    }
    

    /**
     * @return the tableTreeView
     */
    public TreeView getTableTreeView() {
        return tableTreeView;
    }

    /**
     * @param tableTreeView the tableTreeView to set
     */
    public void setTableTreeView(TreeView tableTreeView) {
        this.tableTreeView = tableTreeView;
    }
    
    public void startSpinner(){
        this.spinner.setVisible(true);
    }
    
    public void stopSpinner(){
        this.spinner.setVisible(false);
    }

    /**
     * @return the data
     */
    public ObservableList<ColumnProfilingStatsRow> getData() {
        return data;
    }
    
    
    public float getOverallCompleteness(){
        float totalNotNull = 0;
        float totalLines = 0;
        for (TableInfo table: this.tables){
            for (ColumnInfo columnInfo: table.getColumns()){
                ColumnProfilingStatsRow stat = columnInfo.getStats();    
                totalNotNull += stat.getNbLines() - stat.getNbNull();
                totalLines += stat.getNbLines();
            }
        }
        if (totalLines == 0)
            return -1;
        return totalNotNull / totalLines;
    }
    
    

    /**
     * @return the mainStage
     */

    public static void setProfiler(BasicStatisticsProfiler profiler) {
            FXMLDocumentController.profiler = profiler;
        }
    
    public static void setSqlAreaText(String text){
        sqlArea.setText(text);
    }
    
    
   
    
}
