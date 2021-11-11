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
 * Classe qui permet de découper les noeuds du texte dans le document XML
 *
 */
public class RssPromoProvider extends DefaultHandler {

	// des objets à utiliser pour le stockage temporaire
	private Promotion currentPromo = new Promotion();
	private Magasin m=new Magasin();
	private List<Promotion> promoList = new ArrayList<Promotion>();
	//Current characters being accumulated
	StringBuffer chars = new StringBuffer();
	
	public List<Promotion> getPromoList() {
		return promoList;
	}

	/* 
	 * Cette méthode est appelée à chaque fois un élément de démarrage est trouvé 
	 * Ici, nous réinitialisons toujours les caractères StringBuffer que nous ne sommes actuellement intéressé
	 * * Dans le texte les valeurs stockées dans les nœuds 
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts) {
		chars = new StringBuffer();
	}
	/* 
	 * Cette méthode est appelée à chaque fois un élément d'extrémité est trouvée (un marqueur de fermeture XML)
	 * * On vérifie ici ce que l'élément est fermé
	 * * Vérification, telles que le titre, alors nous obtenons les caractères que nous avons accumulées dans le StringBuffer
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
	 * Cette méthode est appelée lorsque les personnages se trouvent entre les balises XML,
	 *  cependant, il n'y a aucune garantie que ce sera appelée à la fin du noeud,
	 *   ou qu'elle sera appelée une seule fois pour que nous accumulons le noeud 
	 *    puis traitons avec eux endElement () pour être sûr que nous avons tout le texte
	 * 
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}
}