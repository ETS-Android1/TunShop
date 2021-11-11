package com.promotion;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.entite.Magasin;
import com.entite.Promotion;
import com.util.Constants;

/**
 * 
 * Classe qui permet de d�couper les noeuds du texte dans le document XML
 *
 */
public class RssPromoProvider extends DefaultHandler {

	// des objets � utiliser pour le stockage temporaire
	private Promotion currentPromo = new Promotion();
	private Magasin m=new Magasin();
	private List<Promotion> promoList = new ArrayList<Promotion>();
	//Current characters being accumulated
	StringBuffer chars = new StringBuffer();
	
	public List<Promotion> getPromoList() {
		return promoList;
	}

	/* 
	 * Cette m�thode est appel�e � chaque fois un �l�ment de d�marrage est trouv� 
	 * Ici, nous r�initialisons toujours les caract�res StringBuffer que nous ne sommes actuellement int�ress�
	 * * Dans le texte les valeurs stock�es dans les n�uds 
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts) {
		chars = new StringBuffer();
	}
	/* 
	 * Cette m�thode est appel�e � chaque fois un �l�ment d'extr�mit� est trouv�e (un marqueur de fermeture XML)
	 * * On v�rifie ici ce que l'�l�ment est ferm�
	 * * V�rification, telles que le titre, alors nous obtenons les caract�res que nous avons accumul�es dans le StringBuffer
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {		
	
		if (localName.equalsIgnoreCase("titre")){
			currentPromo.setTitre(chars.toString());
		} 
		else if (localName.equalsIgnoreCase("description")){
			currentPromo.setDescription(chars.toString());
		}
		else if (localName.equalsIgnoreCase("categorie")){
			currentPromo.setCategorie(chars.toString());
		}  
		else if (localName.equalsIgnoreCase("date_fin")){
			currentPromo.setDate_fin(chars.toString());
		} 
		else if (localName.equalsIgnoreCase("img")){
			currentPromo.setUrl_img(Constants.url_promo+chars.toString());
		} 
			else if (localName.equalsIgnoreCase("lib")){
			m.setLib(chars.toString());
		} 
		else if (localName.equalsIgnoreCase("logo")){
			m.setUrl_logo(Constants.url_mag+chars.toString());
		} 
		// Check if Promotion is complete
		if (localName.equalsIgnoreCase("promotion")) 
		{
			currentPromo.setMagasin(m);
			promoList.add(currentPromo);
			currentPromo = new Promotion();
			m=new Magasin();
		}		
	}


	/* 
	 * Cette m�thode est appel�e lorsque les personnages se trouvent entre les balises XML,
	 *  cependant, il n'y a aucune garantie que ce sera appel�e � la fin du noeud,
	 *   ou qu'elle sera appel�e une seule fois pour que nous accumulons le noeud 
	 *    puis traitons avec eux endElement () pour �tre s�r que nous avons tout le texte
	 * 
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}
}