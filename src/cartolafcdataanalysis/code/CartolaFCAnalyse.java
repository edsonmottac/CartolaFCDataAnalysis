
package cartolafcdataanalysis.code;

import cartolafcdataanalysis.CartolaFCDataAnalysisFXMLController;
import cartolafcdataanalysis.orm.Matches;
import cartolafcdataanalysis.orm.MyTeam;
import cartolafcdataanalysis.orm.Players;
import cartolafcdataanalysis.orm.Position;
import cartolafcdataanalysis.orm.Tabela;
import cartolafcdataanalysis.util.MyChart;
import cartolafcdataanalysis.util.MyNumber;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;


public class CartolaFCAnalyse {
    
    
    
    /* Dados recebidos */
    public float FL_ORC_INICIAL=0;
    public Integer NUM_YEAR = 0;
    public Integer TOTAL_RODADAS = 0;
    public String LOCAL_DA_PARTIDA="";
    public double FATOR_POSICAO = 0;
    
    //public List<Matches> MatchesList;
    public List<Position> PositionList;
    public List<Players> PlayersList;
    public List<Players> PlayersDistinctIDList;
    public List<Tabela> TabelaList;
    public List<MyChart> myChart;
    
    public List<MyTeam> MyTeamList = new ArrayList<MyTeam>();   
    public MyTeam myTeam = new MyTeam();
    
    public TreeItem<Players> subroot;
   
    public Integer PB_VALUE = 0 ;
    
    public CartolaFCDataAnalysisFXMLController classcontroler;
    
     
    //<editor-fold defaultstate="collapsed" desc="----- CHAMADAS PARA THREAD -----">
    
    Task<Void> RandomizedAlgorithmTask = new Task<Void>() {
                                                @Override
                                                protected Void call() throws Exception {
                                                   InicializaTela();
                                                    Algorithm("rdm");
                                                    return null;
                                                }
                                            };
    Task<Void> StatistictAlgorithmTask = new Task<Void>() {
                                                @Override
                                                protected Void call() throws Exception {
                                                   InicializaTela();
                                                    Algorithm("sta");
                                                    return null;
                                                }
                                            };
    //</editor-fold>      
    
    //<editor-fold defaultstate="collapsed" desc="----- ANALYSIE'S ALGORITHM -----">
    
    
    public void RandomizedAlgorithm() throws SQLException, InterruptedException {
        Thread th = new Thread(RandomizedAlgorithmTask);
        th.start();
        Thread.sleep(2000);
    }
        
    public void StatistictAlgorithm() throws SQLException, InterruptedException {
        Thread th = new Thread(StatistictAlgorithmTask);
        th.start();
        Thread.sleep(2000);
    }
    
    public void Algorithm(String tipo) throws SQLException, InterruptedException {
        
                float NUM_SALDO_ANTERIOR=0;
                float NUM_SALDO_ATUAL=0;
                float NUM_SALDO_VARIACAO=0;
                float NUM_ATIVO_TIME=0;
                
                float PONTOS_TOTAL_CUMULATIVO=0;
                float PONTO_MEDIA_GERAL=0;
                
                List<Players> PlayerSelected = new ArrayList<Players>();
                Players PlayerTemp = null;
                
                List<MyTeam> MyTeamList = new ArrayList<MyTeam>();
                MyTeam myTeam;
                
                /* Filtra por ano  e local da parttida */ 
                 List<Players> PlayersByYear  = PlayersList.stream().filter(p -> (p.getAno()== NUM_YEAR.intValue()))
                                                                    .filter(p -> (p.getLocal_jogo().equals(String.valueOf(LOCAL_DA_PARTIDA))))
                                                                    .collect(Collectors.toList());

                for(int num_rodada=0;num_rodada<TOTAL_RODADAS;num_rodada++) {
                    
                    if ((num_rodada+1)==1) {
                        NUM_SALDO_ATUAL=FL_ORC_INICIAL;
                    } else {
                        NUM_SALDO_ATUAL = (NUM_SALDO_ATUAL + NUM_ATIVO_TIME + NUM_SALDO_VARIACAO);
                    }
                    
                    /* Distribuição por jogador (apenas na execução aleatoria */
                    float   VL_ORC_BY_PLAYER = (NUM_SALDO_ATUAL / 12);
                   
                    /* Calcula médida de jogadores nas rodadas anteriores  (só no estatístico) */
                    PlayersByYear = CalculaPlayersMediaRodadasAnteriores(PlayersByYear,num_rodada+1);
                    
                    /* Calcula medias e percentual de distribuição do orçamento */
                    List<Position> PositionListByMedias = CalculaPositionMediaRodadasAnteriores(PositionList,num_rodada+1);
                    
                    for(Position position: PositionListByMedias){ 
                        
                        /* Filtra por Posição  */
                        List<Players> PlayersByPosition = PlayersByYear.stream().filter(p -> p.getPosicao_id() == position.getPosicao_id()).collect(Collectors.toList());

                        
                        /* CALCULA SALDOS: Se for o algoritmo estatístico redistribui-se os valores em função do percentual médio de ganhos */
                        if (tipo!="rdm") {
                            System.out.println(NUM_SALDO_ATUAL);
                            System.out.println(position.getNum_percentual());
                            System.out.println(position.getNu_jogadores());
                            
                            VL_ORC_BY_PLAYER =  (((NUM_SALDO_ATUAL * position.getNum_percentual()) / 100) );
                        }
                       
                        for (int num_players=0;num_players <= position.getNu_jogadores()-1;num_players ++) {
                            
                             if (tipo.equals("sta")) {
                            
                                /* Filtra jogadores com o maximo de valor disponível até o limite de VL_ORC_BY_PLAYER  */
                                //System.out.println("VALOR DISPOÍVEL: " + VL_ORC_BY_PLAYER);
                                final float v  = VL_ORC_BY_PLAYER;
                                PlayersByPosition  = PlayersByPosition.stream().filter(p -> (p.getPreco_num() <= v )).collect(Collectors.toList());

                                /* Ordena por média. O primeiro tera a maior média: Este jogador é o jogador ideal  */
                                Collections.sort(PlayersByPosition, new Comparator<Players>() {
                                    public int compare(Players obj1, Players obj2) {
                                        return Float.compare(obj2.getMedia_rodadas(), obj1.getMedia_rodadas());
                                    }
                                });
                                
                                /* Inclui o jogador em PlayerSelected */
                                PlayerTemp = PlayersByPosition.get(0);
                                final Integer key_temp=PlayerTemp.getAtleta_id();
                                PlayerSelected.add(PlayerTemp);
                                PlayersByPosition.removeIf(p -> p.getAtleta_id().equals(key_temp));


                             } else { /* Se for algoritmo randomico */
                            
                                  /* Filtra jogadores com o maximo de valor disponível até o limite de VL_ORC_BY_PLAYER  */
                                 final float v  = VL_ORC_BY_PLAYER;
                                 PlayersByPosition  = PlayersByPosition.stream().filter(p -> (p.getPreco_num() <= v )).collect(Collectors.toList());

                                 /* Ordena por preço. */
//                               Collections.sort(PlayersByPosition, new Comparator<Players>() {
//                                  public int compare(Players obj1, Players obj2) {
//                                      return Float.compare(obj2.getPreco_num(), obj1.getPreco_num());
//                                  }
//                               });

                                /* Inclui o jogador em PlayerSelected */
                                 try {
                                    Random rn = new Random();
                                    PlayerTemp = PlayersByPosition.get((rn.nextInt((PlayersByPosition.size()-1) - 1 + 1) + 1));
                                    final Integer key_temp=PlayerTemp.getAtleta_id();
                                    PlayerSelected.add(PlayerTemp);
                                    PlayersByPosition.removeIf(p -> p.getAtleta_id().equals(key_temp));
                                     
                                 } catch (Exception e) {
                                     System.err.println("ERRO ALGORITMO ALEATÓRIO " + e.getMessage());
                                 }
                                    



                            }
                             
                        }
                        PlayerTemp=null;
                } 
    
                /* Atualização de saldos e ativos */
                NUM_ATIVO_TIME = (float) PlayerSelected.stream().filter(o -> o.getPreco_num() > 0).mapToDouble(o -> o.getPreco_num()).sum();
                NUM_SALDO_ATUAL = (NUM_SALDO_ATUAL - NUM_ATIVO_TIME);

                /* Calcula a média historica de pontos */
                PONTOS_TOTAL_CUMULATIVO = (PONTOS_TOTAL_CUMULATIVO + (float) PlayerSelected.stream().filter(o -> o.getPontos_num()> 0).mapToDouble(o -> o.getPontos_num()).sum());
                final float media_pontos=(PONTOS_TOTAL_CUMULATIVO/(num_rodada+1));
                Platform.runLater(() -> classcontroler.lblMediaPontos.setText((new DecimalFormat("0.00")).format(media_pontos)));
                
                float media_time = (float) PlayerSelected.stream().filter(o -> o.getMedia_num()> 0).mapToDouble(o -> o.getMedia_num()).sum();
                
                /* Armazena novo Time */
                myTeam = new MyTeam();
                myTeam.setMyTeamList(PlayerSelected);
                myTeam.setTotal_media_time(media_time);
                myTeam.setTotal_pontos_time((float) PlayerSelected.stream().filter(o -> o.getPontos_num()> 0).mapToDouble(o -> o.getPontos_num()).sum());
                myTeam.setTotal_preco_time((float) PlayerSelected.stream().filter(o -> o.getPreco_num() > 0).mapToDouble(o -> o.getPreco_num()).sum());
                myTeam.setMedia_pontos_cumulativa(media_pontos);
                
                NUM_SALDO_VARIACAO = CalculaVariacao(PlayerSelected, num_rodada+2);
                
                myTeam.setTotal_saldo_anterior(NUM_SALDO_ANTERIOR);
                myTeam.setTotal_saldo_atual(NUM_SALDO_ATUAL);
                myTeam.setTotal_saldo_variacao(NUM_SALDO_VARIACAO);
                
                NUM_SALDO_ANTERIOR = NUM_SALDO_ATUAL;                    
                
                /* ATUALIZA JANELA! */
                classcontroler.lblPontos.setText("0");
                float SA= (NUM_SALDO_ATUAL + NUM_ATIVO_TIME + NUM_SALDO_VARIACAO);;
                //float TP= myTeam.getTotal_pontos_time();
                float TP= PONTOS_TOTAL_CUMULATIVO;
               
                Platform.runLater(() -> classcontroler.lblSaldo.setText((new DecimalFormat("0.00")).format(SA)));
                Platform.runLater(() -> classcontroler.lblPontos.setText((new DecimalFormat("0.00")).format(TP)));
                
                float PercentBar= Float.valueOf(num_rodada+1) / Float.valueOf(TOTAL_RODADAS);
                Platform.runLater(() -> classcontroler.pb.setProgress(PercentBar));
               
                /* Load Treeview */
                Players PLRTView = new Players();
                PLRTView.setRodada_id(num_rodada+1);
                //PLRTView.setMedia_num(((float) PlayerSelected.stream().filter(o -> o.getMedia_num()> 0).mapToDouble(o -> o.getMedia_num()).sum())/12);
                PLRTView.setPontos_num(((float) PlayerSelected.stream().filter(o -> o.getPontos_num()> 0).mapToDouble(o -> o.getPontos_num()).sum()));
                PLRTView.setPreco_num((float) PlayerSelected.stream().filter(o -> o.getPreco_num() > 0).mapToDouble(o -> o.getPreco_num()).sum());
                subroot = new TreeItem<>(PLRTView);
                
                classcontroler.root.getChildren().add(subroot);
                 
                TreeItem<Players> pItem  ;  
                for(Players player: PlayerSelected){
                    player.setRodada_id(0);
                    System.out.println(player.getNome());
                    if (player.getNome().equals("NA")) {
                        player.setNome(String.valueOf(player.getAtleta_id()));
                    }
                    pItem = new TreeItem<>(player);
                    subroot.getChildren().add(pItem);
                }
                 
                classcontroler.tableview.setRoot(classcontroler.root);
                classcontroler.tableview.setShowRoot(false);
                
                /* Carrega Gráficos */
                classcontroler.sMediaPontos.getData().add(new XYChart.Data<String,Number>("R" + num_rodada,media_pontos));
                classcontroler.sMediaTime.getData().add(new XYChart.Data<String,Number>("R" + num_rodada,media_time));
                
                /* LImpa time temporario para proxima execução */
                PlayerSelected.clear();
        }
        
                try {
                        if (classcontroler.chkChart.isSelected()) {
                          if (classcontroler.serie_temp_list_media_pontos.get(classcontroler.count_chart).getData().stream().count()==0) {
                              classcontroler.serie_temp_list_media_pontos.get(classcontroler.count_chart).getData().addAll(classcontroler.sMediaPontos.getData());
                          }
                           if (classcontroler.serie_temp_list_media_time.get(classcontroler.count_chart).getData().stream().count()==0) {
                              classcontroler.serie_temp_list_media_time.get(classcontroler.count_chart).getData().addAll(classcontroler.sMediaTime.getData());
                          }
                            classcontroler.count_chart+=1;
                        } else  {
                            classcontroler.serie_temp_list_media_pontos.get(classcontroler.count_chart).getData().clear();
                            classcontroler.serie_temp_list_media_time.get(classcontroler.count_chart).getData().clear();
                        }                            
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }       
    }
    
    //</editor-fold>      
    
    //<editor-fold defaultstate="collapsed" desc="----- SUPORTE -----">

    private List<Players> CalculaPlayersMediaRodadasAnteriores(List<Players> PList, Integer rodada) {
        
        Integer ID = 0;
        Integer ID_TEMP = 0;
        float total=0;
        float media=0;
                
        for(Players player: PList){
            
            //System.out.println(ID + "  "  + player.getAtleta_id());

            if (ID.intValue() != player.getAtleta_id()) {
                
                /* consulta novos dados */
                List<Players> FiltroAtletas  = PlayersList.stream().filter(p -> (p.getAtleta_id()== player.getAtleta_id().intValue()))
                                                   .filter(p -> (p.getAno()== NUM_YEAR.intValue()))
                                                   .collect(Collectors.toList());
                
                
                
                System.out.println("ID JOGADOR : " + player.getAtleta_id());
                
                /* Calcula média */
                for (int r=rodada;r>=1;r--) {

                    final Integer r1= r;

                    List<Players> FiltroDetalhe  = FiltroAtletas.stream().filter(p -> (p.getRodada_id() == r1)).collect(Collectors.toList());

                    for(Players pMedia: FiltroDetalhe){
                         //System.out.println(pMedia.getAtleta_id() + "  media: " + pMedia.getMedia_num());
                         total = total + pMedia.getMedia_num();
                    } 
                }
                
                media=(total/rodada);
                
                /* verifcia a posição do clube do jogador corrente */
                List<Tabela> tabela_posicao_clube = TabelaList.stream().filter(p-> p.getAno().equals(NUM_YEAR.intValue()))
                                                                        .filter(p-> p.getClube_id().equals(player.getClube_id().toString())).collect(Collectors.toList());
                if (tabela_posicao_clube.size() > 0) {
                    /* Aplica um fator a média do jogador. */ 
                    if (tabela_posicao_clube.get(0).getPosicao() != null) {
                         //System.out.println("POSICAO NA TABELA : " + tabela_posicao_clube.get(0).getPosicao());
                         //System.out.println("MEDIA : " + media);
                         media = media + ((float) (FATOR_POSICAO * (tabela_posicao_clube.get(0).getPosicao())));
                         //System.out.println("NOVA MEDIA : " + media);
                     } else {
                         //System.out.println("FORA DA TABELA ID = " +  player.getAtleta_id());
                    }
                } else {
                    System.out.println("FORA DA TABELA ID = " +  player.getAtleta_id());
                }
               
                
                player.setMedia_rodadas(media);                

                ID =  player.getAtleta_id();
            } else {
                 player.setMedia_rodadas(media);    
            }
            total=0;
        }
        
        return PList;
    }
    
    private List<Position> CalculaPositionMediaRodadasAnteriores(List<Position> PList, Integer rodada) {
          
          if (rodada==26) {
            Integer r = rodada;
        }
        
        float calc=0;
        float media_rodada=0;
        float media_global=0;
        Integer idx = 0 ;
        
        for(Position position: PList){
            
            for (int r=rodada;r>=1;r--) {
               idx+=1; 
                List<Players> FiltroPartidas  = PlayersList.stream().filter(p -> (p.getRodada_id()== rodada.intValue()))
                                                               .filter(p -> (p.getAno()== NUM_YEAR.intValue()))
                                                               .collect(Collectors.toList());
                
                if (FiltroPartidas.size()==0) {
                    /* Se for 0 usa os dados da rodada anterior */
                    FiltroPartidas  = PlayersList.stream().filter(p -> (p.getRodada_id()== rodada.intValue()-1))
                                                               .filter(p -> (p.getAno()== NUM_YEAR.intValue()))
                                                               .collect(Collectors.toList());
                    
                }
                
                
                for(Players pMedia: FiltroPartidas){
                     //System.out.println(pMedia.getAtleta_id());
                     calc = calc + pMedia.getVariacao_num();

                } 
                media_rodada = (calc / idx);
                calc=0;
            }
            /* insere a nova informação no campo */
            position.setNum_media_rodadas(media_rodada);
        }
        idx=0;
        /* soma das médias parciais */
        //media_global = (float) PList.stream().filter(o -> o.getNum_media_rodadas()> 0).mapToDouble(o -> o.getNum_media_rodadas()).sum();
        for(Position position: PList){
            media_global+=position.getNum_media_rodadas();
        }
        
        /* Calcula Percentual por posição */
        float num_percent=0;
         for(Position percent: PList){
             num_percent = ((percent.getNum_media_rodadas() / media_global) * 100);
             percent.setNum_percentual(num_percent);
         }
        return PList;
    }
    
    private float CalculaVariacao(List<Players> PList, Integer proxrodada) {
        
        float CALC_VARIACAO = 0;
        for(Players player: PList){
            System.out.println(player.getAtleta_id());
            /* Ver o preço dos jogadores na proxima rodada */
            List<Players> PlayersFilter  = PlayersList.stream().filter(p -> (p.getAtleta_id()== player.getAtleta_id().intValue()))
                                                               .filter(p -> (p.getRodada_id()== proxrodada.intValue()))
                                                               .filter(p -> (p.getAno()== NUM_YEAR.intValue()))
                                                               .collect(Collectors.toList());
            try {
                CALC_VARIACAO += PlayersFilter.get(0).getVariacao_num();
            } catch (Exception e) {}
        }
        return CALC_VARIACAO;
    }

    private void InicializaTela() throws InterruptedException {
        
        classcontroler.root.getChildren().clear();
        classcontroler.pb.setProgress(0);
        classcontroler.lblPontos.setText("0,00");
        classcontroler.lblSaldo.setText(String.valueOf(FL_ORC_INICIAL));   
        classcontroler.lblMediaPontos.setText("0,00");   
        
        classcontroler.sMediaPontos.getData().clear();
        classcontroler.sMediaTime.getData().clear();
        
        if (!classcontroler.chkChart.isSelected()) {
            for(int instance=0;instance<classcontroler.serie_temp_list_media_pontos.size()-1;instance++) {
                classcontroler.serie_temp_list_media_pontos.get(instance).getData().clear();
                classcontroler.serie_temp_list_media_time.get(instance).getData().clear();
            }
        }
        
        Thread.sleep(1000);
    }
    
    //</editor-fold>     
    
}
            
//<editor-fold defaultstate="collapsed" desc="----- TRASH -----">


//                XYChart.Series<String,Number> sPOntos = new XYChart.Series<String,Number>();
//                sPOntos.setName("Pontos");
//                sPOntos.getData().add(new XYChart.Data<String,Number>("R1",200));
//                sPOntos.getData().add(new XYChart.Data<String,Number>("R2",400));
//                sPOntos.getData().add(new XYChart.Data<String,Number>("R3",600));
//                sPOntos.getData().add(new XYChart.Data<String,Number>("R4",600));
//
//                XYChart.Series<String,Number> sPreco = new XYChart.Series<String,Number>();
//                sPreco.setName("Preço");
//                sPreco.getData().add(new XYChart.Data<String,Number>("R1",2500));
//                sPreco.getData().add(new XYChart.Data<String,Number>("R2",4020));
//                sPreco.getData().add(new XYChart.Data<String,Number>("R3",800));
//                lineChart.getData().add(sPOntos);
//                lineChart.getData().add(sPreco);





//                Players pteste = new Players();
//                pteste.setNome("Edson");
//                pteste.setNome_posicao("Goleiro");

//                Players PLR = new Players();
//                PLR.setRodada_id(NUM_RODADA);
//                  
//                TreeItem<Players> root = new TreeItem<>(PLR);
//                TreeItem<Players> item  = new TreeItem<>(pteste);

//                root.getChildren().setAll(pItem);
//                classcontroler.tableview.setRoot(root);
                
//                classcontroler.col1.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(" Rodada " + String.valueOf(param.getValue().getValue().getRodada_id())));
//                classcontroler.col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNome()));
//                classcontroler.col3.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getNome_posicao()));
//                classcontroler.col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getValue().getMedia_num())));
//                classcontroler.col2.setCellValueFactory((TreeTableColumn.CellDataFeatures<Players, String> param) -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getValue().getPreco_num())));
                    
                //root.getChildren().setAll(item);
                //root.getChildren().setAll();
                


/* filtra as partidas por uma determinada posição */
            //List<Person> beerDrinkers = persons.stream().filter(p -> p.getAge() > 16).collect(Collectors.toList());
            //MatchesList.removeIf(p -> p.getPosicao_id()!= position.getPosicao_id());
            //Collection<String> filtered = Collections2.filter(list,Predicates.containsPattern("How"));
            //print(MatchesList);            
                          //Collections.sort(MatchesByYear, (a, b) -> b.getRodada_id().compareTo(a.getRodada_id()));            
                                    
                //List<Matches> MatchesByRodada = MatchesList.stream().filter(p -> p.getAno()== NUM_YEAR.intValue()).collect(Collectors.toList());
                 
//                for(Position position: PositionList){
//                    
//                   //List<Matches> m = MatchesList.stream().filter(p -> p.getPosicao_id() == position.getPosicao_id()).collect(Collectors.toList());
//                    
//                   System.out.println(position.getNome());
//                   //System.out.println(m.size());
//                            
//                    for(Matches matches: MatchesList){
//                        System.out.println(matches.getNome());
//                    }
//                    
//
//                }

 //</editor-fold>  

