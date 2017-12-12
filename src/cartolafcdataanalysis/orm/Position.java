package cartolafcdataanalysis.orm;

import cartolafcdataanalysis.dao.MySQLObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Position {

//<editor-fold defaultstate="collapsed" desc="----- PROPRIEDADES -----">
  
    private Integer posicao_id= 0;
    private String nome= "";
    private String abreviacao= "";
    private Integer nu_jogadores= 0;
    private float num_media_rodadas= 0;
    private float num_percentual= 0;

    public Integer getPosicao_id() {
        return posicao_id;
    }

    public void setPosicao_id(Integer posicao_id) {
        this.posicao_id = posicao_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public Integer getNu_jogadores() {
        return nu_jogadores;
    }

    public void setNu_jogadores(Integer nu_jogadores) {
        this.nu_jogadores = nu_jogadores;
    }    
    
        public float getNum_media_rodadas() {
        return num_media_rodadas;
    }

    public void setNum_media_rodadas(float num_media_rodadas) {
        this.num_media_rodadas = num_media_rodadas;
    }

    public float getNum_percentual() {
        return num_percentual;
    }

    public void setNum_percentual(float num_percentual) {
        this.num_percentual = num_percentual;
    }
    

//</editor-fold>  
 
//<editor-fold defaultstate="sollapsed" desc="----- MÉTODOS  -----">
    
    public List<Position> getAll(String ESQUEMA_TATICO) throws SQLException{

        List<Position> myList = new ArrayList<Position>();
        
        Statement s =  MySQLObject.getConexaoMySQL().createStatement(); 
        String sqlQuery =  "SELECT posicao_id, nome , (SELECT quant FROM cartolafc.esquema WHERE  nome = '" + ESQUEMA_TATICO + "' AND posicao = posicao.abreviacao  ) as nu_jogadores, abreviacao FROM cartolafc.posicao " ;
        System.out.println(sqlQuery);
        ResultSet rs = s.executeQuery(sqlQuery);
                
        Position obj;
        while(rs.next()) {
            obj = new Position();
            obj.posicao_id = rs.getInt("posicao_id");
            obj.nome = rs.getString("nome");
            obj.abreviacao = rs.getString("abreviacao");
            obj.nu_jogadores = rs.getInt("nu_jogadores");
            myList.add(obj);
        }
               
       return myList;
        
    }
    
//</editor-fold>    

    public void getNum_percentual(float num_percent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
