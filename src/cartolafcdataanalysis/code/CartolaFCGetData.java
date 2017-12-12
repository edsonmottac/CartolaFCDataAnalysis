package cartolafcdataanalysis.code;

import cartolafcdataanalysis.dao.MySQLObject;
import cartolafcdataanalysis.util.MyNumber;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CartolaFCGetData {
    
    //public MyNumber myNum;
    
     public void LoadAtletas(MyNumber myNum) throws SQLException, UnsupportedEncodingException  {
        
        
         
        Statement s =  MySQLObject.getConexaoMySQL().createStatement(); 
        Statement stclube =  MySQLObject.getConexaoMySQL().createStatement(); 
        Statement stposicao =  MySQLObject.getConexaoMySQL().createStatement(); 
        
        String sqlQuery="";

        sqlQuery = " DELETE FROM cartolafc.atleta " ;
        MySQLObject.getConexaoMySQL().createStatement().executeUpdate(sqlQuery);

        //sqlQuery =  " SELECT * FROM cartolafc.cartola_scouts  " ;
        sqlQuery =  " SELECT * FROM cartolafc.cartola_aggregated where posicao not in ('NA')  " ;
        ResultSet rs = MySQLObject.getConexaoMySQL().createStatement().executeQuery(sqlQuery);
        
        int i=0;
        
        while (rs.next()) {            
        i+=1;    
        myNum.setNumber(((float) i/32973));
        System.out.println("GETNUMERO: " + myNum.getNumber());
        System.out.println("Registro: " + i);
       
            /* Verifica duplicidades */

                //String nome;
                //nome=(new String(((String) rs.getString("ClubeID")).getBytes("ISO-8859-1"), "UTF-8"));
                System.out.println(rs.getString("Apelido"));
                
                /* localizar ID do clube */
                 
                Integer clube_id =  0; //rsClube.getInt("clube_id");

                /* Recupera dados do arqivo scouts */                 
                //sqlQuery =  " SELECT clube_id FROM cartolafc.clube WHERE nome =  '" +  (new String(((String) rs.getString("ClubeID")).getBytes("ISO-8859-1"), "UTF-8"))  + "'" ;
                
                /* Recupera dados do aruqivo agregated */
                sqlQuery =  " SELECT clube_id FROM cartolafc.clube WHERE clube_id =  " + rs.getString("ClubeID");
                
                ResultSet rsClube = stclube.executeQuery(sqlQuery);
                if (rsClube.next()) { 
                    clube_id = rsClube.getInt("clube_id");
                } else {
                    clube_id = 0 ;
                }
                
                /* localiza posição */
                Integer posicao_id = 0; //rsPosicao.getInt("posicao_id");
                sqlQuery =  " SELECT posicao_id FROM cartolafc.posicao WHERE abreviacao =  '" + rs.getString("Posicao") + "'" ;
                ResultSet rsPosicao = stposicao.executeQuery(sqlQuery);
                 if (rsPosicao.next()) { 
                    posicao_id = rsPosicao.getInt("posicao_id");
                } else {
                    posicao_id = 0 ;
                }
               
                Integer status_id = 0;//rs.getInt("Status");
                Integer atleta_id = rs.getInt("AtletaID");
                Integer rodada_id = rs.getInt("Rodada");
                
                String nome = (new String(((String) rs.getString("Apelido")).getBytes("ISO-8859-1"), "UTF-8"));
                String apelido = (new String(((String) rs.getString("Apelido")).getBytes("ISO-8859-1"), "UTF-8"));
                String foto = "";
                String pontos_num = rs.getString("Pontos");
                String preco_num = rs.getString("Preco");
                String variacao_num = rs.getString("PrecoVariacao");
                String media_num = rs.getString("PontosMedia");
                String jogos_num = rs.getString("Jogos");
                String scout_p_RB = rs.getString("RB");
                String scout_p_G = rs.getString("G");
                String scout_p_A = rs.getString("A");
                String scout_p_SG = rs.getString("SG");
                String scout_p_FS = rs.getString("FS");
                String scout_p_FF = rs.getString("FF");
                String scout_p_FD = rs.getString("FD");
                String scout_p_FT = rs.getString("FT");
                String scout_p_DD = rs.getString("DD");
                String scout_p_DP = rs.getString("DP");
                String scout_n_GC = rs.getString("GC");
                String scout_n_CV = rs.getString("CV");
                String scout_n_CA = rs.getString("CA");
                String scout_n_GS = rs.getString("GS");
                String scout_n_PP = rs.getString("PP");
                String scout_n_FC = rs.getString("FC");
                String scout_n_I = rs.getString("I");
                String scout_n_PE = rs.getString("PE");
                String ano = rs.getString("ano");
                String mes = rs.getString("mes");
                String local_jogo = rs.getString("variable");
                String score_home = rs.getString("home.score.x");
                String score_away = rs.getString("away.score.x");
           
           
             sqlQuery =  "INSERT INTO cartolafc.atleta "
                            + "VALUES ( "
                            + "'"  + atleta_id + "'"
                            + ",'" + posicao_id + "'"
                            + ",'" + clube_id + "'"
                            + ",'" + status_id + "'"
                            + ",'" + rodada_id + "'"
                            + ",'" + nome + "'"
                            + ",'" + apelido + "'"
                            + ",'" + foto + "'"
                            + ",'" + pontos_num + "'"
                            + ",'" + preco_num + "'"
                            + ",'" + variacao_num + "'"
                            + ",'" + media_num + "'"
                            + ",'" + jogos_num + "'"
                            + "," + scout_p_RB 
                            + "," + scout_p_G 
                            + "," + scout_p_A 
                            + "," + scout_p_SG 
                            + "," + scout_p_FS 
                            + "," + scout_p_FF 
                            + "," + scout_p_FD 
                            + "," + scout_p_FT 
                            + "," + scout_p_DD 
                            + "," + scout_p_DP 
                            + "," + scout_n_GC 
                            + "," + scout_n_CV 
                            + "," + scout_n_CA 
                            + "," + scout_n_GS 
                            + "," + scout_n_PP 
                            + "," + scout_n_FC 
                            + "," + scout_n_I 
                            + "," + scout_n_PE
                            + "," + ano
                            + "," + mes
                            + ",'" + local_jogo + "'"
                            + "," + score_home                     
                            + "," + score_away;                     
                            sqlQuery = sqlQuery + ");";
                    System.out.println(sqlQuery);
                    s.executeUpdate(sqlQuery);
        }
    }
}
