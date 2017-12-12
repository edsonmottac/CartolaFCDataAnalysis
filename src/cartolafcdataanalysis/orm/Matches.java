
package cartolafcdataanalysis.orm;

import cartolafcdataanalysis.dao.MySQLObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Matches {

    public static Object stream() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //<editor-fold defaultstate="collapsed" desc="----- VARIÁVEIS -----">
    
    private Integer atleta_id = 0;
    private Integer posicao_id = 0;
    private Integer clube_id = 0;
    private Integer status_id = 0;
    private Integer rodada_id = 0;
    private String nome = "";
    private float pontos_num = 0;
    private float preco_num = 0;
    private float variacao_num = 0;
    private float media_num = 0;
    private Integer ano = 0;
    private Integer mes = 0;
    private String local_jogo = "";
    
    //</editor-fold>  
    
    //<editor-fold defaultstate="collapsed" desc="----- PROPRIEDADES -----">
    
    public Integer getAtleta_id() {
        return atleta_id;
    }

    public void setAtleta_id(Integer atleta_id) {
        this.atleta_id = atleta_id;
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

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getRodada_id() {
        return rodada_id;
    }

    public void setRodada_id(Integer rodada_id) {
        this.rodada_id = rodada_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getLocal_jogo() {
        return local_jogo;
    }

    public void setLocal_jogo(String local_jogo) {
        this.local_jogo = local_jogo;
    }
    
    //</editor-fold>      
    
    //<editor-fold defaultstate="sollapsed" desc="----- MÉTODOS  -----">
    
    public List<Matches> getAll() throws SQLException{
        
        List<Matches> myList = new ArrayList<Matches>();
        
        Statement s =  MySQLObject.getConexaoMySQL().createStatement(); 
        String sqlQuery =  " SELECT * FROM cartolafc.atleta  " ;
        ResultSet rs = s.executeQuery(sqlQuery);
                
        Matches obj;
        while(rs.next()) {
            //System.out.println(rs.getString("nome"));
            //System.out.println(rs.getInt("clube_id"));

            
            obj = new Matches();
            obj.atleta_id = rs.getInt("atleta_id");
            obj.posicao_id = rs.getInt("posicao_id");
            obj.clube_id = rs.getInt("clube_id");
            obj.status_id = rs.getInt("status_id");
            obj.rodada_id = rs.getInt("rodada_id");
            obj.nome=rs.getString("nome");
            obj.pontos_num = rs.getFloat("pontos_num");
            obj.preco_num = rs.getFloat("preco_num");
            obj.variacao_num = rs.getFloat("variacao_num");
            obj.media_num = rs.getFloat("media_num");
            obj.ano = rs.getInt("ano");
            obj.mes = rs.getInt("mes");
            obj.local_jogo=rs.getString("local_jogo");
            myList.add(obj);
        }
               
       return myList;
        
    }
    
    
    //</editor-fold>      
}
