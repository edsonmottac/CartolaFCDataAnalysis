
package cartolafcdataanalysis.orm;

import cartolafcdataanalysis.dao.MySQLObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Tabela {

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getClube_id() {
        return clube_id;
    }

    public void setClube_id(String clube_id) {
        this.clube_id = clube_id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    Integer posicao;
    String clube_id;
    Integer ano;

    
    public List<Tabela> getAll(Integer ano) throws SQLException{
        List<Tabela> myList = new ArrayList<>();
        
        Statement s =  MySQLObject.getConexaoMySQL().createStatement(); 
        String sqlQuery =   " select posicao, clube_id, ano  from cartolafc.tabela WHERE ano  = " + ano; 
        
        System.out.println(sqlQuery);

        ResultSet rs = s.executeQuery(sqlQuery);
                
        Tabela obj;
        while(rs.next()) {
            
            obj = new Tabela();
            obj.posicao = rs.getInt("posicao");
            obj.clube_id =rs.getString("clube_id");
            obj.ano = rs.getInt("ano");
            
            myList.add(obj);
        }
               
       return myList;
    }
    
    
}
