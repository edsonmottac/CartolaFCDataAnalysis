
package cartolafcdataanalysis.orm;

import cartolafcdataanalysis.dao.MySQLObject;
import com.sun.media.jfxmedia.events.PlayerEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Players {
    
    //<editor-fold defaultstate="collapsed" desc="----- PROPRIEDADES -----">
    private Integer atleta_id = 0;
    private String nome = "";
    private Integer posicao_id = 0;
    private Integer clube_id = 0;
    private String nome_posicao = "";
    private Integer rodada_id = 0;
    private String local_jogo = "";
    private Integer ano = 0;
    private float pontos_num = 0;
    private float preco_num = 0;
    private float variacao_num = 0;
    private float media_num = 0;
    private float media_rodadas = 0;
    

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
    
    public Integer getAtleta_id() {
        return atleta_id;
    }

    public void setAtleta_id(Integer atleta_id) {
        this.atleta_id = atleta_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPosicao_id() {
        return posicao_id;
    }

    public void setPosicao_id(Integer posicao_id) {
        this.posicao_id = posicao_id;
    }
    
    public Integer getClube_id() {
        return clube_id;
    }

    public void setClube_id(Integer clube_id) {
        this.clube_id = clube_id;
    }

    public String getNome_posicao() {
        return nome_posicao;
    }

    public void setNome_posicao(String nome_posicao) {
        this.nome_posicao = nome_posicao;
    }

    public Integer getRodada_id() {
        return rodada_id;
    }

    public void setRodada_id(Integer rodada_id) {
        this.rodada_id = rodada_id;
    }
    
     public String getLocal_jogo() {
        return local_jogo;
    }

    public void setLocal_jogo(String local_jogo) {
        this.local_jogo = local_jogo;
    }

    public float getPontos_num() {
        return pontos_num;
    }

    public void setPontos_num(float pontos_num) {
        this.pontos_num = pontos_num;
    }

    public float getPreco_num() {
        return preco_num;
    }

    public void setPreco_num(float preco_num) {
        this.preco_num = preco_num;
    }

    public float getVariacao_num() {
        return variacao_num;
    }

    public void setVariacao_num(float variacao_num) {
        this.variacao_num = variacao_num;
    }

    public float getMedia_num() {
        return media_num;
    }

    public void setMedia_num(float media_num) {
        this.media_num = media_num;
    }

    public float getMedia_rodadas() {
        return media_rodadas;
    }

    public void setMedia_rodadas(float media_rodadas) {
        this.media_rodadas = media_rodadas;
    }
    
    //</editor-fold>  

    //<editor-fold defaultstate="sollapsed" desc="----- MÉTODOS  -----">
    
    public List<Players> getAll() throws SQLException{
        List<Players> myList = new ArrayList<Players>();
        
        Statement s =  MySQLObject.getConexaoMySQL().createStatement(); 
        String sqlQuery =   " SELECT DISTINCT " + 
                            " 	atleta.atleta_id, " + 
                            " 	atleta.nome, " + 
                            " 	atleta.posicao_id, " + 
                            " 	atleta.clube_id, " + 
                            " 	atleta.rodada_id," + 
                            " 	CASE atleta.local_jogo WHEN 'home.team' THEN 'Casa' ELSE 'Fora' END AS local_jogo," + 
                            " 	atleta.ano," + 
                            " 	posicao.nome as nome_posicao, " + 
                            " 	pontos_num," + 
                            " 	preco_num," + 
                            " 	variacao_num," + 
                            " 	media_num," + 
                            " 	0 as media_rodadas " + 
                            " FROM " + 
                            " 	cartolafc.atleta " + 
                            "      JOIN cartolafc.posicao" + 
                            "           ON atleta.posicao_id = posicao.posicao_id " +
                            " WHERE 1=1 "; 

        ResultSet rs = s.executeQuery(sqlQuery);
        System.out.println(sqlQuery);
        Players obj;
        while(rs.next()) {
            
            obj = new Players();
            obj.atleta_id = rs.getInt("atleta_id");
            obj.nome=rs.getString("nome");
            obj.posicao_id = rs.getInt("posicao_id");
            obj.clube_id = rs.getInt("clube_id");
            obj.ano = rs.getInt("ano");
            obj.nome_posicao = rs.getString("nome_posicao");
            obj.local_jogo = rs.getString("local_jogo");
            obj.rodada_id = rs.getInt("rodada_id");
            obj.preco_num = rs.getFloat("preco_num");
            obj.pontos_num=  rs.getFloat("pontos_num");
            obj.variacao_num = rs.getFloat("variacao_num");
            obj.media_num = rs.getFloat("media_num");
            obj.media_rodadas = rs.getFloat("media_rodadas");
            
            myList.add(obj);
        }
               
       return myList;
    }
    
    public List<Players> getDistinctID (Integer ano_ref) throws SQLException{
        List<Players> myList = new ArrayList<Players>();
        
        Statement s =  MySQLObject.getConexaoMySQL().createStatement(); 
        String sqlQuery =   " SELECT DISTINCT " + 
                            " 	atleta.atleta_id " + 
                            " FROM " + 
                            " 	cartolafc.atleta " + 
                            " WHERE 1=1 "; 

        ResultSet rs = s.executeQuery(sqlQuery);
                
        Players obj;
        while(rs.next()) {
            obj = new Players();
            obj.atleta_id = rs.getInt("atleta_id");
            myList.add(obj);
        }
               
       return myList;
    }
    
    
    public int UltimaRodada(int ano_ref) throws SQLException {
        Statement s =  MySQLObject.getConexaoMySQL().createStatement(); 
        String sqlQuery =   " SELECT MAX(CONVERT(rodada_id,UNSIGNED)) AS valor FROM cartolafc.atleta WHERE ano=" + ano_ref;
        ResultSet rs = s.executeQuery(sqlQuery);
        if (rs.next()) { 
            return (rs.getInt("valor")); /* Este índice é utilizado no loop, que esta configurados para inicialização com  "0" */
        } else {
            return 0;
        }
       
    }
    //</editor-fold>  
    
}
