package cartolafcdataanalysis.orm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyTeam {

    //<editor-fold defaultstate="sollapsed" desc="----- PROPRIEDADES  -----">

    private List<Players> MyTeamList;
    private float total_media_time=0;
    private float total_preco_time=0;
    private float total_pontos_time=0;
    private float total_saldo_anterior=0;
    private float total_saldo_atual=0;
    private float total_saldo_variacao=0;
    
    private float media_pontos_cumulativa=0;
   
    public List<Players> getMyTeamList() {
        return MyTeamList;
    }

    public void setMyTeamList(List<Players> MyTeamList) {
        this.MyTeamList = MyTeamList;
    }

    public float getTotal_media_time() {
        return total_media_time;
    }

    public void setTotal_media_time(float total_media_time) {
        this.total_media_time = total_media_time;
    }

    public float getTotal_preco_time() {
        return total_preco_time;
    }

    public void setTotal_preco_time(float total_preco_time) {
        this.total_preco_time = total_preco_time;
    }

    public float getTotal_pontos_time() {
        return total_pontos_time;
    }

    public void setTotal_pontos_time(float total_pontos_time) {
        this.total_pontos_time = total_pontos_time;
    }

    public float getTotal_saldo_anterior() {
        return total_saldo_anterior;
    }

    public void setTotal_saldo_anterior(float total_saldo_anterior) {
        this.total_saldo_anterior = total_saldo_anterior;
    }

    public float getTotal_saldo_atual() {
        return total_saldo_atual;
    }

    public void setTotal_saldo_atual(float total_saldo_atual) {
        this.total_saldo_atual = total_saldo_atual;
    }

    public float getTotal_saldo_variacao() {
        return total_saldo_variacao;
    }

    public void setTotal_saldo_variacao(float total_saldo_variacao) {
        this.total_saldo_variacao = total_saldo_variacao;
    }
    
     public float getMedia_pontos_cumulativa() {
        return media_pontos_cumulativa;
    }

    public void setMedia_pontos_cumulativa(float media_pontos_cumulativa) {
        this.media_pontos_cumulativa = media_pontos_cumulativa;
    }
    
    
    //</editor-fold> 
   
    //<editor-fold defaultstate="sollapsed" desc="----- MÉTODOS  -----">
  
    //</editor-fold> 

    

    
    
        
    }
    
      
    
    
    
    


