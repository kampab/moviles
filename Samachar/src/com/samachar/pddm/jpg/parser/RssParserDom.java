package com.samachar.pddm.jpg.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.samachar.pddm.jpg.model.Catalogo;
import com.samachar.pddm.jpg.model.Noticia;

import android.util.Log;

public class RssParserDom {
	private URL rssUrl;
	private List<Noticia> noticias = new ArrayList<Noticia>();;

	public RssParserDom(String url) {
		Log.i("RSSParserDom", "Constructor");
		try {
			this.rssUrl = new URL(url.trim());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public List<Noticia> parse() {

		try {
			// Instanciamos la fábrica para DOM
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// Creamos un nuevo parser DOM
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Realizamos la lectrua completa del XML
			Document dom = builder.parse(getInputStream());

			// Nos posicionamos en el nodo principal del árbol<Rss>
			Element root = dom.getDocumentElement();

			// Localizamos todos los elementos <item>
			NodeList items = root.getElementsByTagName("item");

			// Recorremos la lista de noticias
			for (int i = 0; i < items.getLength(); i++) {
				Noticia noticia = new Noticia();

				// Obtenemos la noticia actual
				Node item = items.item(i);

				// Obtenemos la lista de datos de la noticia actual
				NodeList datosNoticia = item.getChildNodes();

				// Procesamos cada dato de la noticia
				for (int j = 0; j < datosNoticia.getLength(); j++) {
					Node dato = datosNoticia.item(j);
					String etiqueta = dato.getNodeName();

					if (etiqueta.equals("title")) {
						String texto = obtenerTexto(dato);
						noticia.setTitulo(texto);
					} else if (etiqueta.equals("link")) {
						noticia.setLink(dato.getFirstChild().getNodeValue());
					} else if (etiqueta.equals("description")) {
						String texto = obtenerTexto(dato);
						noticia.setDescripcion(texto);
					} else if (etiqueta.equals("guid")) {
						noticia.setGuid(dato.getFirstChild().getNodeValue());
					} else if (etiqueta.equals("pubDate")) {
						noticia.setFecha(dato.getFirstChild().getNodeValue());
					} else if (etiqueta.equals("media:content")
							|| etiqueta.equalsIgnoreCase("media:thumbnail")) {
						Element ima = (Element) dato;
						String link = ima.getAttribute("url");
						noticia.setImagen(link);
					}
				}

				Log.i("Parse", "añadir un item");

				noticias.add(noticia);
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return noticias;
	}

	public Catalogo parseTitulares() {

		Catalogo catalogo = new Catalogo();

		try {
			// Instanciamos la fábrica para DOM
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// Creamos un nuevo parser DOM
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Realizamos la lectrua completa del XML
			Document dom;

			dom = builder.parse(getInputStream());

			// Nos posicionamos en el nodo principal del árbol<Rss>
			Element root = dom.getDocumentElement();

			// Localizamos todos los elementos <Channel>
			NodeList canal = root.getElementsByTagName("channel");

			// Obtener nodo de canal
			Node nodos = canal.item(0);

			// Obtenemos la lista de los los elementos de raíz
			NodeList datosCatalogo = nodos.getChildNodes();

			for (int i = 0; i < datosCatalogo.getLength(); i++) {
				// Procesamos cada etiqueta de channel
				Node dato = datosCatalogo.item(i);
				String etiqueta = dato.getNodeName();
				

				if (etiqueta.equals("title")) {
					catalogo.setNombre(dato.getFirstChild().getNodeValue());
				} else if (etiqueta.equals("atom:link")) {
					Element ima = (Element) dato;
					String origen = ima.getAttribute("href");
					catalogo.setLinkOrigen(origen);
				} else if (etiqueta.equals("link")) {
					catalogo.setLinkOrigen(dato.getFirstChild().getNodeValue());
				} else if (etiqueta.equals("image")) {
					NodeList nodoImagen = dato.getChildNodes();
					for (int j = 0; j < nodoImagen.getLength(); j++) {
						Node itemImagen = nodoImagen.item(j);
						etiqueta = itemImagen.getNodeName();
						if (etiqueta.equals("url")) {
							catalogo.setImagen(itemImagen.getFirstChild()
									.getNodeValue());
							j= nodoImagen.getLength();
							i = datosCatalogo.getLength();
						}
					}
				}
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return catalogo;

	}

	public InputStream getInputStream() {
		try {
			return rssUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private String obtenerTexto(Node datos) {
		StringBuilder texto = new StringBuilder();
		NodeList fragmentos = datos.getChildNodes();

		for (int k = 0; k < fragmentos.getLength(); k++) {
			texto.append(fragmentos.item(k).getNodeValue());
		}
		return texto.toString();
	}

}
