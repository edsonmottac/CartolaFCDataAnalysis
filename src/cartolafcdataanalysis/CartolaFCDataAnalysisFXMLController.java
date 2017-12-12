
package cartolafcdataanalysis;

import cartolafcdataanalysis.code.CartolaFCAnalyse;
import cartolafcdataanalysis.orm.Matches;
import cartolafcdataanalysis.orm.MyTeam;
import cartolafcdataanalysis.orm.Players;
import cartolafcdataanalysis.orm.Position;
import cartolafcdataanalysis.orm.Tabela;
import com.sun.javafx.charts.Legend;
import com.sun.swing.internal.plaf.metal.resources.metal;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;


public class CartolaFCDataAnalysisFXMLController implements Initializable {

    @FXML
    public Button btStart;
    public ProgressBar pb;
    
    public Label lblPontos;
    public Label lblSaldo;
    public Label lblMediaPontos;
    
    public ComboBox cmbOrcamento, cmbEsquema, cmbAlgoritmo, cmbAno, cmbLocal;
    
    public TreeTableView<Players> tableview;
    public TreeTableColumn<Players,String> col1;
    public TreeTableColumn<Players,String> col2;
    public TreeTableColumn<Players,String> col3;
    public TreeTableColumn<Players,String> col4;
    public TreeTableColumn<Players,String> col5;
    public TreeTableColumn<Players,String> col6;
    
    public TreeItem<Players> root;
    
    public AreaChart<Number,Number> areaChart;
    public XYChart.Series<Number,Number> serie_area_chart;
    
    public LineChart<String,Number> lineChart;
    public XYChart.Series<String,Number> sTotalPontos;
    public XYChart.Series<String,Number> sMediaPontos;
    public XYChart.Series<String,Number> sMediaTime;

    
    //public List<XYChart.Series<String,Number>> serie_temp_list_saldo;
    public List<XYChart.Series<String,Number>> serie_temp_list_media_pontos;
    public List<XYChart.Series<String,Number>> serie_temp_list_media_time;
    public Integer count_chart=0;
      
    @FXML
    private AnchorPane pn_diaplay_titulo;
    @FXML
    private Label lbltitle;
    @FXML
    private Label lblsubtitulo;
    @FXML
    private AnchorPane pn_diaplay_pontos;
    @FXML
    private AnchorPane pn_left_spli_2;
    @FXML
    public CheckBox chkChart;
    @FXML
    private HBox info0;
    @FXML
    private Pane info3;
    @FXML
    private Pane info1;
    @FXML
    private Pane info2;
    @FXML
    private Pane info4;

    
    @FXML
    public void Click_btStart(ActionEvent rb) throws SQLException, UnsupportedEncodingException, InterruptedException {

        List<Position> PositionList;
        List<Players> PlayersList;
        List<Players> PlayersListDistinctIDList;
        List<Tabela> TabelaList;
        
        
        String ESQUEMA_TATICO=cmbEsquema.getSelectionModel().getSelectedItem().toString();
        
        PositionList = (new Position()).getAll(ESQUEMA_TATICO);
        PlayersList = (new Players()).getAll();
        
        PlayersListDistinctIDList = (new Players()).getDistinctID((Integer) cmbAno.getSelectionModel().getSelectedItem());
        
        /* Recupera dados da tabela classificatória */
        TabelaList = (new Tabela()).getAll((Integer) cmbAno.getSelectionModel().getSelectedItem());
        
        CartolaFCAnalyse algotithm = new CartolaFCAnalyse();
        algotithm.PositionList=PositionList;
        algotithm.PlayersList=PlayersList;
        algotithm.PlayersDistinctIDList = PlayersListDistinctIDList;
        algotithm.TabelaList = TabelaList;
        
                     
        algotithm.FL_ORC_INICIAL = 100; //(float) cmbOrcamento.getSelectionModel().getSelectedItem();
        algotithm.NUM_YEAR = (Integer) cmbAno.getSelectionModel().getSelectedItem();
        algotithm.LOCAL_DA_PARTIDA  = cmbLocal.getSelectionModel().getSelectedItem().toString();
        algotithm.FATOR_POSICAO = (double) cmbOrcamento.getSelectionModel().getSelectedItem();
        algotithm.TOTAL_RODADAS = (new Players()).UltimaRodada(algotithm.NUM_YEAR);        
        
        
        /*  Fornce acesso aos controle */
        algotithm.classcontroler=this;
        
        /* Execulta algoritmo */
        if (cmbAlgoritmo.getSelectionModel().getSelectedItem().toString()=="Aleatório") {
            algotithm.RandomizedAlgorithm();
        } else {
            algotithm.StatistictAlgorithm();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Inicializacoes();
    }
    
    public void Inicializacoes() {
        /* carrega Combo */
        cmbOrcamento.getItems().add(0, 0.3);
        cmbOrcamento.getItems().add(1, 0.7);
        cmbOrcamento.getSelectionModel().select(0);
        
        cmbEsquema.getItems().add(0, "4-4-2");
        cmbEsquema.getItems().add(1, "4-3-3");
        cmbEsquema.getSelectionModel().select(0);
        
        cmbAlgoritmo.getItems().add(0, "Aleatório");
        cmbAlgoritmo.getItems().add(1, "Estatístico");
        cmbAlgoritmo.getSelectionModel().select(0);
        
        cmbLocal.getItems().add(0, "Casa");
        cmbLocal.getItems().add(1, "Fora");
        cmbLocal.getSelectionModel().select(0);
        
        cmbAno.getItems().add(0, 2014);
        cmbAno.getItems().add(1, 2015);
        cmbAno.getItems().add(2, 2016);
        cmbAno.getItems().add(3, 2017);
        cmbAno.getSelectionModel().select(0);
        
        Players PLRTView = new Players();
        PLRTView.setRodada_id(0);
        root = new TreeItem<>(PLRTView);
        
        col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper((param.getValue().getValue().getRodada_id()==0) ? "" : String.valueOf("R" + param.getValue().getValue().getRodada_id())));
        col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNome()));
        col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNome_posicao()));
        col4.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(String.valueOf((new DecimalFormat("0.00")).format(param.getValue().getValue().getPontos_num()))));
        col5.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> {
            return new ReadOnlyStringWrapper(String.valueOf(param.getValue().getValue().getPreco_num()));
        });
    
        
        /* Carrega Séries do Grafico */
        sMediaPontos = new XYChart.Series<>();
        lineChart.getData().add(sMediaPontos);
        sMediaPontos.setName("Média de Pontos");
    
        sMediaTime = new XYChart.Series<>();
        lineChart.getData().add(sMediaTime);    
        sMediaTime.setName("Média do Time");
         
         /* COMPARATIVOS */
         serie_temp_list_media_pontos = new ArrayList<>();
         serie_temp_list_media_time = new ArrayList<>();
         
         for (int instance=0;instance<6;instance++) { /*permite até seis sobreposições */
             
             serie_temp_list_media_pontos.add(new XYChart.Series<>());
             lineChart.getData().add(serie_temp_list_media_pontos.get(instance)); 
             
             serie_temp_list_media_time.add(new XYChart.Series<>());
             lineChart.getData().add(serie_temp_list_media_time.get(instance)); 
         }
        Legend legend = (Legend)lineChart.lookup(".chart-legend");
        legend.getItems().removeIf(item-> item.getText()== null);
    }
    
      
    
}

                

         
//<editor-fold defaultstate="collapsed" desc="----- TRASH -----">


//      
//        rodada1.getChildren().setAll(player1_c1,player1_c2);
//        
//        col1.setCellValueFactory(new TreeItemPropertyValueFactory<String, String>("EDSON MOTTA"));
//        col2.setCellValueFactory(new TreeItemPropertyValueFactory<String, String>("GOLEIRO"));
//        
      
      
        //    TreeItem<String>item_l1 = new TreeItem<>("NASM");
//    TreeItem<String>item_l2 = new TreeItem<>("ASSEMBLY");
//    
//    TreeItem<String>parentl = new TreeItem<>("LOW");
//
//    TreeItem<String>item_h1 = new TreeItem<>("XPTO");
//    TreeItem<String>item_h2= new TreeItem<>("VB.NET");
//    TreeItem<String>item_h3= new TreeItem<>("VB.NET2");
//    
//    TreeItem<String>parent2 = new TreeItem<>("HIGHT");
    
    
//    TreeItem<String>rodada1 = new TreeItem<>("Rodada1");
//    TreeItem<String>player1_c1 = new TreeItem<>("EDSON MOTTA");
//    TreeItem<String>player1_c2 = new TreeItem<>("GOLEIRO");
//    TreeItem<String>player1_c3 = new TreeItem<>("10,50");
//    TreeItem<String>player1_c4 = new TreeItem<>("$15,25");
    
     //Person person2 = new Person("FirstName2", "LastName2", LocalDate.of(1956, 12, 17));
//        TreeItem<Person> person6Node = new TreeItem<>(person6);
//       person6Node.getChildren().addAll(new TreeItem<>(person11), new TreeItem<>(person12));

      
      //col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new SimpleStringProperty(param.getValue().getValue()));
//      col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new SimpleStringProperty(param.getValue().getValue()));
//      col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new SimpleStringProperty(param.getValue().getValue()));
//      col4.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new SimpleStringProperty(param.getValue().getValue()));
      
      
        
//      parentl.getChildren().setAll(item_l1,item_l2);
//      parent2.getChildren().setAll(item_h1,item_h2);
//      root.getChildren().setAll(parentl,parent2);
//      col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new SimpleStringProperty(param.getValue().getValue()));
//      tableview.setRoot(root);
      
 //</editor-fold>  
