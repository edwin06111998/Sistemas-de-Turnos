package FXMLControllers;

import ComponentesSistema.Puesto;
import ComponentesSistema.Turno;
import ComponentesSistema.TurnoPuesto;
import TDA.CircularLinkedList;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import resources.MediaVideos;

/**
 *
 * @author PC
 */

    
public class SitemaPrincipalController implements Initializable{

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private MediaView media;
    @FXML
    private TableView<TurnoPuesto> tbTurnoPuesto;
    @FXML
    private TableColumn<String, TurnoPuesto> colTurno;
    @FXML
    private TableColumn<Integer, TurnoPuesto> colPuesto;

    private LinkedList<TurnoPuesto> tableList;
    private Queue<Puesto> puestosLibres;
    private PriorityQueue<Turno> turnos;
    
    
    
    private static SitemaPrincipalController singleInstance;
    
    /**
     *Constructor: Initializes the lists
     */
    public SitemaPrincipalController() {
        tableList = new LinkedList<>();
        puestosLibres = new LinkedList<>();
        turnos = new PriorityQueue<>((Turno t1,Turno t2)-> 
                t1.getPaciente().getSintoma().getPrioridad()-t2.getPaciente().getSintoma().getPrioridad());
    }
    
    /**
     * Method for Singleton Design Pattern.
     * @return Single Instance of the class
     */
    public static SitemaPrincipalController getInstance(){
        if(singleInstance == null){
            singleInstance = new SitemaPrincipalController();
        }
        return singleInstance;
    }

  

    /**
     *Method for assigning places to a turn.
     */
    public void asignarPuestoATurno() {
        if (puestosLibres.size() > 0 && !turnos.isEmpty()) {
            Turno t = turnos.poll();
            Puesto p = puestosLibres.poll();
            TurnoPuesto tp = new TurnoPuesto(t,p);
            tableList.addFirst(tp);
            tbTurnoPuesto.refresh();
        }
    }

    public LinkedList<TurnoPuesto> getTableList() {
        return tableList;
    }

    public void setTableList(LinkedList<TurnoPuesto> tableList) {
        this.tableList = tableList;
    }

    public Queue<Puesto> getPuestosLibres() {
        return puestosLibres;
    }

    public void setPuestosLibres(Queue<Puesto> puestosLibres) {
        this.puestosLibres = puestosLibres;
    }

    public PriorityQueue<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(PriorityQueue<Turno> turnos) {
        this.turnos = turnos;
    }
    
      @FXML
     @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        
       colTurno.setCellValueFactory(new PropertyValueFactory("turno"));
        colPuesto.setCellValueFactory(new PropertyValueFactory("puesto"));
        //System.out.println(url.toString());
        //System.out.println("Prueba");
        //mediaplayer= new MediaPlayer(new Media(this.getClass().getResource(MEDIA_URL).toExternalForm()));
       // mediaplayer.setAutoPlay(true);
        //media.setMediaPlayer(mediaplayer)
     
       
           initMediaPlayer(media, urlsVideos);
        
    }
    
    
    
    
     CircularLinkedList<String> urls= MediaVideos.readFileOfVideo();
     Iterator<String> urlsVideos=urls.iterator();
     
 

    private void initMediaPlayer( final MediaView mediaView, final Iterator<String> urls)       {
        
         if (urlsVideos.hasNext()){
       MediaPlayer mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource(urlsVideos.next()).toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                initMediaPlayer(mediaView, (Iterator<String>) urls);
            }
        });
        mediaView.setMediaPlayer(mediaPlayer);
    } 
      
}
    
    
}
