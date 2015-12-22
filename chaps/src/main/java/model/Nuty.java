package model;

import java.util.HashMap;

/**
 * Created by Pawe³ on 2015-12-19.
 */
public class Nuty {



    public HashMap<String,String> utwory = new HashMap<String, String>();
    public HashMap<String,String> koledy = new HashMap<String, String>();
    public HashMap<String,String> sakralne = new HashMap<String, String>();
    public HashMap<String,String> rozrywkowe = new HashMap<String, String>();
    public HashMap<String,String> ludowe = new HashMap<String, String>();
    public HashMap<String,String> wspolczesna = new HashMap<String, String>();
    public HashMap<String,String> kategorie = new HashMap<String, String>();

    public Nuty()
    {

        this.kategorie.put("Koledy","Koledy");
        this.kategorie.put("Muzyka sakralna","Muzyka sakralna");
        this.kategorie.put("Muzyka rozrywkowa","Muzyka rozrywkowa");
        this.kategorie.put("Muzyka ludowa","Muzyka ludowa");
        this.kategorie.put("Muzyka wspolczesna","Muzyka wspolczesna");

        this.koledy.put("Aniol pasterzom mowil", "http://orientana-klub.pl/chapsapp/nuty/aniolpasterzommowi.pdf");
        this.koledy.put("Anioly","http://orientana-klub.pl/chapsapp/nuty/anioly.pdf");
        this.koledy.put("Bog sie rodzi","http://orientana-klub.pl/chapsapp/nuty/bogsierodzi.pdf");
        this.koledy.put("Bracia patrzcie jeno & Do szopy hej pasterze","http://orientana-klub.pl/chapsapp/nuty/braciapatrzciejenodoszopyhejpasterze.pdf");
        this.koledy.put("Dzieciatko sie narodzilo","http://orientana-klub.pl/chapsapp/nuty/dzieciatkosienarodzilo.pdf");
        this.koledy.put("Dziecina mala","http://orientana-klub.pl/chapsapp/nuty/dziecinamala.pdf");
        this.koledy.put("Dzisiaj w Betleem","http://orientana-klub.pl/chapsapp/nuty/dzisiajwbetleem.pdf");
        this.koledy.put("Dzisiaj w Betlejem & Lulajze jezuniu","http://orientana-klub.pl/chapsapp/nuty/dzisiajwbetlejemlulajzejezuniu.pdf");
        this.koledy.put("Gdy sie Chrystus rodzi","http://orientana-klub.pl/chapsapp/nuty/gdysiechrystusrodzi.pdf");
        this.koledy.put("Gdy sliczna Panna","http://orientana-klub.pl/chapsapp/nuty/gdyslicznapanna.pdf");
        this.koledy.put("Hej w dzien narodzenia","http://orientana-klub.pl/chapsapp/nuty/hejwdziennarodzenia.pdf");
        this.koledy.put("Jam jest dudka","http://orientana-klub.pl/chapsapp/nuty/jamjestdudka.pdf");
        this.koledy.put("Jezus malusienki","http://orientana-klub.pl/chapsapp/nuty/jezusmalusienki.pdf");
        this.koledy.put("Jezusek czuwa","http://orientana-klub.pl/chapsapp/nuty/jezusekczuwa.pdf");
        this.koledy.put("My tez pastuszkowie","http://orientana-klub.pl/chapsapp/nuty/mytezpastuszkowie.pdf");
        this.koledy.put("Pasli pasterze woly","http://orientana-klub.pl/chapsapp/nuty/paslipasterzewoly.pdf");
        this.koledy.put("Pojdzmy wszyscy","http://orientana-klub.pl/chapsapp/nuty/pojdzmywszyscy.pdf");
        this.koledy.put("Szczedryk","http://orientana-klub.pl/chapsapp/nuty/szczedryk.pdf");
        this.koledy.put("Wesola nowine","http://orientana-klub.pl/chapsapp/nuty/wesolanowine.pdf");
        this.koledy.put("Wsrod nocnej ciszy","http://orientana-klub.pl/chapsapp/nuty/wsrodnocnejciszy.pdf");
        this.koledy.put("Z narodzenia Pana","http://orientana-klub.pl/chapsapp/nuty/znarodzeniapana.pdf");
        this.koledy.put("Zagraly Fulorki","http://orientana-klub.pl/chapsapp/nuty/zagralyfulorki.pdf");
        this.koledy.put("Mosci gospodarzu","http://orientana-klub.pl/chapsapp/nuty/moscigospodarzu.pdf");

        this.sakralne.put("Ave Maria(Angelus Domini) - Franz Biebl","http://www.orientana-klub.pl/kruk/chapsapp/nuty/angelus_domini_biebl.pdf");
        this.sakralne.put("Bogoroditse Devo","http://www.orientana-klub.pl/kruk/chapsapp/nuty/bogoroditse_devo_rachmaninov.pdf");
        this.sakralne.put("Ave Verum Corpus","http://www.orientana-klub.pl/kruk/chapsapp/nuty/ave_verum_corpus.pdf");
        this.sakralne.put("Locus iste","http://www.orientana-klub.pl/kruk/chapsapp/nuty/locus_iste.pdf");
        this.sakralne.put("Niescie chwale, mocarze","http://www.orientana-klub.pl/kruk/chapsapp/nuty/niescie_chwale_mocarze.pdf");
        this.sakralne.put("Cieszmy sie","http://orientana-klub.pl/chapsapp/nuty/cieszmysie.pdf");

        this.ludowe.put("Mala suita kaszubska","http://www.orientana-klub.pl/kruk/chapsapp/nuty/mala_suita_kaszubska.pdf");

        this.wspolczesna.put("Mala suita kaszubska","http://www.orientana-klub.pl/kruk/chapsapp/nuty/mala_suita_kaszubska.pdf");

    }

}
